package com.taklip.jediorder.populator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

public class LockIdPopulator extends BasePopulator {

	private Lock lock = new ReentrantLock();

	public LockIdPopulator() {
		super();
	}

	public void populateId(Timer timer, Id id, IdMeta idMeta) {
		lock.lock();
		try {
			super.populateId(timer, id, idMeta);
		} finally {
			lock.unlock();
		}
	}

}
