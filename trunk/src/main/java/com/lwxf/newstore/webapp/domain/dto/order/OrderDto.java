package com.lwxf.newstore.webapp.domain.dto.order;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lwxf.mybatis.annotation.Column;
import com.lwxf.mybatis.utils.TypesExtend;
import com.lwxf.newstore.webapp.domain.entity.order.Address;
import com.lwxf.newstore.webapp.domain.entity.order.Order;
import com.lwxf.newstore.webapp.domain.entity.order.OrderGoods;

/**
 * 功能：
 *
 * @author：Administrator
 * @create：2018/6/28 10:44
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class OrderDto extends Order {
	private List<GoodsDto>  goodsDtos=new ArrayList<>();
	private Address address;
	public List<GoodsDto> getGoodsDtos() {
		return goodsDtos;
	}

	public void setGoodsDtos(List<GoodsDto> goodsDtos) {
		this.goodsDtos = goodsDtos;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
