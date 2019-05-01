package com.taklip.jediorder.populator;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

public class SyncIdPopulator extends BasePopulator {

	public SyncIdPopulator() {
		super();
	}

	public synchronized void populateId(Timer timer, Id id, IdMeta idMeta) {
		super.populateId(timer, id, idMeta);
	}

}
