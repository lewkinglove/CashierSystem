package com.thoughtworks.cashier.vo;

import java.util.Date;

/**
 * 商品类型
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class GoodType {
	/**
	 * 主键;自增ID;
	 */
	private int id;

	/**
	 * 商品类型名称
	 */
	private String name;

	/**
	 * 商品类型说明
	 */
	private String description;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
