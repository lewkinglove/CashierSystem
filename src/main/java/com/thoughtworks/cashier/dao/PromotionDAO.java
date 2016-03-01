package com.thoughtworks.cashier.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;

import com.thoughtworks.cashier.common.db.BaseDao;
import com.thoughtworks.cashier.vo.Promotion;

/**
 * 营销活动对应的数据访问对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionDAO extends BaseDao<Promotion> {

	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "promotion";

	/**
	 * 主键列名
	 */
	public static final String PK_COLUMN_NAME = "id";

	@Override
	public String getPkName() {
		return PK_COLUMN_NAME;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean save(Promotion model) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public Promotion mappingRow(ResultSet rs) throws SQLException {
		Promotion item = new Promotion();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setStartTime(rs.getTimestamp("start_time"));
		item.setEndTime(rs.getTimestamp("end_time"));
		item.setSuitAll(rs.getInt("suit_all"));
		item.setPriority(rs.getInt("priority"));
		item.setPromotionType(rs.getInt("promotion_type"));
		item.setPromotionTypeArgs(rs.getString("promotion_type_args"));
		item.setStatus(rs.getInt("status"));
		item.setLastUpdate(rs.getTimestamp("last_update"));
		item.setCreateTime(rs.getTimestamp("create_time"));
		return item;
	}

}
