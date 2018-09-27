package com.pcsb.rig.dao.impl;

import java.util.List;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pcsb.rig.dao.RigPlanDao;
import com.pcsb.rig.model.Contract;
import com.pcsb.rig.model.Rig;
import com.pcsb.rig.model.RigPlan;
import com.pcsb.rig.util.CustomHibernateDaoSupport;

@Repository("rigPlanDao")
@Transactional(readOnly = false)
public class RigPlanDaoImpl extends CustomHibernateDaoSupport implements RigPlanDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<RigPlan> findByRigTyp(String rigTyp) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		List list = getHibernateTemplate().find("from RigPlan where rig_typ=?", rigTyp);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RigPlan> findAllRigPlan() {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		List list = getHibernateTemplate().find("from RigPlan order by rig_typ,start_date");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rig> findAllRig() {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		List list = getHibernateTemplate().find("from Rig");
		return list;
	}

	@Override
	public RigPlan findRigPlanById(Integer id) {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		List list = getHibernateTemplate().find("from RigPlan where rp_id=?", id);
		return (RigPlan) list.get(0);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<RigPlan> findRigPlanMove(RigPlan rPlanObj) {
		System.out.println("dao----" + rPlanObj.getRp_id() + rPlanObj.getRig_typ() + rPlanObj.getRig_name()
				+ rPlanObj.getWell_name() + rPlanObj.getStart_date() + rPlanObj.getEnd_date());

		Criterion startdate = Restrictions.between("start_date", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion enddate = Restrictions.between("end_date", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion rigup = Restrictions.between("rig_up", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion rigdown = Restrictions.between("rig_down", rPlanObj.getStart_date(), rPlanObj.getEnd_date());

		Criterion startdatechk = Restrictions.le("start_date", rPlanObj.getStart_date());
		Criterion enddatechk = Restrictions.gt("end_date", rPlanObj.getEnd_date());
		LogicalExpression logExp = Restrictions.and(startdatechk, enddatechk);
		Disjunction orExp = Restrictions.or(startdate, enddate, rigup, rigdown, logExp);

		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(RigPlan.class).add(Restrictions.eq("rig_typ", rPlanObj.getRig_typ()))
						.add(Restrictions.eq("rig_name", rPlanObj.getRig_name()))
						.add(Restrictions.ne("well_name", rPlanObj.getWell_name())).add(orExp));
		return list;
	}

	@Override
	public void saveRigPlan(RigPlan rigplan) {
		System.out.println("save rig plan");
		getHibernateTemplate().saveOrUpdate(rigplan);

	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<RigPlan> findRigPlanMoveList(RigPlan rPlanObj) {

		Criterion startdate = Restrictions.between("start_date", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion enddate = Restrictions.between("end_date", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion rigup = Restrictions.between("rig_up", rPlanObj.getStart_date(), rPlanObj.getEnd_date());
		Criterion rigdown = Restrictions.between("rig_down", rPlanObj.getStart_date(), rPlanObj.getEnd_date());

		Criterion startdatechk = Restrictions.le("start_date", rPlanObj.getStart_date());
		Criterion enddatechk = Restrictions.gt("end_date", rPlanObj.getEnd_date());
		LogicalExpression logExp = Restrictions.and(startdatechk, enddatechk);
		Disjunction orExp = Restrictions.or(startdate, enddate, rigup, rigdown, logExp);

		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().findByCriteria(
				DetachedCriteria.forClass(RigPlan.class).add(Restrictions.eq("rig_typ", rPlanObj.getRig_typ()))
						.add(Restrictions.eq("rig_name", rPlanObj.getRig_name()))
						.add(Restrictions.ne("well_name", rPlanObj.getWell_name())).add(orExp));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RigPlan> findByRigCriteria(String rig, String well, String block, String status, String contract) {

		if (contract != null) {
			if (contract.equalsIgnoreCase("contracted")) {
				contract = "SIGNED";
			} else {
				contract = "NO";
			}
		}

		Criterion rigtyp = Restrictions.eq("rig_typ", rig);
		Criterion welltyp = Restrictions.eq("well_typ", well);
		Criterion region = Restrictions.eq("block", block);
		Criterion stat = Restrictions.eq("status", status);
		Criterion cont = Restrictions.eq("contract_stat", contract);
		Conjunction allExp = new Conjunction();

		if (!rig.equalsIgnoreCase("select")) {
			allExp.add(rigtyp);
		}
		if (!well.equalsIgnoreCase("select")) {
			allExp.add(welltyp);
		}
		if (!block.equalsIgnoreCase("select")) {
			allExp.add(region);
		}
		if (!status.equalsIgnoreCase("select")) {
			allExp.add(stat);
		}
		if (contract != null) {
			allExp.add(cont);
		}

		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(RigPlan.class).add(allExp));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> findAllContract() {
		@SuppressWarnings({ "deprecation", "rawtypes" })
		List list = getHibernateTemplate().find("from Contract");
		return list;
	}

}
