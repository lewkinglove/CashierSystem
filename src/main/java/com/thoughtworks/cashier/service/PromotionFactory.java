package com.thoughtworks.cashier.service;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

public final class PromotionFactory {

	private static Map<String, IPromotionProcesser> processers = new HashMap<String, IPromotionProcesser>();

	static {
		discoveryProcessers();
	}

	private static void discoveryProcessers() {
		String rootPkgPath = "com.thoughtworks.cashier.service.impl";
		try {
			// 扫描impl包下所有IPromotionProcesser的实现类
			ClassPath cp = ClassPath.from(PromotionFactory.class.getClassLoader());
			ImmutableSet<ClassInfo> list = cp.getTopLevelClassesRecursive(rootPkgPath);
			for (ClassInfo info : list) {
				String className = info.getName();
				String pkgName = info.getPackageName();

				// 验证当前指定的Class是否声明为对外提供服务的ServiceApi类
				Class<?> clazz = Class.forName(className);
				boolean isClassOk = clazz != null && IPromotionProcesser.class.isAssignableFrom(clazz) == true;
				if (isClassOk == false)
					continue;

				// 缓存当前的Processer实例
				IPromotionProcesser instance = (IPromotionProcesser) clazz.newInstance();
				registPromotionProcesser(instance.getPromotionTypeCode(), instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static IPromotionProcesser getProcesser(String promotionTypeCode) {
		return processers.get(promotionTypeCode);
	}

	public static void registPromotionProcesser(String promotionTypeCode, IPromotionProcesser newProcesser) {
		if (processers.containsKey(promotionTypeCode))
			throw new IllegalArgumentException("指定类型[" + promotionTypeCode + "]的优惠处理器已存在");
		processers.put(promotionTypeCode, newProcesser);
	}

}
