package com.truncate.simple.timer.watchdog;

import com.truncate.simple.timer.JobManager;

/**
 * 描述: job.xml文件发生更新触发事件
 * 版权: Copyright (c) 2017
 * 公司: 思迪科技 
 * 作者: 王功俊(wanggj@thinkive.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月25日
 * 创建时间: 13:52
 */
public class JobXmlChangeServiceImpl implements IChangeService
{

	@Override
	public boolean doChange()
	{
		JobManager.reloadJob();
		return true;
	}
}
