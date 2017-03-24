package com.truncate.simple.timer.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����: //TODO ������
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��25��
 * ����ʱ��: 1:08
 */
public class TestSimleJob implements Job
{

	private static final Logger logger = LoggerFactory.getLogger(TestSimleJob.class);

	private static int step = 0;

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
	{
		logger.info("TestSimleJob ��{}������...", ++step);
	}
}
