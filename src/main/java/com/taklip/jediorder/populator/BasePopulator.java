package com.taklip.jediorder.populator;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

public abstract class BasePopulator implements IdPopulator, ResetPopulator {
	protected long sequence = 0;
	protected long lastTimestamp = -1;

	public BasePopulator() {
		super();
	}

	public void populateId(Timer timer, Id id, IdMeta idMeta) {
		long timestamp = timer.genTime();
		timer.validateTimestamp(lastTimestamp, timestamp);

		if (timestamp == lastTimestamp) {
			sequence++;
			sequence &= idMeta.getSeqBitsMask();
			if (sequence == 0) {
				timestamp = timer.tillNextTimeUnit(lastTimestamp);
			}
		} else {
			lastTimestamp = timestamp;
			sequence = 0;
		}

		id.setSeq(sequence);
		id.setTime(timestamp);
	}

	public void reset() {
		this.sequence = 0;
		this.lastTimestamp = -1;
	}
}
