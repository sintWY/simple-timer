package com.truncate.simple.timer.watchdog;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 描述: 文件监听类 默认是根据文件的修改事件判断是否变化
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0
 * 创建日期: 2017年03月25日
 * 创建时间: 0:52
 */
public abstract class FileWatchdog extends WatchdogListening
{

	private static final Logger logger = LoggerFactory.getLogger(FileWatchdog.class);

	//需要监听的文件路径
	private String filePath;

	//文件最后修改时间
	private long lastModifyTime;

	//文件
	private File file;

	public FileWatchdog(String filePath)
	{
		this.filePath = filePath;
		init();
	}

	private void init()
	{
		if(StringUtils.isEmpty(filePath))
		{
			throw new IllegalArgumentException("文件名称不能为空!");
		}
		this.file = new File(filePath);
		if(!file.exists())
		{
			throw new IllegalArgumentException("文件[" + filePath + "]不存在!");
		}
		this.lastModifyTime = file.lastModified();
	}

	@Override
	boolean isChange()
	{
		boolean isChange = file.lastModified() > lastModifyTime;
		if(logger.isDebugEnabled())
		{
			logger.debug("文件[" + filePath + "]内容是否发生变动：" + isChange);
		}
		return isChange;
	}
}
