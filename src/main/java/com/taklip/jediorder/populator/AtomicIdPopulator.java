package com.taklip.jediorder.populator;

import java.util.concurrent.atomic.AtomicReference;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

public class AtomicIdPopulator implements IdPopulator, ResetPopulator {

	class Variant {

		private long sequence = 0;
		private long lastTimestamp = -1;

	}

	private AtomicReference<Variant> variant = new AtomicReference<Variant>(new Variant());

	public AtomicIdPopulator() {
		super();
	}

	public void populateId(Timer timer, Id id, IdMeta idMeta) {
		Variant varOld, varNew;
		long timestamp, sequence;

		while (true) {

			// Save the old variant
			varOld = variant.get();

			// populate the current variant
			timestamp = timer.genTime();
			timer.validateTimestamp(varOld.lastTimestamp, timestamp);

			sequence = varOld.sequence;

			if (timestamp == varOld.lastTimestamp) {
				sequence++;
				sequence &= idMeta.getSeqBitsMask();
				if (sequence == 0) {
					timestamp = timer.tillNextTimeUnit(varOld.lastTimestamp);
				}
			} else {
				sequence = 0;
			}

			// Assign the current variant by the atomic tools
			varNew = new Variant();
			varNew.sequence = sequence;
			varNew.lastTimestamp = timestamp;

			if (variant.compareAndSet(varOld, varNew)) {
				id.setSeq(sequence);
				id.setTime(timestamp);

				break;
			}

		}
	}

	public void reset() {
		variant = new AtomicReference<Variant>(new Variant());
	}

}