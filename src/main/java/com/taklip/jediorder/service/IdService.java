package com.taklip.jediorder.service;

import java.util.Date;

import com.taklip.jediorder.bean.Id;

public interface IdService {
	public long generateId();

	public Id explainId(long id);

	public Date translate(long time);
}