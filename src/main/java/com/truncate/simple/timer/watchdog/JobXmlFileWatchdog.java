package com.truncate.simple.timer.watchdog;

import com.truncate.simple.timer.JobManager;

/**
 * ����:	job.xml�ļ��ļ���
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(truncate@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��27��
 * ����ʱ��: 11:22
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
