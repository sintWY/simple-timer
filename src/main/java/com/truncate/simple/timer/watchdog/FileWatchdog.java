package com.truncate.simple.timer.watchdog;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 描述: 文件监听类
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0
 * 创建日期: 2017年03月25日
 * 创建时间: 0:52
 */
public class FileWatchdog extends Thread
{

	private static final Logger logger = LoggerFactory.getLogger(FileWatchdog.class);

	//需要监听的文件路径
	private String filePath;

	//文件最后修改时间
	private long lastModifyTime;

	//检测间隔时间
	private long delayTime;

	//文件
	private File file;

	//是否启动检测
	private volatile boolean isShutdown = false;

	//文件发生变动发生的逻辑 需要实现接口IChangeService
	private IChangeService changeService;

	public FileWatchdog(String filePath, long delayTime, IChangeService changeService)
	{
		this.filePath = filePath;
		this.delayTime = delayTime;
		this.file = new File(filePath);
		this.changeService = changeService;
		init();
	}

	private void init()
	{
		if(StringUtils.isEmpty(filePath))
		{
			isShutdown = true;
			throw new IllegalArgumentException("文件路径不能为空!");
		}
		if(this.file.exists())
		{
			lastModifyTime = file.lastModified();
		}
		else
		{
			isShutdown = true;
			throw new IllegalArgumentException("文件[" + filePath + "]不存在!");
		}
	}

	@Override
	public void run()
	{
		while(!isShutdown)
		{
			if(file.lastModified() > lastModifyTime)
			{
				//有过修改,出发对应的逻辑
				boolean success = changeService.doChange();
				if(success)
				{
					lastModifyTime = file.lastModified();
				}

				if(logger.isDebugEnabled())
				{
					logger.debug("文件[{}]内容发生变动，触发事件返回结果：{}", filePath, success);
				}

				try
				{
					Thread.sleep(delayTime * 1000);
				}
				catch(InterruptedException e)
				{
				}
			}
		}
	}
}
