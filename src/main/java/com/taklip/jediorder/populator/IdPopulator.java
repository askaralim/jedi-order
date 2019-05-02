package com.taklip.jediorder.populator;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.timer.Timer;

public interface IdPopulator {
	void populateId(Timer timer, Id id, IdMeta idMeta);
}