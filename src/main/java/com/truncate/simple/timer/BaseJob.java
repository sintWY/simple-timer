package com.truncate.simple.timer;

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
	protected String jobName;

	//������
	protected String jobGroup;

	//job��ʼʱ��
	protected Date jobStartTime;

	//job����
	protected String jobType;

	//jobʵ����
	protected String jobClassName;

	//job״̬
	protected String jobStatus;

	//job����
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
