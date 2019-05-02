package com.taklip.jediorder.populator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

@Component
public class IdPopulatorImpl implements IdPopulator, ResetPopulator {
	protected long sequence = 0;
	protected long lastTimestamp = -1;

	private Lock lock = new ReentrantLock();

	public IdPopulatorImpl() {
		super();
	}

	public void populateId(Timer timer, Id id, IdMeta idMeta) {
		lock.lock();

		try {
			long timestamp = timer.generateTime();
			timer.validateTimestamp(lastTimestamp, timestamp);

			if (timestamp == lastTimestamp) {
				sequence++;
				sequence &= idMeta.getSequenceBitsMask();

				if (sequence == 0) {
					timestamp = timer.tillNextTimeUnit(lastTimestamp);
				}
			}
			else {
				lastTimestamp = timestamp;
				sequence = 0;
			}

			id.setSequence(sequence);
			id.setTime(timestamp);
		}
		finally {
			lock.unlock();
		}
	}

	public void reset() {
		this.sequence = 0;
		this.lastTimestamp = -1;
	}
}