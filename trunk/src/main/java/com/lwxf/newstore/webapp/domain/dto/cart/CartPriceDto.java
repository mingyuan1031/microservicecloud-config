package com.lwxf.newstore.webapp.domain.dto.cart;

import java.util.Date;
import java.util.Objects;

/**
 * 功能：
 *
 * @author：dell
 * @create：2018/7/9 11:50
 * @version：2018 Version：1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
public class CartPriceDto {

	private String id;
	private String goodsId;
	private String memberId;
	private String extendId;
	private Double price;
	private Integer count;
	private Date created;

	public CartPriceDto() {
	}

	public CartPriceDto(String id, String goodsId, String memberId, String extendId, Double price, Integer count, Date created) {
		this.id = id;
		this.goodsId = goodsId;
		this.memberId = memberId;
		this.extendId = extendId;
		this.price = price;
		this.count = count;
		this.created = created;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getExtendId() {
		return extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CartPriceDto that = (CartPriceDto) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(goodsId, that.goodsId) &&
				Objects.equals(memberId, that.memberId) &&
				Objects.equals(extendId, that.extendId) &&
				Objects.equals(price, that.price) &&
				Objects.equals(count, that.count) &&
				Objects.equals(created, that.created);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, goodsId, memberId, extendId, price, count, created);
	}

	@Override
	public String toString() {
		return "CartPriceDto{" +
				"id='" + id + '\'' +
				", goodsId='" + goodsId + '\'' +
				", memberId='" + memberId + '\'' +
				", extendId='" + extendId + '\'' +
				", price=" + price +
				", count=" + count +
				", created=" + created +
				'}';
	}
}
