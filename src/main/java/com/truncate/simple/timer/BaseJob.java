package com.truncate.simple.timer;

import org.quartz.JobDetail;
import org.quartz.Trigger;

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
	private String jobName;

	//任务组
	private String jobGroup;

	//job开始时间
	private Date jobStartTime;

	//job类型
	private String jobType;

	//job实现类
	private String jobClassName;

	//job状态
	private String jobStatus;

	//job描述
	private String jobDescription;

	//任务详情
	private JobDetail jobDetail;

	//任务触发器
	private Trigger trigger;

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

	public void setJobType(String jobType)
	{
		this.jobType = jobType;
	}

	public void setJobDetail(JobDetail jobDetail)
	{
		this.jobDetail = jobDetail;
	}

	public void setTrigger(Trigger trigger)
	{
		this.trigger = trigger;
	}

	public JobDetail getJobDetail()
	{
		return jobDetail;
	}

	public Trigger getTrigger()
	{
		return trigger;
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
