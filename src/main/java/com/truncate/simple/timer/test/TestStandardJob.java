package com.truncate.simple.timer.test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述: 测试任务
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 0:37
 */
public class TestStandardJob implements Job
{

	public static final Logger logger = LoggerFactory.getLogger(TestStandardJob.class);

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("现在时间：{}", simpleDateFormat.format(new Date()));
	}
}
