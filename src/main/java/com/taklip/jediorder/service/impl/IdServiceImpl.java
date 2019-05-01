package com.taklip.jediorder.service.impl;

import com.taklip.jediorder.bean.Id;
import com.taklip.jediorder.populator.AtomicIdPopulator;
import com.taklip.jediorder.populator.IdPopulator;
import com.taklip.jediorder.populator.LockIdPopulator;
import com.taklip.jediorder.populator.SyncIdPopulator;
import com.taklip.jediorder.util.CommonUtils;

public class IdServiceImpl extends AbstractIdServiceImpl {

	private static final String SYNC_LOCK_IMPL_KEY = "vesta.sync.lock.impl.key";

	private static final String ATOMIC_IMPL_KEY = "vesta.atomic.impl.key";

	protected IdPopulator idPopulator;

	public IdServiceImpl() {
		super();
	}

	public IdServiceImpl(String type) {
		super(type);
	}

	public IdServiceImpl(long type) {
		super(type);
	}

	@Override
	public void init() {
		super.init();
		initPopulator();
	}

	public void initPopulator() {
		if (idPopulator != null) {
			log.info("The " + idPopulator.getClass().getCanonicalName() + " is used.");
		} else if (CommonUtils.isPropKeyOn(SYNC_LOCK_IMPL_KEY)) {
			log.info("The SyncIdPopulator is used.");
			idPopulator = new SyncIdPopulator();
		} else if (CommonUtils.isPropKeyOn(ATOMIC_IMPL_KEY)) {
			log.info("The AtomicIdPopulator is used.");
			idPopulator = new AtomicIdPopulator();
		} else {
			log.info("The default LockIdPopulator is used.");
			idPopulator = new LockIdPopulator();
		}
	}

	protected void populateId(Id id) {
		idPopulator.populateId(timer, id, idMeta);
	}

	public void setIdPopulator(IdPopulator idPopulator) {
		this.idPopulator = idPopulator;
	}

	@Override
	public long generateId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
