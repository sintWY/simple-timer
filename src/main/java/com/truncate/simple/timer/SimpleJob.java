package com.truncate.simple.timer;

/**
 * 描述: 简单Job
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 23:56
 */
public class SimpleJob extends BaseJob
{

	//重复次数
	private int jobRepeatCount;

	//间隔时间 单位：秒
	private int jobIntervalSecond;

	public SimpleJob()
	{
		this.jobType = JobConstant.JobType.SIMPLE_JOB_TYPE;
	}

	public int getJobRepeatCount()
	{
		return jobRepeatCount;
	}

	public void setJobRepeatCount(int jobRepeatCount)
	{
		this.jobRepeatCount = jobRepeatCount;
	}

	public int getJobIntervalSecond()
	{
		return jobIntervalSecond;
	}

	public void setJobIntervalSecond(int jobIntervalSecond)
	{
		this.jobIntervalSecond = jobIntervalSecond;
	}
}
