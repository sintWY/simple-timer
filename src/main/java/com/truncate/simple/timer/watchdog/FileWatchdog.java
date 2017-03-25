package com.truncate.simple.timer.watchdog;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * ����: �ļ�������
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0
 * ��������: 2017��03��25��
 * ����ʱ��: 0:52
 */
public class FileWatchdog extends Thread
{

	private static final Logger logger = LoggerFactory.getLogger(FileWatchdog.class);

	//��Ҫ�������ļ�·��
	private String filePath;

	//�ļ�����޸�ʱ��
	private long lastModifyTime;

	//�����ʱ��
	private long delayTime;

	//�ļ�
	private File file;

	//�Ƿ��������
	private volatile boolean isShutdown = false;

	//�ļ������䶯�������߼� ��Ҫʵ�ֽӿ�IChangeService
	private IChangeService changeService;

	public FileWatchdog(String filePath, long delayTime, IChangeService changeService)
	{
		this.filePath = filePath;
		this.delayTime = delayTime;
		this.file = new File(filePath);
		this.changeService = changeService;
		init();
	}

	private void init()
	{
		if(StringUtils.isEmpty(filePath))
		{
			isShutdown = true;
			throw new IllegalArgumentException("�ļ�·������Ϊ��!");
		}
		if(this.file.exists())
		{
			lastModifyTime = file.lastModified();
		}
		else
		{
			isShutdown = true;
			throw new IllegalArgumentException("�ļ�[" + filePath + "]������!");
		}
	}

	@Override
	public void run()
	{
		while(!isShutdown)
		{
			if(file.lastModified() > lastModifyTime)
			{
				//�й��޸�,������Ӧ���߼�
				boolean success = changeService.doChange();
				if(success)
				{
					lastModifyTime = file.lastModified();
				}

				if(logger.isDebugEnabled())
				{
					logger.debug("�ļ�[{}]���ݷ����䶯�������¼����ؽ����{}", filePath, success);
				}

				try
				{
					Thread.sleep(delayTime * 1000);
				}
				catch(InterruptedException e)
				{
				}
			}
		}
	}
}
