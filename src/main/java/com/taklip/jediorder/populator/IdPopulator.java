package com.taklip.jediorder.populator;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.timer.TimeService;

public interface IdPopulator {
	void populateId(TimeService timer, Id id);
}