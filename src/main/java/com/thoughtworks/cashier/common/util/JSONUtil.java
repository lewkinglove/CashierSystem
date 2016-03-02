package com.thoughtworks.cashier.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON工具类 
 * 为了避免重复初始化json parser造成的性能损失 
 * 特此编写此类进行包装和处理
 * @author liujing(lewkinglove@gmail.com)
 */
public class JSONUtil {
	private JSONUtil() {

	}

	public static Gson instance;

	static {
		// JsonUtil.instance = new Gson();
		JSONUtil.instance = new GsonBuilder()
				// .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
		        .serializeNulls() // 当需要序列化的值为空时，采用null映射，否则会把该字段省略
		        // .setDateFormat("yyyy-MM-dd HH:mm:ss") //日期格式转换
		        // .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		        // //将属性的首字母大写
		        // .setPrettyPrinting() //将结果进行格式化
		        .create();
	}

	public static String marshal(Object object) {
		return instance.toJson(object);
	}

	// 无法确定 <T>T 的类型参数；对于上限为 T,java.lang.Object 的类型变量 T，不存在唯一最大实例
	public static <T> T unmarshal(String json, Class<T> clazz) {
		return (T) instance.fromJson(json, clazz);
	}
}
