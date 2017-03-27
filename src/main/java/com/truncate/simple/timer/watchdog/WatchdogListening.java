package com.truncate.simple.timer.watchdog;

/**
 * 描述: watchdog监听
 * 版权: Copyright (c) 2017
 * 作者: truncate(truncate@163.com)
 * 版本: 1.0 
 * 创建日期: 2017年03月27日
 * 创建时间: 10:45
 */
public abstract class WatchdogListening extends Thread
{

	//默认检测时间
	private static final long DEFAULT_DELAY_TIME = 60000L;

	//检测时间
	private long delayTime;

	//是否启动线程，主要用来启动之前的资源检测，是否需要开启监听
	protected boolean isStart()
	{
		return true;
	}

	//true：发生了变化 false：未变化
	abstract boolean isChange();

	//true：修改成功 false：修改失败
	abstract boolean doChange();

	//空实现，子类可以选择是否重写
	//变化事件执行成功触发
	protected void doChangeSuccess()
	{
	}

	//空实现，子类可以选择是否重写
	//变化事件执行失败触发
	protected void doChangeFail()
	{
	}

	public WatchdogListening()
	{
		this(DEFAULT_DELAY_TIME);

	}

	public WatchdogListening(long delayTime)
	{
		//已守护线程启动
		setDaemon(true);
		this.delayTime = delayTime;
	}

	public long getDelayTime()
	{
		return delayTime;
	}

	public void setDelayTime(long delayTime)
	{
		this.delayTime = delayTime;
	}

	@Override
	public void run()
	{
		while(isStart())
		{
			if(isChange())
			{
				if(doChange())
				{
					doChangeSuccess();
				}
				else
				{
					doChangeFail();
				}
			}

			try
			{
				Thread.sleep(delayTime);
			}
			catch(InterruptedException e)
			{
			}
		}
	}

}
