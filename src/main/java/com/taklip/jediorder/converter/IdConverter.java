package com.taklip.jediorder.converter;

import com.taklip.jediorder.bean.Id;

public interface IdConverter {
//	long convert(Id id, IdMeta idMeta);
//	Id convert(long id, IdMeta idMeta);
	long convert(Id id);
	Id convert(long id);
}