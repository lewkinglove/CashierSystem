package com.thoughtworks.cashier.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;

import com.thoughtworks.cashier.common.db.BaseDao;
import com.thoughtworks.cashier.vo.PromotionType;

/**
 * 营销活动类型表对应的数据访问对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionTypeDAO extends BaseDao<PromotionType> {

	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "promotion_type";

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
	public boolean save(PromotionType model) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public PromotionType mappingRow(ResultSet rs) throws SQLException {
		PromotionType item = new PromotionType();
		item.setId(rs.getInt("id"));
		item.setCode(rs.getString("code"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		return item;
	}

}
