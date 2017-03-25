package com.truncate.simple.timer;

import com.truncate.simple.timer.watchdog.FileWatchdog;
import com.truncate.simple.timer.watchdog.IChangeService;
import com.truncate.simple.timer.watchdog.JobXmlChangeServiceImpl;

/**
 * 描述:
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 0:39
 */
public class Main
{

	public static void main(String[] args) throws InterruptedException
	{
		JobManager.startJob();

		IChangeService changeService = new JobXmlChangeServiceImpl();
		FileWatchdog fileWatchdog = new FileWatchdog("D:\\idea_workspace\\simpletimer\\target\\classes\\job.xml", 20, changeService);
		fileWatchdog.start();
	}
}
