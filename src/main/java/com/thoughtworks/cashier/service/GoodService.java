package com.thoughtworks.cashier.service;

import java.util.List;

import com.thoughtworks.cashier.dao.GoodDAO;
import com.thoughtworks.cashier.vo.Good;

/**
 * 商品信息服务对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class GoodService {

	private GoodDAO goodDao;

	private static GoodService instance = new GoodService();

	private GoodService() {
		this.goodDao = new GoodDAO();
	}

	public static GoodService getInstance() {
		return instance;
	}

	public Good getByID(int id) throws Exception {
		return goodDao.findByPk(id);
	}

	/**
	 * 获取商品信息码中的真实商品码
	 * 
	 * @param code
	 *            要处理的标签条码
	 * @return
	 */
	public String getRealCode(String code) {
		int pos = code.indexOf('-');
		if (pos != -1) {
			String[] codeParts = code.split("-");
			if (codeParts.length != 2)
				throw new IllegalArgumentException("商品条形码(伪码)格式不正确");
			code = codeParts[0];
		}
		return code;
	}

	public Good getByCode(String code) throws Exception {
		if (code == null || code.trim().length() == 0)
			throw new IllegalArgumentException("商品条形码不能为空");

		// 处理伪码
		code = getRealCode(code);

		Good result = getByCodeFromCache(code);
		if (result == null) {
			List<Good> goods = goodDao.findByProperty("code", code);
			result = goods != null && goods.size() > 0 ? goods.get(0) : null;
			if (result != null)
				setGoodToCache(result);
		}
		return result;
	}

	/**
	 * 使用商品条形码从缓存中获取商品
	 * 
	 * @param code
	 *            不支持伪码
	 * @return
	 */
	public Good getByCodeFromCache(String code) {
		// 如果数据量小可以使用MAP来实现
		// 数据量大且需要定期失效情况使用Redis的HASH结构实现

		// 避免外围代码太多, 此处仅提供伪代码
		// Jedis cache = RedisPool.getReader();
		// String jsonStr = cache.hget("good", code);
		// if(jsonStr==null || jsonStr.length()==0)
		// return null;
		// return JSONUtil.unmarshal(jsonStr, Good.class);

		return null;
	}

	/**
	 * 将指定商品信息添加到缓存中
	 * 
	 * @param item
	 */
	public void setGoodToCache(Good item) {
		// 避免外围代码太多, 此处仅提供伪代码
		// Jedis cache = RedisPool.getWriter();
		// cache.hset("good", item.getCode(), JSONUtil.marshal(item));
	}
}
