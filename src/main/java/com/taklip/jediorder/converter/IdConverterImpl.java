package com.taklip.jediorder.converter;

import org.springframework.stereotype.Component;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;

@Component
public class IdConverterImpl implements IdConverter {
	public IdConverterImpl() {
	}

	public long convert(Id id, IdMeta idMeta) {
		return doConvert(id, idMeta);
	}

	protected long doConvert(Id id, IdMeta idMeta) {
		long ret = 0;

		ret |= id.getVersion();
		ret |= id.getTime() << idMeta.getTimeBitsStartPos();
		ret |= id.getMachine() << idMeta.getMachineBitsStartPos();
		ret |= id.getSequence() << idMeta.getSequenceBitsStartPos();

		return ret;
	}

	public Id convert(long id, IdMeta idMeta) {
		return doConvert(id, idMeta);
	}

	protected Id doConvert(long id, IdMeta idMeta) {
		Id ret = new Id();

		ret.setVersion(id & idMeta.getVersionBitsMask());
		ret.setTime((id >>> idMeta.getTimeBitsStartPos()) & idMeta.getTimeBitsMask());
		ret.setMachine((id >>> idMeta.getMachineBitsStartPos()) & idMeta.getMachineBitsMask());
		ret.setSequence((id >>> idMeta.getSequenceBitsStartPos()) & idMeta.getSequenceBitsMask());

		return ret;
	}
}