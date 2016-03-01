package com.thoughtworks.cashier.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class Good {
	/**
	 * 主键;自增ID
	 */
	private int id;

	/**
	 * 识别码;条形码;唯一;
	 */
	private String code;

	/**
	 * 商品类型;外键:good_type.id
	 */
	private int type;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品单价
	 */
	private BigDecimal price;

	/**
	 * 数量单位; 如: 个、千克、支等
	 */
	private String countUnit;

	/**
	 * 状态;1:正常;2:禁用;3已删除;
	 */
	private int status;

	/**
	 * 更新时间
	 */
	private Date lastUpdate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCountUnit() {
		return countUnit;
	}

	public void setCountUnit(String countUnit) {
		this.countUnit = countUnit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
