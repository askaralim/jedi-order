package com.taklip.jediorder.timer;

import java.util.Date;

import com.taklip.jediorder.bean.IdMeta;

public interface Timer {
	long EPOCH = 1514736000000L;

	void init(IdMeta idMeta);

	Date transTime(long time);

	void validateTimestamp(long lastTimestamp, long timestamp);

	long tillNextTimeUnit(long lastTimestamp);

	long genTime();

}
