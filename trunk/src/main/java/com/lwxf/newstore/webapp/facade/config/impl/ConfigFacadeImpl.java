package com.lwxf.newstore.webapp.facade.config.impl;


import sun.security.krb5.Config;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lwxf.commons.exception.ErrorCodes;
import com.lwxf.commons.utils.DateUtil;
import com.lwxf.commons.utils.LwxfStringUtils;
import com.lwxf.mybatis.annotation.Id;
import com.lwxf.mybatis.utils.MapContext;
import com.lwxf.newstore.webapp.baseservice.tsmanager.TSManualData;
import com.lwxf.newstore.webapp.bizservice.common.UploadFilesService;
import com.lwxf.newstore.webapp.bizservice.company.CompanyService;
import com.lwxf.newstore.webapp.bizservice.config.StoreConfigService;
import com.lwxf.newstore.webapp.bizservice.config.StoreHomeNavService;
import com.lwxf.newstore.webapp.bizservice.config.SystemConfigService;
import com.lwxf.newstore.webapp.common.component.UploadInfo;
import com.lwxf.newstore.webapp.common.component.UploadType;
import com.lwxf.newstore.webapp.common.constant.WebConstant;
import com.lwxf.newstore.webapp.common.enums.UploadResourceType;
import com.lwxf.newstore.webapp.common.enums.activity.SystemActivityEvent;
import com.lwxf.newstore.webapp.common.enums.cfgs.CfgsType;
import com.lwxf.newstore.webapp.common.result.RequestResult;
import com.lwxf.newstore.webapp.common.result.ResultFactory;
import com.lwxf.newstore.webapp.common.utils.WebUtils;
import com.lwxf.newstore.webapp.domain.entity.common.UploadFiles;
import com.lwxf.newstore.webapp.domain.entity.company.Company;
import com.lwxf.newstore.webapp.domain.entity.config.StoreConfig;
import com.lwxf.newstore.webapp.domain.entity.config.StoreHomeNav;
import com.lwxf.newstore.webapp.domain.entity.config.SystemConfig;
import com.lwxf.newstore.webapp.facade.AppBeanInjector;
import com.lwxf.newstore.webapp.facade.base.BaseFacadeImpl;
import com.lwxf.newstore.webapp.facade.config.ConfigFacade;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-06-15 16:10
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Component("configFacade")
public class ConfigFacadeImpl extends BaseFacadeImpl implements ConfigFacade {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Resource(name = "uploadFilesService")
	private UploadFilesService uploadFilesService;
	@Resource(name = "systemConfigService")
	private SystemConfigService systemConfigService;
	@Resource(name = "storeConfigService")
	private StoreConfigService storeConfigService;
	@Resource(name = "storeHomeNavService")
	private StoreHomeNavService storeHomeNavService;
	@Resource(name = "companyService")
	private CompanyService companyService;

