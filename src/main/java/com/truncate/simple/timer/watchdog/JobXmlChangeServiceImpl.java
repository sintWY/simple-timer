package com.truncate.simple.timer.watchdog;

import com.truncate.simple.timer.JobManager;

/**
 * ����: job.xml�ļ��������´����¼�
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0
 * ��������: 2017��03��25��
 * ����ʱ��: 13:48
 */
public class JobXmlChangeServiceImpl implements IChangeService
{

	@Override
	public boolean doChange()
	{
		JobManager.reloadJob();
		return true;
	}
}
