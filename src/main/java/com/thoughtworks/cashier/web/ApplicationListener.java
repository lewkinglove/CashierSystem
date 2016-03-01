package com.thoughtworks.cashier.web;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.thoughtworks.cashier.ConfigPool;

/**
 * 应用启动监听器 
 * @author liujing(lewkinglove@gmail.com)
 */
@WebListener("ServletContextListener")
public class ApplicationListener implements ServletContextListener{

	@Override
    public void contextInitialized(ServletContextEvent sce) {
	    try {
	        ConfigPool.getInstance().init();
	        
        } catch (Exception e) {
	        e.printStackTrace();
        }
    }

	@Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
