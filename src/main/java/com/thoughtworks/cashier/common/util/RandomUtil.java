package com.thoughtworks.cashier.common.util;

import java.util.Random;

/**
 * 随机数工具类
 * @author liujing(lewkinglove@gmail.com)
 */
public class RandomUtil {
	private  static Random random = null;
	static{
		RandomUtil.random = new Random(System.currentTimeMillis());
	}
	
	public static int getInt(){
		return RandomUtil.random.nextInt();
	}
	
	/**
	 * 返回0到指定数值之间的随机数(包含0, 但是不包含bound) 
	 * @param bound 最大值, 必须为正数
	 * @return
	 */
	public static int getInt(int bound){
		return RandomUtil.random.nextInt(bound);
	}
	/**
	 * 返回一个介于min和max直接的随机数(包含min和max)
	 * @param min	随机范围最小值, 必须为正数
	 * @param max	随机范围最大值, 必须为负数
	 * @return
	 */
	public static int getInt(int min, int max){
		return  RandomUtil.random.nextInt(max)%(max-min+1) + min;
	}
	
	public static boolean getBoolean(){
		return RandomUtil.random.nextBoolean();
	}
	
	public static float getFloat(){
		return RandomUtil.random.nextFloat();
	}
	
	public static double getDouble(){
		return RandomUtil.random.nextDouble();
	}
	
	public static long getLong(){
		return RandomUtil.random.nextLong();
	}
}