	@Override
	@Transactional
	public RequestResult putSystemConfig(String cfgId, MapContext update) {
//		TSManualData tsManualData = WebUtils.getTSManualData();
//		tsManualData.setClazz(SystemConfig.class);
//		tsManualData.setParams(this.systemConfigService.findById(cfgId).getCompanyId());
//		tsManualData.setEvent(SystemActivityEvent.COMPANY_MD.getValue());
//		tsManualData.put(WebConstant.KEY_ENTITY_ID, this.systemConfigService.findById(cfgId).getCompanyId());
		SystemConfig systemConfig = this.systemConfigService.findById(cfgId);
		if(systemConfig == null){
			return ResultFactory.generateResNotFoundResult();
		}

		String companyId = WebUtils.getCurrCompanyId();
		if(!systemConfig.getCompanyId().equals(companyId)){
			return ResultFactory.generateResNotFoundResult();
		}

		update.put(WebConstant.KEY_ENTITY_ID,cfgId);

		// 更新系统配置
		this.systemConfigService.updateByMapContext(update);

		// 创建微信菜单
		String wxmenus = (String)update.get(WebConstant.KEY_ENTITY_WXMENUS);
		if(LwxfStringUtils.isNotBlank(wxmenus)){
			try {
				AppBeanInjector.wxMpService.getMenuService().menuCreate(wxmenus);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		// TODO：下边代码是解决什么问题的？ @张家乐
		StoreConfig storeConfig = this.storeConfigService.findByCompanyId(companyId);
		MapContext data = MapContext.newOne();
		data.put("id", systemConfig.getCompanyId());
		data.put("name", storeConfig.getName());
		this.companyService.updateByMapContext(data);
		return ResultFactory.generateRequestResult(systemConfig);
	}

	@Override
	@Transactional
	public RequestResult  putStoreConfig(String cfgId, MapContext update) {
//		TSManualData tsManualData = WebUtils.getTSManualData();
//		tsManualData.setClazz(StoreConfig.class);
//		tsManualData.setParams(this.storeConfigService.findById(cfgId).getCompanyId());
//		tsManualData.setEvent(SystemActivityEvent.COMPANY_MD.getValue());
//		tsManualData.put(WebConstant.KEY_ENTITY_ID, this.storeConfigService.findById(cfgId).getCompanyId());
		StoreConfig storeConfig = this.storeConfigService.findById(cfgId);
		if(storeConfig == null){
			return ResultFactory.generateResNotFoundResult();
		}
		update.put( WebConstant.KEY_ENTITY_ID,cfgId);
		this.storeConfigService.updateByMapContext(update);
		MapContext data = MapContext.newOne();
		data.put("id", storeConfig.getCompanyId());
		data.put("name", this.storeConfigService.findOneByLimit().getName());
		this.companyService.updateByMapContext(data);
		return ResultFactory.generateRequestResult(this.storeConfigService.findOneByLimit());
	}



	@Override
	public RequestResult findConfigData() {
		Map<String,Object> data = new HashMap<>();
		//将数据库中图片的地址改为项目中图片的链接
		data.put("syscfg", this.systemConfigService.findOneByLimit());
		data.put("storecfg", this.storeConfigService.findOneByLimit());
		return ResultFactory.generateRequestResult(data);
	}

	@Override
	@Transactional
	public RequestResult uploadStorecfgImage(String cfgId, MultipartFile file,Integer type){
		// 获取cfgId的数据
		StoreConfig storecfg=this.storeConfigService.findById(cfgId);
		// 判断数据是否存在
		if(storecfg == null){
			// 不存在返回错误信息
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
		}
		// 赋值给uploadinfo,进行数据处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL, file,UploadResourceType.STORECFG,cfgId);
		// TODO 1.先从UploadFiles 中查询是否已存在该图片
		UploadFiles uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(cfgId, cfgId, UploadResourceType.STORECFG.getType());
		// 查询不到就是null
		if(uploadFiles == null){
			// 没有数据,从新创建
		    uploadFiles = UploadFiles.create(cfgId, cfgId, uploadInfo, UploadResourceType.STORECFG, UploadType.FORMAL);
		    // 创建成功添加到数据库
		    this.uploadFilesService.add(uploadFiles);
		}else {
			// 否则
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(uploadFiles.getPath());
			MapContext updata = MapContext.newOne();
			updata.put(WebConstant.KEY_ENTITY_ID, uploadFiles.getId());
			updata.put("path", uploadInfo.getRelativePath());
			updata.put("name", uploadInfo.getFileName());
			updata.put("originalMime", uploadInfo.getFileMimeType().getOriginalType());
			updata.put("mime", uploadInfo.getFileMimeType().getRealType());
			updata.put("creator", WebUtils.getCurrUserId());
			updata.put("created", DateUtil.getSystemDate());
			this.uploadFilesService.updateByMapContext(updata);
		}
		MapContext mapContext = new MapContext();
		mapContext.put(WebConstant.KEY_ENTITY_ID, cfgId);
		mapContext.put(CfgsType.getDesc(type), uploadInfo.getRelativePath());
		this.storeConfigService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(this.storeConfigService.findById(cfgId));
	}

	@Override
	public RequestResult findHomeNavDatas() {
		return ResultFactory.generateRequestResult(this.storeHomeNavService.findHomeNavDatas());
	}

	@Override
	@Transactional
	public RequestResult putHomeNav(String navId, MultipartFile file) {
		StoreHomeNav homeNav=this.storeHomeNavService.findById(navId);
		if(homeNav == null){
			return ResultFactory.generateErrorResult(ErrorCodes.BIZ_RES_NOT_FOUND_10001, AppBeanInjector.i18nUtil.getMessage("BIZ_RES_NOT_FOUND_10001"));
		}
		// 赋值给uploadinfo,进行数据处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.FORMAL, file,UploadResourceType.STOREHOMENAV,navId);
		// TODO 1.先从UploadFiles 中查询是否已存在该图片
		UploadFiles uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(navId, navId, UploadResourceType.STOREHOMENAV.getType());
		if(uploadFiles == null){
			uploadFiles = UploadFiles.create(navId, navId, uploadInfo, UploadResourceType.STOREHOMENAV, UploadType.FORMAL);
			this.uploadFilesService.add(uploadFiles);
		}else {
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(uploadFiles.getPath());
			MapContext updata = MapContext.newOne();
			updata.put(WebConstant.KEY_ENTITY_ID, uploadFiles.getId());
			updata.put("path", uploadInfo.getRelativePath());
			updata.put("name", uploadInfo.getFileName());
			updata.put("originalMime", uploadInfo.getFileMimeType().getOriginalType());
			updata.put("mime", uploadInfo.getFileMimeType().getRealType());
			updata.put("creator", WebUtils.getCurrUserId());
			updata.put("created", DateUtil.getSystemDate());
			this.uploadFilesService.updateByMapContext(updata);
		}
		MapContext mapContext = new MapContext();
		mapContext.put(WebConstant.KEY_ENTITY_ID, navId);
		mapContext.put("image", uploadInfo.getRelativePath());
		this.storeHomeNavService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(this.storeHomeNavService.findById(navId));
	}

	@Override
	@Transactional
	public RequestResult updateMapContext(String navId, MapContext mapContext) {
		StoreHomeNav homeNav = this.storeHomeNavService.findById(navId);
		if(homeNav == null){
			return ResultFactory.generateResNotFoundResult();
		}
		mapContext.put(WebConstant.KEY_ENTITY_ID, navId);
		this.storeHomeNavService.updateByMapContext(mapContext);
		return ResultFactory.generateRequestResult(this.storeHomeNavService.findById(navId));
	}

	/*@Override
	public RequestResult postHomeNavImage(MultipartFile file, StoreHomeNav storeHomeNav) {
		this.storeHomeNavService.add(storeHomeNav);
		// 赋值给uploadinfo,进行数据处理
		UploadInfo uploadInfo = AppBeanInjector.baseFileUploadComponent.doUploadByModule(UploadType.TEMP, file,UploadResourceType.STOREHOMENAV,storeHomeNav.getId());
		// TODO 1.先从UploadFiles 中查询是否已存在该图片
		UploadFiles uploadFiles = this.uploadFilesService.findByBelongIdAndResourceIdAndResourceType(storeHomeNav.getId(), storeHomeNav.getId(), UploadResourceType.STOREHOMENAV.getType());
		if(uploadFiles == null){
			// 资源ID和所属ID
			uploadFiles = UploadFiles.create(storeHomeNav.getId(), storeHomeNav.getId(), uploadInfo, UploadResourceType.STOREHOMENAV, UploadType.TEMP);
			this.uploadFilesService.add(uploadFiles);
		}else {
			AppBeanInjector.baseFileUploadComponent.deleteFileByDir(uploadFiles.getPath());
			MapContext updata = MapContext.newOne();
			updata.put(WebConstant.KEY_ENTITY_ID, uploadFiles.getId());
			updata.put("path", uploadInfo.getRelativePath());
			updata.put("name", uploadInfo.getFileName());
			updata.put("originalMime", uploadInfo.getFileMimeType().getOriginalType());
			updata.put("mime", uploadInfo.getFileMimeType().getRealType());
			updata.put("creator", WebUtils.getCurrUserId());
			updata.put("created", DateUtil.getSystemDate());
			this.uploadFilesService.updateByMapContext(updata);
		}
		storeHomeNav.setImage(uploadInfo.getRelativePath());
		this.storeHomeNavService.insert(storeHomeNav);
		return ResultFactory.generateRequestResult(uploadFiles);
	}*/
}
