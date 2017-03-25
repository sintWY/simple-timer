package com.truncate.simple.timer;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.Date;

/**
 * ����: job��������
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��24��
 * ����ʱ��: 23:57
 */
public class BaseJob
{

	//��������
	private String jobName;

	//������
	private String jobGroup;

	//job��ʼʱ��
	private Date jobStartTime;

	//job����
	private String jobType;

	//jobʵ����
	private String jobClassName;

	//job״̬
	private String jobStatus;

	//job����
	private String jobDescription;

	//��������
	private JobDetail jobDetail;

	//���񴥷���
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
