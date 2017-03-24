package com.truncate.simple.timer.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ����: ��������
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��25��
 * ����ʱ��: 0:37
 */
public class TestStandardJob implements Job
{

	public static final Logger logger = LoggerFactory.getLogger(TestStandardJob.class);

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("����ʱ�䣺{}", simpleDateFormat.format(new Date()));
	}
}
