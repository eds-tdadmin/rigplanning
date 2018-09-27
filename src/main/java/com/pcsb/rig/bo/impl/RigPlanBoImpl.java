package com.pcsb.rig.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcsb.rig.bo.RigPlanBo;
import com.pcsb.rig.dao.RigPlanDao;
import com.pcsb.rig.model.Contract;
import com.pcsb.rig.model.Rig;
import com.pcsb.rig.model.RigPlan;

@Service("rigPlanBo")
public class RigPlanBoImpl implements RigPlanBo {

	@Autowired
	RigPlanDao rigPlanDao;

	public void setRigPlanDao(RigPlanDao rigPlanDao) {
		this.rigPlanDao = rigPlanDao;
	}

	@Override
	public List<RigPlan> findByRigTyp(String rigTyp) {
		// TODO Auto-generated method stub
		return rigPlanDao.findByRigTyp(rigTyp);
	}

	@Override
	public List<RigPlan> findAllRigPlan() {
		// TODO Auto-generated method stub
		return rigPlanDao.findAllRigPlan();
	}

	@Override
	public List<Rig> findAllRig() {
		// TODO Auto-generated method stub
		return rigPlanDao.findAllRig();
	}

	@Override
	public RigPlan findRigPlanById(Integer id) {
		// TODO Auto-generated method stub
		return rigPlanDao.findRigPlanById(id);
	}

	@Override
	public List<RigPlan> findRigPlanMove(RigPlan rPlanObj) {
		// TODO Auto-generated method stub
		return rigPlanDao.findRigPlanMove(rPlanObj);
	}

	@Override
	public void saveRigPlan(RigPlan rigplan) {
		rigPlanDao.saveRigPlan(rigplan);
		
	}

	@Override
	public List<RigPlan> findRigPlanMoveList(RigPlan rPlanObj) {
		// TODO Auto-generated method stub
		return rigPlanDao.findRigPlanMoveList(rPlanObj);
	}

	@Override
	public List<RigPlan> findByRigCriteria(String rig, String well, String block, String status, String contract) {
		// TODO Auto-generated method stub
		return rigPlanDao.findByRigCriteria(rig, well, block, status, contract);
	}

	@Override
	public List<Contract> findAllContract() {
		// TODO Auto-generated method stub
		return rigPlanDao.findAllContract();
	}

}
