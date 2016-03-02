package com.thoughtworks.cashier;

import java.util.Properties;

import com.thoughtworks.cashier.common.db.DataSourcePool;
import com.thoughtworks.cashier.service.CashierTicketPrinter;

/**
 * 配置池
 * @author liujing(lewkinglove@gmail.com)
 */
public class ConfigPool extends Properties {
	private static final long serialVersionUID = 1L;

	private static ConfigPool instance;
	
	static {
		// 初始化单例对象
		ConfigPool.instance = new ConfigPool();
	}

	private ConfigPool() {
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static ConfigPool getInstance() {
		return ConfigPool.instance;
	}

	/**
	 * 当前项目的部署环境
	 */
	private String environment;

	public String getCurrentEnvironment() {
		return this.environment;
	}

	/**
	 * 初始化方法. 如果自己进行依赖管理, 则创建当前类之后必须调用本方法进行初始化.
	 * 如果使用Spring进行依赖管理,则需要配置当前方法为对应bean的初始化方法.
	 */
	public void init() throws Exception {
		// 初始化环境配置文件
		String envPropertiesPath = "configs/environment.properties";
		this.loadPropertiesFile(envPropertiesPath);
		this.environment = this.getProperty("config.environment");
		
		// 加载基础公共配置文件
		String basePropertiesPath = "configs/" + this.environment + "/base.properties";
		this.loadPropertiesFile(basePropertiesPath);
		
		// 加载数据库配置文件
		String dbPropertiesPath = "configs/" + this.environment + "/db.properties";
		this.loadPropertiesFile(dbPropertiesPath);
		DataSourcePool.init();	//初始化数据库相关链接数据源

		this.initExtraConfigs();	//初始化其他额外配置
	}
	
	private void initExtraConfigs(){
		CashierTicketPrinter.setShopPrintName(this.getProperty("config.base.shopName"));
	} 
	
	
	private void loadPropertiesFile(String filepath){
		try {
			this.load(ConfigPool.class.getClassLoader().getResourceAsStream(filepath));
		} catch (Exception e) {
			throw new Error("配置文件[" + filepath + "]未找到，系统无法启动！", e);
		}
	}
}
