package com.truncate.simple.timer.watchdog;

import com.truncate.simple.timer.JobManager;

/**
 * 描述:	job.xml文件的监听
 * 版权: Copyright (c) 2017
 * 作者: truncate(truncate@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月27日
 * 创建时间: 11:22
 */
public class JobXmlFileWatchdog extends FileWatchdog
{

	public JobXmlFileWatchdog(String filePath)
	{
		super(filePath);
	}

	@Override
	boolean doChange()
	{
		JobManager.reloadJob();
		return true;
	}
}
