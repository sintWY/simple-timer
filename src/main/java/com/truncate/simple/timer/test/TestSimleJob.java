package com.truncate.simple.timer.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: //TODO 类描述
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 1:08
 */
public class TestSimleJob implements Job
{

	private static final Logger logger = LoggerFactory.getLogger(TestSimleJob.class);

	private static int step = 0;

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
	{
		logger.info("TestSimleJob 第{}次运行...", ++step);
	}
}
