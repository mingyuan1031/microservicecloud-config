package com.lwxf.newstore.webapp.domain.dto.goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能：
 *
 * @author：F_baisi
 * @create：2018/6/20/020 19:59
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class GoodsDefault {
	private String goodsId;
	private String name;
	private Date created;
	private String goodsTypeId;
	private boolean disabled;
	private String brandId;
	private BigDecimal originalprice;
	private BigDecimal price;
	private int sumSales;
	private String path;
	private String content;
	private BigDecimal freight;
	private boolean tops;
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getGoodsTypeId() {
		return goodsTypeId;
	}

	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}

	public boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}


	public BigDecimal getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(BigDecimal originalprice) {
		this.originalprice = originalprice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSumSales() {
		return sumSales;
	}

	public void setSumSales(int sumSales) {
		this.sumSales = sumSales;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public boolean isTops() {
		return tops;
	}

	public void setTops(boolean tops) {
		this.tops = tops;
	}

}
