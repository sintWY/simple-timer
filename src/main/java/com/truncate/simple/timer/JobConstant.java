package com.truncate.simple.timer;

/**
 * 描述: 常量
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 0:01
 */
public class JobConstant
{

	//任务类型
	public static final class JobType
	{

		//简单任务
		public static final String SIMPLE_JOB_TYPE = "0";

		//标准任务
		public static final String STANDARD_JOB_TYPE = "1";

	}

	public static final class XmlTag
	{

		//job名称tag
		public static final String JOB_NAME_TAG = "jobName";

		//任务组tag
		public static final String JOB_GROUP_TAG = "jobGroup";

		//任务开始时间tag
		public static final String JOB_START_TIME_TAG = "jobStartTime";

		//任务类型tag
		public static final String JOB_TYPE_TAG = "jobType";

		//任务实现类tag
		public static final String JOB_CLASS_NAME_TAG = "jobClassName";

		//任务cron表达式tag
		public static final String JOB_CRON_EXPRESS_TAG = "jobCronExpress";

		//任务状态tag
		public static final String JOB_STATUS_TAG = "jobStatus";

		//任务描述tag
		public static final String JOB_DESCRIPTION_TAG = "jobDescription";

		//任务重复次数
		public static final String JOB_REPEAT_COUNT_TAG = "jobRepeatCount";

		//任务间隔时间
		public static final String JOB_INTERVAL_SECOND_TAG = "jobIntervalSecond";
	}
}
