package com.lwxf.newstore.webapp.domain.dto.cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 功能：购物车中需要显示的详细信息封装成VO
 *
 * @author：dell
 * @create：2018/6/28 10:26
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CartDto {

	private String id;//　购物车中添加的商品在购物车中的ID
	private String memberId;//　用户ID
	private String goodsId;//　商品ID，便于用户点击商品跳转到商品详情页
	private String extendId;//　规格ID
	private String name;//　商品名称
	private String options;//　商品规格
	private Integer stock;// 库存数量，加到最大位时不能再加，输入的数量大于库存剩余时，自动填写最大库存
	private Double price;//　商品单价
	private String bigimage;//　商品图片
	private String state;// 状态，用于添加到购物车时判断是否时可用状态下，不可用则不允许添加
	private Integer count;//　商品数量，用于计算总价
	private Date created;//　时间
	private BigDecimal freight;// 运费
	private boolean disabled;//商品上下架情况

	public CartDto() {
	}

	public CartDto(String id, String memberId, String goodsId, String extendId, String name, String options, Integer stock, Double price, String bigimage, String state, Integer count, Date created, BigDecimal freight) {
		this.id = id;
		this.memberId = memberId;
		this.goodsId = goodsId;
		this.extendId = extendId;
		this.name = name;
		this.options = options;
		this.stock = stock;
		this.price = price;
		this.bigimage = bigimage;
		this.state = state;
		this.count = count;
		this.created = created;
		this.freight = freight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBigimage() {
		return bigimage;
	}

	public void setBigimage(String bigimage) {
		this.bigimage = bigimage;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CartDto cartDto = (CartDto) o;
		return Objects.equals(id, cartDto.id) &&
				Objects.equals(memberId, cartDto.memberId) &&
				Objects.equals(goodsId, cartDto.goodsId) &&
				Objects.equals(extendId, cartDto.extendId) &&
				Objects.equals(name, cartDto.name) &&
				Objects.equals(options, cartDto.options) &&
				Objects.equals(stock, cartDto.stock) &&
				Objects.equals(price, cartDto.price) &&
				Objects.equals(bigimage, cartDto.bigimage) &&
				Objects.equals(state, cartDto.state) &&
				Objects.equals(count, cartDto.count) &&
				Objects.equals(created, cartDto.created) &&
				Objects.equals(freight, cartDto.freight);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, memberId, goodsId, extendId, name, options, stock, price, bigimage, state, count, created, freight);
	}

	@Override
	public String toString() {
		return "CartDto{" +
				"id='" + id + '\'' +
				", memberId='" + memberId + '\'' +
				", goodsId='" + goodsId + '\'' +
				", extendId='" + extendId + '\'' +
				", name='" + name + '\'' +
				", options='" + options + '\'' +
				", stock=" + stock +
				", price=" + price +
				", bigimage='" + bigimage + '\'' +
				", state='" + state + '\'' +
				", count=" + count +
				", created=" + created +
				", freight=" + freight +
				'}';
	}
}
