package com.truncate.simple.timer;

/**
 * ����: ����
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��25��
 * ����ʱ��: 0:01
 */
public class JobConstant
{

	//��������
	public static final class JobType
	{

		//������
		public static final String SIMPLE_JOB_TYPE = "0";

		//��׼����
		public static final String STANDARD_JOB_TYPE = "1";

	}

	public static final class XmlTag
	{

		//job����tag
		public static final String JOB_NAME_TAG = "jobName";

		//������tag
		public static final String JOB_GROUP_TAG = "jobGroup";

		//����ʼʱ��tag
		public static final String JOB_START_TIME_TAG = "jobStartTime";

		//��������tag
		public static final String JOB_TYPE_TAG = "jobType";

		//����ʵ����tag
		public static final String JOB_CLASS_NAME_TAG = "jobClassName";

		//����cron���ʽtag
		public static final String JOB_CRON_EXPRESS_TAG = "jobCronExpress";

		//����״̬tag
		public static final String JOB_STATUS_TAG = "jobStatus";

		//��������tag
		public static final String JOB_DESCRIPTION_TAG = "jobDescription";

		//�����ظ�����
		public static final String JOB_REPEAT_COUNT_TAG = "jobRepeatCount";

		//������ʱ��
		public static final String JOB_INTERVAL_SECOND_TAG = "jobIntervalSecond";
	}
}
