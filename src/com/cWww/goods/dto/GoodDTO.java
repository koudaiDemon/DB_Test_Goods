package com.cWww.goods.dto;

import java.math.BigDecimal;
import java.util.Date;

public class GoodDTO {

	private int id;
	private String goodsname;
	private BigDecimal price;
	private double offset;
	private Date time;
	private int counts;
	private TypeDTO type;

	public GoodDTO() {
	}

	public GoodDTO(int id, String goodsname, BigDecimal price, double offset,
			Date time, int counts, TypeDTO type) {
		super();
		this.id = id;
		this.goodsname = goodsname;
		this.price = price;
		this.offset = offset;
		this.time = time;
		this.counts = counts;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public TypeDTO getType() {
		return type;
	}

	public void setType(TypeDTO type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "GoodDTO [id=" + id + ", goodsname=" + goodsname + ", price="
				+ price + ", offset=" + offset + ", time=" + time + ", counts="
				+ counts + ", type=" + type + "]";
	}

	
}
