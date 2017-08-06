package com.cWww.excel;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
	
	private int gno;	        //	id
	private String gname; 		// ��Ʒ��
	private BigDecimal price;   //����
	private double offset;	    //�ۿ���
	private Date time;		    //����
	private int count;		    //���

	public Goods() {
	
	}

	public Goods(int gno, String gname, BigDecimal price, double offset,
			Date time, int count) {
		super();
		this.gno = gno;
		this.gname = gname;
		this.price = price;
		this.offset = offset;
		this.time = time;
		this.count = count;
	}

	public int getGno() {
		return gno;
	}

	public void setGno(int gno) {
		this.gno = gno;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Goods [gno=" + gno + ", gname=" + gname + ", price=" + price
				+ ", offset=" + offset + ", time=" + time + ", count=" + count
				+ "]";
	}
	
}
