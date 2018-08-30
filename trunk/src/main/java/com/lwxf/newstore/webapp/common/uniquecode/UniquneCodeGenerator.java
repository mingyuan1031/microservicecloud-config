package com.lwxf.newstore.webapp.common.uniquecode;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.lwxf.commons.constant.CommonConstant;
import com.lwxf.commons.exception.LwxfIllegalArgumentException;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.newstore.webapp.baseservice.cache.RedisNotSupportTransactionUtils;
import com.lwxf.newstore.webapp.baseservice.cache.SequenceGenerator;

/**
 * 功能：编号生成器
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-28 15:13
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("uniquneCodeGenerator")
public class UniquneCodeGenerator {
	@Resource(name = "sequenceGenerator")
	private SequenceGenerator sequenceGenerator;
	@Resource(name = "redisNotSupportTransactionUtils")
	private RedisNotSupportTransactionUtils redisUtils;
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<UniqueResource,INoCreator> creators;
	UniquneCodeGenerator(){
		creators = new HashMap<>();
		creators.put(UniqueResource.ORDER_NO,new OrderNoCreator(UniqueResource.ORDER_NO));
		creators.put(UniqueResource.LOGISTICS_NO,new LogisticsNoCreator(UniqueResource.LOGISTICS_NO));
		creators.put(UniqueResource.PAID_RECORDS_NO,new PaidRecordsNoCreator(UniqueResource.PAID_RECORDS_NO));
		creators.put(UniqueResource.NEWS_ID,new NewsIdCreator(UniqueResource.NEWS_ID));
		creators.put(UniqueResource.TRADE_NO,new TradeNoCreator(UniqueResource.TRADE_NO));
	}

	public String getNextNo(UniqueResource resource){
		INoCreator creator = this.creators.get(resource);
		if(null == creator){
			throw new LwxfIllegalArgumentException("未初始化的编号生成器："+resource.toString());
		}
		return creator.createNextNo();
	}

	interface INoCreator{
		String createNextNo();
	}

	abstract class AbstractNoCreator implements INoCreator{
		protected static final String dateFormat = "yyyyMMdd";
		protected static final String REDIS_KEY_PREFIX_TEMPLATE="{0}_PREFIX";
		protected volatile  String prefix;
		protected int noLen=5;
		protected String redisKey;
		protected String redisKeyPrefix;

		AbstractNoCreator(UniqueResource uniqueResource){
			this.redisKey=uniqueResource.toString();
			this.redisKeyPrefix = LwxfStringUtils.format(REDIS_KEY_PREFIX_TEMPLATE,this.redisKey);
		}
		@Override
		public String createNextNo() {
			return this.getNextNo(this.reset());
		}

		protected String getNextNo(int resetNo){
			String noStr;
			if(resetNo > 0){
				noStr = String.valueOf(resetNo);
			}else{
				noStr = String.valueOf(sequenceGenerator.generate(this.redisKey));
			}
			logger.error("1 >>>>>>>>>> redisKey ={}，生成的编号：{}",this.redisKey,noStr);
			int noStrLen = noStr.length();
			if(noStrLen >= this.noLen){
				return noStr;
			}
			int preFixLen = this.noLen - noStrLen;
			StringBuilder sb = new StringBuilder(this.prefix);
			for(int i=0;i<preFixLen;i++){
				sb.append(CommonConstant.STRING_ZERO);
			}
			String retNo = sb.append(noStr).toString();
			logger.error("2 >>>>>>>>>> redisKey ={}，生成的编号：{}",this.redisKey,retNo);
			return retNo;
		}

		protected int reset(){
 			String currDate = DateUtil.dateTimeToString(DateUtil.getSystemDate(),dateFormat);
 			int resetNo = -1; // 表示没重置

			if(this.prefix == null){
				String redisCurrDate = redisUtils.getString(this.redisKeyPrefix);
				if(LwxfStringUtils.isBlank(redisCurrDate)){
					redisCurrDate = currDate;
					redisUtils.set(this.redisKeyPrefix,redisCurrDate);
				}
				this.prefix = redisCurrDate;
			}
			if(!this.prefix.equals(currDate)){
				resetNo = 1;
				this.prefix = currDate;
				sequenceGenerator.set(this.redisKey,resetNo);
				redisUtils.set(this.redisKeyPrefix,currDate);
			}
			return resetNo;
		}
	}

	/**
	 * 订单编号生成器
	 */
	class OrderNoCreator extends AbstractNoCreator{
		public OrderNoCreator(UniqueResource uniqueResource) {
			super(uniqueResource);
		}
	}


	/**
	 * 物流编号生成器
	 */
	class LogisticsNoCreator extends AbstractNoCreator{
		public LogisticsNoCreator(UniqueResource uniqueResource) {
			super(uniqueResource);
		}
	}


	/**
	 * 支付记录编号生成器
	 */
	class PaidRecordsNoCreator extends AbstractNoCreator{
		public PaidRecordsNoCreator(UniqueResource uniqueResource) {
			super(uniqueResource);
		}
	}

	/**
	 * 商户系统内部订单号
	 */
	class TradeNoCreator extends OrderNoCreator{
		public TradeNoCreator(UniqueResource uniqueResource) {
			super(uniqueResource);
		}
	}

	/**
	 * 生成企业动态的文章id
	 */
	class NewsIdCreator extends AbstractNoCreator{
		public NewsIdCreator(UniqueResource uniqueResource) {
			super(uniqueResource);
		}
	}

	public enum UniqueResource{
		ORDER_NO, // 订单编号
		LOGISTICS_NO, // 物流编号
		PAID_RECORDS_NO,// 支付记录编号
		TRADE_NO,//商户系统内部订单号
		NEWS_ID			// 企业动态的文章id
	}
}
