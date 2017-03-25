package com.truncate.simple.timer;

import com.truncate.simple.timer.watchdog.FileWatchdog;
import com.truncate.simple.timer.watchdog.IChangeService;
import com.truncate.simple.timer.watchdog.JobXmlChangeServiceImpl;

/**
 * ����:
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��25��
 * ����ʱ��: 0:39
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
