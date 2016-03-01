package com.thoughtworks.cashier.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;

import com.thoughtworks.cashier.common.db.BaseDao;
import com.thoughtworks.cashier.vo.Good;

/**
 * 商品信息表对应的数据访问对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class GoodDAO extends BaseDao<Good>{
	
	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "good";
	
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
    public boolean save(Good model) throws Exception {
	    throw new NotImplementedException();
    }

	@Override
    public Good mappingRow(ResultSet rs) throws SQLException {
	    Good item = new Good();
	    item.setId(rs.getInt("id"));
	    item.setCode(rs.getString("code"));
	    item.setType(rs.getInt("type"));
	    item.setName(rs.getString("name"));
	    item.setPrice(rs.getBigDecimal("price"));
	    item.setCountUnit(rs.getString("count_unit"));
	    item.setStatus(rs.getInt("status"));
	    item.setLastUpdate(rs.getTimestamp("last_update"));
	    item.setCreateTime(rs.getTimestamp("create_time"));
	    return item;
    }

}
