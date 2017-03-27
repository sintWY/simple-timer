package com.truncate.simple.timer.watchdog;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * ����: �ļ������� Ĭ���Ǹ����ļ����޸��¼��ж��Ƿ�仯
 * ��Ȩ: Copyright (c) 2017
 * ����: truncate(wy940407@163.com)
 * �汾: 1.0
 * ��������: 2017��03��25��
 * ����ʱ��: 0:52
 */
public abstract class FileWatchdog extends WatchdogListening
{

	private static final Logger logger = LoggerFactory.getLogger(FileWatchdog.class);

	//��Ҫ�������ļ�·��
	private String filePath;

	//�ļ�����޸�ʱ��
	private long lastModifyTime;

	//�ļ�
	private File file;

	public FileWatchdog(String filePath)
	{
		this.filePath = filePath;
		init();
	}

	private void init()
	{
		if(StringUtils.isEmpty(filePath))
		{
			throw new IllegalArgumentException("�ļ����Ʋ���Ϊ��!");
		}
		this.file = new File(filePath);
		if(!file.exists())
		{
			throw new IllegalArgumentException("�ļ�[" + filePath + "]������!");
		}
		this.lastModifyTime = file.lastModified();
	}

	@Override
	boolean isChange()
	{
		boolean isChange = file.lastModified() > lastModifyTime;
		if(logger.isDebugEnabled())
		{
			logger.debug("�ļ�[" + filePath + "]�����Ƿ����䶯��" + isChange);
		}
		return isChange;
	}
}
