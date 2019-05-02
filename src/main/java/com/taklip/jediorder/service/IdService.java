package com.taklip.jediorder.service;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

import com.taklip.jediorder.bean.Id;

public interface IdService extends InitializingBean {
	public long generateId();

	public Id explainId(long id);

	public Date translate(long time);
}