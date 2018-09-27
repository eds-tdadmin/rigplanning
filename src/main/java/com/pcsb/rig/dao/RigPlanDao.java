package com.pcsb.rig.dao;

import java.util.List;

import com.pcsb.rig.model.Contract;
import com.pcsb.rig.model.Rig;
import com.pcsb.rig.model.RigPlan;

public interface RigPlanDao {

	List<RigPlan> findByRigTyp(String rigTyp);

	List<Rig> findAllRig();

	List<RigPlan> findAllRigPlan();
	
	RigPlan findRigPlanById (Integer id);
	
	List<RigPlan> findRigPlanMove(RigPlan rPlanObj);
	
	List<RigPlan> findRigPlanMoveList(RigPlan rPlanObj);
	
	void saveRigPlan(RigPlan rigplan);
	
	List<RigPlan> findByRigCriteria(String rig, String well, String block, String status, String contract);
	
	List<Contract> findAllContract();
}
