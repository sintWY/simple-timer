package com.truncate.simple.timer;

import java.util.Date;

/**
 * 描述: job基础属性
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 23:57
 */
public class BaseJob
{

	//任务名称
	protected String jobName;

	//任务组
	protected String jobGroup;

	//job开始时间
	protected Date jobStartTime;

	//job类型
	protected String jobType;

	//job实现类
	protected String jobClassName;

	//job状态
	protected String jobStatus;

	//job描述
	protected String jobDescription;

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public String getJobGroup()
	{
		return jobGroup;
	}

	public void setJobGroup(String jobGroup)
	{
		this.jobGroup = jobGroup;
	}

	public Date getJobStartTime()
	{
		return jobStartTime;
	}

	public void setJobStartTime(Date jobStartTime)
	{
		this.jobStartTime = jobStartTime;
	}

	public String getJobType()
	{
		return jobType;
	}

	public String getJobClassName()
	{
		return jobClassName;
	}

	public void setJobClassName(String jobClassName)
	{
		this.jobClassName = jobClassName;
	}

	public String getJobStatus()
	{
		return jobStatus;
	}

	public void setJobStatus(String jobStatus)
	{
		this.jobStatus = jobStatus;
	}

	public String getJobDescription()
	{
		return jobDescription;
	}

	public void setJobDescription(String jobDescription)
	{
		this.jobDescription = jobDescription;
	}
}
