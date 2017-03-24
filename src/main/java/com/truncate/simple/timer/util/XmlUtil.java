package com.truncate.simple.timer.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 描述: xml工具类
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 23:36
 */
public class XmlUtil
{

	private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	/**
	 *@描述：从xml文件中后去Document对象
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:41
	 *
	 */
	public static Document getDocument(String filePath)
	{
		if(StringUtils.isNotEmpty(filePath))
		{
			File xmlFile = new File(filePath);
			if(xmlFile.exists())
			{
				SAXReader reader = new SAXReader();
				try
				{
					return reader.read(xmlFile);
				}
				catch(DocumentException e)
				{
					logger.error("读取下xml文件出错！", e);
				}
			}
			else
			{
				throw new IllegalArgumentException("文件[" + filePath + "]不存在!");
			}
		}
		else
		{
			throw new IllegalArgumentException("文件路径不能为空!");
		}
		throw new IllegalArgumentException("返回的文档对象为空!");
	}
}
