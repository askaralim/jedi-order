package com.taklip.jediorder.timer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taklip.jediorder.bean.IdMeta;

@Component
public class SimpleTimer implements Timer {
	protected static final Logger log = LoggerFactory.getLogger(SimpleTimer.class);

	protected static final long EPOCH = 1514736000000L;

	protected IdMeta idMeta;
	protected long maxTime;

	public Date translateTime(long time) {
		return new Date(time + EPOCH);
	}

	public long generateTime() {
		long time = (System.currentTimeMillis() - EPOCH);

		validateTimestamp(time);

		return time;
	}

	public long tillNextTimeUnit(final long lastTimestamp) {
		if (log.isInfoEnabled())
			log.info(String.format("Ids are used out during %d. Waiting till next second/milisencond.", lastTimestamp));

		long timestamp = generateTime();
		while (timestamp <= lastTimestamp) {
			timestamp = generateTime();
		}

		if (log.isInfoEnabled())
			log.info(String.format("Next second/milisencond %d is up.", timestamp));

		return timestamp;
	}

	public void init(IdMeta idMeta) {
		this.idMeta = idMeta;
		this.maxTime = (1L << idMeta.getTimeBits()) - 1;
		this.generateTime();
		this.timerUsedLog();
	}

	public void timerUsedLog() {
		Date expirationDate = translateTime(maxTime);

		long days = ((expirationDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24));

		log.info("The current time bit length is {}, the expiration date is {}, this can be used for {} days.",
			idMeta.getTimeBits(), expirationDate, days);
	}

	private void validateTimestamp(long timestamp) {
		if (timestamp > maxTime) {
			String error = String.format(
				"The current timestamp (%s >= %s) has overflowed, Id Generator will be terminate.", timestamp, maxTime);

			log.error(error);

			throw new RuntimeException(error);
		}
	}

	public void validateTimestamp(long lastTimestamp, long timestamp) {
		if (timestamp < lastTimestamp) {
			if (log.isErrorEnabled())
				log.error(String.format("Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
					lastTimestamp - timestamp));

			throw new IllegalStateException(
				String.format("Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
					lastTimestamp - timestamp));
		}
	}
}