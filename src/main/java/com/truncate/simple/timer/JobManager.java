package com.truncate.simple.timer;

import com.truncate.simple.timer.util.DateUtil;
import com.truncate.simple.timer.util.ReflectUtil;
import com.truncate.simple.timer.util.XmlUtil;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * ����: ���������
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0 
 * ��������: 2017��03��24��
 * ����ʱ��: 23:43
 */
public class JobManager
{

	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);

	//xml�ļ���
	private static final String TASK_XML_NAME = "job.xml";

	//�����
	private static final Map<String, BaseJob> JOB_MAP = new HashMap<String, BaseJob>();

	//����ִ�й���
	private static final SchedulerFactory SCHEDULER_FACTORY = new StdSchedulerFactory();

	static
	{
		loadJobXml();
	}

	/**
	 *@����������job�������ļ�
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:1:42
	 *
	 */
	public static void loadJobXml()
	{
		String xmlFilePath = JobManager.class.getResource("/").getPath() + TASK_XML_NAME;
		Document document = XmlUtil.getDocument(xmlFilePath);
		Element rootElement = document.getRootElement();
		List<Element> elementList = rootElement.elements("job");
		if(elementList != null && !elementList.isEmpty())
		{
			Iterator<Element> iterator = elementList.iterator();
			while(iterator.hasNext())
			{
				Element element = iterator.next();
				String jobName = element.elementText(JobConstant.XmlTag.JOB_NAME_TAG);
				String jobGroup = element.elementText(JobConstant.XmlTag.JOB_GROUP_TAG);
				String jobStartTime = element.elementText(JobConstant.XmlTag.JOB_START_TIME_TAG);
				String jobType = element.elementText(JobConstant.XmlTag.JOB_TYPE_TAG);
				String jobClassName = element.elementText(JobConstant.XmlTag.JOB_CLASS_NAME_TAG);
				String jobCronExpress = element.elementText(JobConstant.XmlTag.JOB_CRON_EXPRESS_TAG);
				String jobStatus = element.elementText(JobConstant.XmlTag.JOB_STATUS_TAG);
				String jobDescription = element.elementText(JobConstant.XmlTag.JOB_DESCRIPTION_TAG);
				String jobRepeatCount = element.elementText(JobConstant.XmlTag.JOB_REPEAT_COUNT_TAG);
				String jobIntervalSecond = element.elementText(JobConstant.XmlTag.JOB_INTERVAL_SECOND_TAG);

				if(JobConstant.JobType.SIMPLE_JOB_TYPE.equals(jobType))
				{
					SimpleJob simpleJob = new SimpleJob();
					simpleJob.setJobName(jobName);
					simpleJob.setJobGroup(jobGroup);
					if("0".equals(jobStartTime) || StringUtils.isEmpty(jobStartTime))
					{
						simpleJob.setJobStartTime(new Date());
					}
					else
					{
						simpleJob.setJobStartTime(DateUtil.parse(jobStartTime));
					}
					simpleJob.setJobClassName(jobClassName);
					simpleJob.setJobStatus(jobStatus);
					simpleJob.setJobIntervalSecond(Integer.valueOf(jobIntervalSecond));
					simpleJob.setJobRepeatCount(Integer.valueOf(jobRepeatCount));
					simpleJob.setJobDescription(jobDescription);
					JOB_MAP.put(jobName, simpleJob);
					logger.info("������������� [{}] �ɹ�!", jobName);
				}
				else if(JobConstant.JobType.STANDARD_JOB_TYPE.equals(jobType))
				{
					StandardJob standardJob = new StandardJob();
					standardJob.setJobName(jobName);
					standardJob.setJobGroup(jobGroup);
					if("0".equals(jobStartTime) || StringUtils.isEmpty(jobStartTime))
					{
						standardJob.setJobStartTime(new Date());
					}
					else
					{
						standardJob.setJobStartTime(DateUtil.parse(jobStartTime));
					}
					standardJob.setJobClassName(jobClassName);
					standardJob.setJobCronExpress(jobCronExpress);
					standardJob.setJobStatus(jobStatus);
					standardJob.setJobDescription(jobDescription);
					JOB_MAP.put(jobName, standardJob);
					logger.info("���ر�׼���� [{}] �ɹ�!", jobName);
				}
				else
				{
					logger.error("��֧�ֵ���������[{}]", jobType);
				}
			}
		}
		else
		{
			logger.warn("task.xml��û����������!");
		}
	}

	/**
	 *@������������ʱ����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:1:42
	 *
	 */
	public static void startJob()
	{
		if(!JOB_MAP.isEmpty())
		{
			Set<Map.Entry<String, BaseJob>> entrySet = JOB_MAP.entrySet();
			Iterator<Map.Entry<String, BaseJob>> iterator = entrySet.iterator();
			while(iterator.hasNext())
			{
				Map.Entry<String, BaseJob> entry = iterator.next();
				BaseJob job = entry.getValue();
				String jobType = job.getJobType();
				if(JobConstant.JobType.SIMPLE_JOB_TYPE.equals(jobType))
				{
					SimpleJob simpleJob = (SimpleJob) job;
					JobDetail jobDetail = JobBuilder.newJob(ReflectUtil.reflectObject(simpleJob.getJobClassName()).getClass()).withIdentity(simpleJob.getJobName(), simpleJob.getJobGroup())
							.withDescription(simpleJob.getJobDescription()).build();
					SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(simpleJob.getJobIntervalSecond());
					if(simpleJob.getJobRepeatCount() >= 1)
					{
						//quartz �������еĴ���=�ظ�����+1
						simpleScheduleBuilder.withRepeatCount(simpleJob.getJobRepeatCount() - 1);
					}
					else
					{
						//�ظ�����С�ڵ���0����һֱ����
						simpleScheduleBuilder.repeatForever();
					}
					SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(simpleJob.getJobName(), simpleJob.getJobGroup()).startAt(simpleJob.getJobStartTime())
							.withSchedule(simpleScheduleBuilder).build();
					scheduleJob(simpleJob.getJobName(), jobDetail, trigger);
				}
				else if(JobConstant.JobType.STANDARD_JOB_TYPE.equals(jobType))
				{
					StandardJob standardJob = (StandardJob) job;
					JobDetail jobDetail = JobBuilder.newJob(ReflectUtil.reflectObject(standardJob.getJobClassName()).getClass()).withIdentity(standardJob.getJobName(), standardJob.getJobGroup())
							.withDescription(standardJob.getJobDescription()).build();
					CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(standardJob.getJobName(), standardJob.getJobGroup()).startAt(standardJob.getJobStartTime())
							.withSchedule(CronScheduleBuilder.cronSchedule(standardJob.getJobCronExpress())).build();
					scheduleJob(standardJob.getJobName(), jobDetail, trigger);
				}
			}

			//����������
			startSchedule();
		}
		else
		{
			logger.warn("�������û������!");
		}
	}

	/**
	 *@������ע���������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:1:43
	 *
	 */
	private static void scheduleJob(String jobName, JobDetail jobDetail, Trigger trigger)
	{
		try
		{
			SCHEDULER_FACTORY.getScheduler().scheduleJob(jobDetail, trigger);
		}
		catch(SchedulerException e)
		{
			logger.error("��������[" + jobName + "]ʧ��!", e);
		}
	}

	/**
	 *@�����������������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:1:43
	 *
	 */
	private static void startSchedule()
	{
		try
		{
			SCHEDULER_FACTORY.getScheduler().start();
		}
		catch(SchedulerException e)
		{
			logger.error("�������������ʧ��!", e);
		}
	}
}
