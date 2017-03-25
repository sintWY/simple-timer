package com.truncate.simple.timer;

/**
 * ����: ��Job
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��24��
 * ����ʱ��: 23:56
 */
public class SimpleJob extends BaseJob
{

	//�ظ�����
	private int jobRepeatCount;

	//���ʱ�� ��λ����
	private int jobIntervalSecond;

	public SimpleJob()
	{
		setJobType(JobConstant.JobType.SIMPLE_JOB_TYPE);
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
