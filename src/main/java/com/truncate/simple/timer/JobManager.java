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
	private static Scheduler scheduler = initScheduler();

	static
	{
		loadJobXml();
	}

	private static Scheduler initScheduler()
	{
		try
		{
			Scheduler instance = new StdSchedulerFactory().getScheduler();
			return instance;
		}
		catch(SchedulerException e)
		{
			logger.warn("��ʼ��������ȶ���ʧ��!", e);
		}
		throw new IllegalArgumentException("���صĵ��ȶ���Ϊ��!");
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
		if(logger.isDebugEnabled())
		{
			logger.debug("����job�����ļ���{}", xmlFilePath);
		}
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
					simpleJob.setJobDetail(jobDetail);
					simpleJob.setTrigger(trigger);
					JOB_MAP.put(jobName, simpleJob);
					if(logger.isDebugEnabled())
					{
						logger.debug("������������� [{}] �ɹ�!", jobName);
					}
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
					JobDetail jobDetail = JobBuilder.newJob(ReflectUtil.reflectObject(standardJob.getJobClassName()).getClass()).withIdentity(standardJob.getJobName(), standardJob.getJobGroup())
							.withDescription(standardJob.getJobDescription()).build();
					CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(standardJob.getJobName(), standardJob.getJobGroup()).startAt(standardJob.getJobStartTime())
							.withSchedule(CronScheduleBuilder.cronSchedule(standardJob.getJobCronExpress())).build();
					standardJob.setJobDetail(jobDetail);
					standardJob.setTrigger(trigger);
					JOB_MAP.put(jobName, standardJob);
					if(logger.isDebugEnabled())
					{
						logger.debug("������������� [{}] �ɹ�!", jobName);
					}
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
				scheduleJob(job.getJobName(), job.getJobDetail(), job.getTrigger());
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
	 *@�������ر���������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:9:22
	 *
	 */
	public static void stopJob()
	{
		try
		{
			logger.info("����������е���������");
			scheduler.clear();
		}
		catch(SchedulerException e)
		{
			logger.error("�ر���������ʧ��!", e);
		}
	}

	/**
	 *@��������ͣĳ������ ��resumeJob�ָ�
	 * 		�ָ���˲��Ὣ��ͣ�ڼ�Ӧ��ִ�е�����һ����ִ����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:9:26
	 *
	 */
	public static void pauseJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("��ͣ����[{}]...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		JobDetail jobDetail = job.getJobDetail();
		try
		{
			scheduler.pauseJob(jobDetail.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("��ͣ����[" + jobName + "]ʧ��!", e);
		}
	}

	/**
	 *@�������ָ�ĳ������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:9:40
	 *
	 */
	public static void resumeJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("�ָ�����[{}]...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		JobDetail jobDetail = job.getJobDetail();
		try
		{
			job.getTrigger().getTriggerBuilder().startAt(new Date());
			scheduler.resumeJob(jobDetail.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("�ָ�����[" + jobName + "]ʧ��!", e);
		}
	}

	/**
	 *@��������ͣĳ������Ĵ�����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:10:31
	 *
	 */
	public static void pauseTrigger(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("��ͣ����[" + jobName + "]�Ĵ�����...");
		}
		BaseJob job = JOB_MAP.get(jobName);
		Trigger trigger = job.getTrigger();
		try
		{
			scheduler.pauseTrigger(trigger.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("��ͣ����[" + jobName + "]������ʧ��!", e);
		}
	}

	/**
	 *@����: �ָ�ĳ������Ĵ�����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:10:30
	 *
	 */
	public static void resumeTrigger(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("�ָ�����[" + jobName + "]�Ĵ�����...");
		}
		BaseJob job = JOB_MAP.get(jobName);
		Trigger trigger = job.getTrigger();
		try
		{
			scheduler.resumeTrigger(trigger.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("�ָ�����[" + jobName + "]������ʧ��!", e);
		}
	}

	/**
	 *@������ȡ����������еĴ�����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:10:49
	 *
	 */
	public static void deleteJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("����[{}]�ӵ�������ɾ��...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		try
		{
			scheduler.unscheduleJob(job.getTrigger().getKey());
			scheduler.deleteJob(job.getJobDetail().getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("�ӵ�������ɾ������ʧ��!", e);
		}
	}

	/**
	 *@�������������񵽵�����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:11:18
	 *
	 */
	public static void addJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("�������[{}]��������...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		job.setTrigger(job.getTrigger().getTriggerBuilder().startAt(new Date()).build());
		scheduleJob(job.getJobName(), job.getJobDetail(), job.getTrigger());
	}

	/**
	 *@�������������ô�����
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:12:59
	 *
	 */
	public static void reschedule(String jobName)
	{
		BaseJob job = JOB_MAP.get(jobName);
		try
		{
			//��Чʱ�����óɴ˿�
			job.getTrigger().getTriggerBuilder().startAt(new Date());
			scheduler.rescheduleJob(job.getTrigger().getKey(), job.getTrigger());
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *@������ע���������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:1:43
	 *
	 */
	public static void scheduleJob(String jobName, JobDetail jobDetail, Trigger trigger)
	{
		try
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("����[{}]ע�ᵽ��������...", jobName);
			}
			scheduler.scheduleJob(jobDetail, trigger);
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
			if(logger.isInfoEnabled())
			{
				logger.info("�������������...");
			}
			if(!scheduler.isStarted())
			{
				scheduler.start();
			}
		}
		catch(SchedulerException e)
		{
			logger.error("�������������ʧ��!", e);
		}
	}

	/**
	 *@��������ȡָ�����������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:12:46
	 *
	 */
	public static BaseJob getJobBean(String jobName)
	{
		if(JOB_MAP.containsKey(jobName))
		{
			return JOB_MAP.get(jobName);

		}
		return null;
	}

	/**
	 *@���������¼�����������
	 *@����:truncate(wy940407@163.com)
	 *@����:2017/3/25
	 *@ʱ��:13:31
	 *
	 */
	public static void reloadJob()
	{
		logger.info("���¼��������ļ��е����������...");
		if(!JOB_MAP.isEmpty())
		{
			JOB_MAP.clear();
		}
		//ֹͣ��������
		stopJob();
		//���¼���xml
		loadJobXml();
		//�������е�����
		startJob();
	}
}
