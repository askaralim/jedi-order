package com.taklip.jediorder.timer;

import java.util.Date;

import com.taklip.jediorder.bean.IdMeta;

public interface Timer {
	Date translateTime(long time);

	long generateTime();

	long tillNextTimeUnit(long lastTimestamp);

	void init(IdMeta idMeta);

	void validateTimestamp(long lastTimestamp, long timestamp);
}