package com.truncate.simple.timer.watchdog;

import com.truncate.simple.timer.JobManager;

/**
 * ����: job.xml�ļ��������´����¼�
 * ��Ȩ: Copyright (c) 2017
 * ��˾: ˼�ϿƼ� 
 * ����: ������(wanggj@thinkive.com)
 * �汾: 1.0 
 * ��������: 2017��03��25��
 * ����ʱ��: 13:52
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
