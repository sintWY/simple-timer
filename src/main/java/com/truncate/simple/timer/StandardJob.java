package com.truncate.simple.timer;

/**
 * 描述: 标准Job
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 23:56
 */
public class StandardJob extends BaseJob
{

	//cron表达式
	private String jobCronExpress;

	public StandardJob()
	{
		setJobType(JobConstant.JobType.STANDARD_JOB_TYPE);
	}

	public String getJobCronExpress()
	{
		return jobCronExpress;
	}

	public void setJobCronExpress(String jobCronExpress)
	{
		this.jobCronExpress = jobCronExpress;
	}
}
