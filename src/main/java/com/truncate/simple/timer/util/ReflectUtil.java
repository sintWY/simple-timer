package com.truncate.simple.timer.util;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: 反射工具类
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 17:38
 */
public class ReflectUtil
{

	private static final Logger logger = LoggerFactory.getLogger(ReflectUtil.class);

	/**
	 *@描述：根据类名反射对象
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:41
	 *
	 */
	public static <T extends Job> T reflectObject(String className)
	{
		if(StringUtils.isEmpty(className))
		{
			throw new IllegalArgumentException("实现类不能为空!");
		}
		try
		{
			T t = (T) Class.forName(className).newInstance();
			return t;
		}
		catch(ClassNotFoundException e)
		{
			logger.error("实现类[" + className + "]可能不存在!", e);
		}
		catch(IllegalAccessException e)
		{
			logger.error("实现类[" + className + "]初始化失败!", e);
		}
		catch(InstantiationException e)
		{
			logger.error("实现类[" + className + "]初始化失败!", e);
		}
		throw new RuntimeException("反射得到的对象为空!");
	}
}
