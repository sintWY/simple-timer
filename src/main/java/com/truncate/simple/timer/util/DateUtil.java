package com.truncate.simple.timer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: 日期工具类
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 0:52
 */
public class DateUtil
{

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	//默认格式
	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 *@描述：格式化日期成指定格式
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:39
	 */
	public static String format(Date date, String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 *@描述：格式化日期为默认格式
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:40
	 *
	 */
	public static String format(Date date)
	{
		return DEFAULT_DATE_FORMAT.format(date);
	}

	/**
	 *@描述：将字符串按照指定格式转为日期
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:40
	 *
	 */
	public static Date parse(String date, String pattern)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try
		{
			return simpleDateFormat.parse(date);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("日期格式不正确！");
		}
	}

	/**
	 *@描述：将字符串按照默认格式转为日期
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:40
	 *
	 */
	public static Date parse(String date)
	{
		try
		{
			return DEFAULT_DATE_FORMAT.parse(date);
		}
		catch(ParseException e)
		{
			throw new IllegalArgumentException("日期格式不正确！");
		}
	}
}
