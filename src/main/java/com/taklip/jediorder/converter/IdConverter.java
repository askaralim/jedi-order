package com.taklip.jediorder.converter;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;

public interface IdConverter {
	long convert(Id id, IdMeta idMeta);
	Id convert(long id, IdMeta idMeta);
}