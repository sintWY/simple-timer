package com.truncate.simple.timer;

/**
 * ����: ��׼Job
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��24��
 * ����ʱ��: 23:56
 */
public class StandardJob extends BaseJob
{

	//cron���ʽ
	private String jobCronExpress;

	public StandardJob()
	{
		this.jobType = JobConstant.JobType.STANDARD_JOB_TYPE;
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
