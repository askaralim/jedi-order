package com.taklip.jediorder.service;

import com.taklip.jediorder.bean.Id;

public interface IdService {
	public long generateId();

	public Id explainId(long id);
}
