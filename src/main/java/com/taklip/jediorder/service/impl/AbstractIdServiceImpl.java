package com.taklip.jediorder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.bean.IdMetaFactory;
import com.taklip.jediorder.converter.IdConverter;
import com.taklip.jediorder.converter.IdConverterImpl;
import com.taklip.jediorder.provider.MachineIdProvider;
import com.taklip.jediorder.service.IdService;
import com.taklip.jediorder.timer.SimpleTimer;
import com.taklip.jediorder.timer.Timer;

public abstract class AbstractIdServiceImpl implements IdService {

	protected final Logger log = LoggerFactory.getLogger(AbstractIdServiceImpl.class);

	protected long machineId = -1;
//    protected long genMethod = 0;
	protected long version = 0;
//    protected IdType idType;

	protected IdMeta idMeta;

	protected IdConverter idConverter;

	protected MachineIdProvider machineIdProvider;

	protected Timer timer;

	public void init() {
		if (this.idMeta == null) {
			setIdMeta(IdMetaFactory.getIdMeta());
		}
		if (this.idConverter == null) {
			setIdConverter(new IdConverterImpl());
		}
		if (this.timer == null) {
			setTimer(new SimpleTimer());
		}
		this.timer.init(idMeta);

		this.machineId = machineIdProvider.getMachineId();
		validateMachineId(this.machineId);
	}

	public long genId() {
		Id id = new Id();

		id.setMachine(machineId);
//        id.setGenMethod(genMethod);
//        id.setType(idType.value());
		id.setVersion(version);

		populateId(id);

		long ret = idConverter.convert(id, this.idMeta);

		// Use trace because it cause low performance
		if (log.isTraceEnabled())
			log.trace(String.format("Id: %s => %d", id, ret));

		return ret;
	}

	public void validateMachineId(long machineId) {
		if (machineId < 0) {
			log.error("The machine ID is not configured properly (" + machineId + " < 0) so that Vesta Service refuses to start.");

			throw new IllegalStateException("The machine ID is not configured properly (" + machineId + " < 0) so that Vesta Service refuses to start.");

		} else if (machineId >= (1 << this.idMeta.getMachineBits())) {
			log.error("The machine ID is not configured properly (" + machineId + " >= " + (1 << this.idMeta.getMachineBits()) + ") so that Vesta Service refuses to start.");

			throw new IllegalStateException("The machine ID is not configured properly (" + machineId + " >= " + (1 << this.idMeta.getMachineBits()) + ") so that Vesta Service refuses to start.");

		}
	}

	protected abstract void populateId(Id id);

	public Id explainId(long id) {
		return idConverter.convert(id, this.idMeta);
	}

	public void setMachineId(long machineId) {
		this.machineId = machineId;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public void setIdConverter(IdConverter idConverter) {
		this.idConverter = idConverter;
	}

	public void setIdMeta(IdMeta idMeta) {
		this.idMeta = idMeta;
	}

	public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
		this.machineIdProvider = machineIdProvider;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}