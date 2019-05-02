package com.taklip.jediorder.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.bean.IdMeta;
import com.taklip.jediorder.bean.IdMetaFactory;
import com.taklip.jediorder.converter.IdConverter;
import com.taklip.jediorder.populator.IdPopulator;
import com.taklip.jediorder.property.JediOrderProperties;
import com.taklip.jediorder.service.IdService;
import com.taklip.jediorder.timer.Timer;

@Service
public class IdServiceImpl implements IdService {
	protected final Logger log = LoggerFactory.getLogger(IdServiceImpl.class);

	protected long machineId = -1;
	protected long version = 0;

	protected IdMeta idMeta;

	@Autowired
	protected IdConverter idConverter;

	@Autowired
	protected Timer timer;

	@Autowired
	protected IdPopulator idPopulator;

	protected final JediOrderProperties properties;

	@Autowired
	public IdServiceImpl(JediOrderProperties properties) {
		this.properties = properties;
	}

	public void afterPropertiesSet() {
		if (this.idMeta == null) {
			setIdMeta(IdMetaFactory.getIdMeta());
		}

		this.timer.init(idMeta);

		this.machineId = properties.getMachineId();

		validateMachineId(this.machineId);
	}

	protected void populateId(Id id) {
		idPopulator.populateId(timer, id, idMeta);
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

	public Date translate(final long time) {
		return timer.translateTime(time);
	}

	public Id explainId(long id) {
		return idConverter.convert(id, this.idMeta);
	}

	public long generateId() {
		Id id = new Id();

		id.setMachine(machineId);
		id.setVersion(version);

		populateId(id);

		long ret = idConverter.convert(id, this.idMeta);

		if (log.isTraceEnabled())
			log.trace(String.format("Id: %s => %d", id, ret));

		return ret;
	}

	public void setIdPopulator(IdPopulator idPopulator) {
		this.idPopulator = idPopulator;
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

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}