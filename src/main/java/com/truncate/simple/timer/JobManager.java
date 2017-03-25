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
 * 描述: 任务管理器
 * 版权: Copyright (c) 2017
 * 作者: truncate(wy940407@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月24日
 * 创建时间: 23:43
 */
public class JobManager
{

	private static final Logger logger = LoggerFactory.getLogger(JobManager.class);

	//xml文件名
	private static final String TASK_XML_NAME = "job.xml";

	//任务池
	private static final Map<String, BaseJob> JOB_MAP = new HashMap<String, BaseJob>();

	//任务执行工厂
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
			logger.warn("初始化任务调度对象失败!", e);
		}
		throw new IllegalArgumentException("返回的调度对象为空!");
	}

	/**
	 *@描述：加载job的配置文件
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:42
	 *
	 */
	public static void loadJobXml()
	{
		String xmlFilePath = JobManager.class.getResource("/").getPath() + TASK_XML_NAME;
		if(logger.isDebugEnabled())
		{
			logger.debug("加载job配置文件：{}", xmlFilePath);
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
						//quartz 任务运行的次数=重复次数+1
						simpleScheduleBuilder.withRepeatCount(simpleJob.getJobRepeatCount() - 1);
					}
					else
					{
						//重复次数小于等于0，则一直运行
						simpleScheduleBuilder.repeatForever();
					}
					SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(simpleJob.getJobName(), simpleJob.getJobGroup()).startAt(simpleJob.getJobStartTime())
							.withSchedule(simpleScheduleBuilder).build();
					simpleJob.setJobDetail(jobDetail);
					simpleJob.setTrigger(trigger);
					JOB_MAP.put(jobName, simpleJob);
					if(logger.isDebugEnabled())
					{
						logger.debug("加载任务简单任务 [{}] 成功!", jobName);
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
						logger.debug("加载任务简单任务 [{}] 成功!", jobName);
					}
				}
				else
				{
					logger.error("不支持的任务类型[{}]", jobType);
				}
			}
		}
		else
		{
			logger.warn("task.xml中没有配置任务!");
		}
	}

	/**
	 *@描述：启动定时任务
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:42
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

			//启动调度器
			startSchedule();
		}
		else
		{
			logger.warn("任务池中没有任务!");
		}
	}

	/**
	 *@描述：关闭所有任务
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:9:22
	 *
	 */
	public static void stopJob()
	{
		try
		{
			logger.info("清除调度器中的所有任务");
			scheduler.clear();
		}
		catch(SchedulerException e)
		{
			logger.error("关闭所有任务失败!", e);
		}
	}

	/**
	 *@描述：暂停某个任务 用resumeJob恢复
	 * 		恢复的瞬间会将暂停期间应该执行的任务一次性执行完
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:9:26
	 *
	 */
	public static void pauseJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("暂停任务[{}]...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		JobDetail jobDetail = job.getJobDetail();
		try
		{
			scheduler.pauseJob(jobDetail.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("暂停任务[" + jobName + "]失败!", e);
		}
	}

	/**
	 *@描述：恢复某个任务
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:9:40
	 *
	 */
	public static void resumeJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("恢复任务[{}]...", jobName);
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
			logger.error("恢复任务[" + jobName + "]失败!", e);
		}
	}

	/**
	 *@描述：暂停某个任务的触发器
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:10:31
	 *
	 */
	public static void pauseTrigger(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("暂停任务[" + jobName + "]的触发器...");
		}
		BaseJob job = JOB_MAP.get(jobName);
		Trigger trigger = job.getTrigger();
		try
		{
			scheduler.pauseTrigger(trigger.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("暂停任务[" + jobName + "]触发器失败!", e);
		}
	}

	/**
	 *@描述: 恢复某个任务的触发器
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:10:30
	 *
	 */
	public static void resumeTrigger(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("恢复任务[" + jobName + "]的触发器...");
		}
		BaseJob job = JOB_MAP.get(jobName);
		Trigger trigger = job.getTrigger();
		try
		{
			scheduler.resumeTrigger(trigger.getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("恢复任务[" + jobName + "]触发器失败!", e);
		}
	}

	/**
	 *@描述：取消任务调度中的触发器
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:10:49
	 *
	 */
	public static void deleteJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("任务[{}]从调度器中删除...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		try
		{
			scheduler.unscheduleJob(job.getTrigger().getKey());
			scheduler.deleteJob(job.getJobDetail().getKey());
		}
		catch(SchedulerException e)
		{
			logger.error("从调度器中删除任务失败!", e);
		}
	}

	/**
	 *@描述：加入任务到调度器
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:11:18
	 *
	 */
	public static void addJob(String jobName)
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("添加任务[{}]到调度器...", jobName);
		}
		BaseJob job = JOB_MAP.get(jobName);
		job.setTrigger(job.getTrigger().getTriggerBuilder().startAt(new Date()).build());
		scheduleJob(job.getJobName(), job.getJobDetail(), job.getTrigger());
	}

	/**
	 *@描述：重新设置触发器
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:12:59
	 *
	 */
	public static void reschedule(String jobName)
	{
		BaseJob job = JOB_MAP.get(jobName);
		try
		{
			//生效时间设置成此刻
			job.getTrigger().getTriggerBuilder().startAt(new Date());
			scheduler.rescheduleJob(job.getTrigger().getKey(), job.getTrigger());
		}
		catch(SchedulerException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *@描述：注册任务调度
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:43
	 *
	 */
	public static void scheduleJob(String jobName, JobDetail jobDetail, Trigger trigger)
	{
		try
		{
			if(logger.isDebugEnabled())
			{
				logger.debug("任务[{}]注册到调度器中...", jobName);
			}
			scheduler.scheduleJob(jobDetail, trigger);
		}
		catch(SchedulerException e)
		{
			logger.error("启动任务[" + jobName + "]失败!", e);
		}
	}

	/**
	 *@描述：启动任务调度
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:1:43
	 *
	 */
	private static void startSchedule()
	{
		try
		{
			if(logger.isInfoEnabled())
			{
				logger.info("任务调度器启动...");
			}
			if(!scheduler.isStarted())
			{
				scheduler.start();
			}
		}
		catch(SchedulerException e)
		{
			logger.error("启动任务调度器失败!", e);
		}
	}

	/**
	 *@描述：获取指定的任务对象
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:12:46
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
	 *@描述：重新加载所有任务
	 *@作者:truncate(wy940407@163.com)
	 *@日期:2017/3/25
	 *@时间:13:31
	 *
	 */
	public static void reloadJob()
	{
		logger.info("重新加载配置文件中的任务到任务池...");
		if(!JOB_MAP.isEmpty())
		{
			JOB_MAP.clear();
		}
		//停止所有任务
		stopJob();
		//重新加载xml
		loadJobXml();
		//启动所有的任务
		startJob();
	}
}
