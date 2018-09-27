package com.pcsb.rig.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rig_plan", catalog = "rps")
public class RigPlan {
	private Integer rp_id;
	private String rig_typ;
	private String rig_name;
	private String well_name;
	private String well_typ;
	private String pac;
	private String status;
	private String block;
	private Integer plan_days;
	private Integer actual_days;
	private Date rig_up;
	private Date rig_down;
	private Date start_date;
	private Date end_date;
	private String contract_stat;
	
	public RigPlan() {
		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RP_ID", unique = true, nullable = false)
	public Integer getRp_id() {
		return rp_id;
	}
	public void setRp_id(Integer rp_id) {
		this.rp_id = rp_id;
	}
	
	@Column(name = "RIG_TYPE", nullable = false, length = 50)
	public String getRig_typ() {
		return rig_typ;
	}
	public void setRig_typ(String rig_typ) {
		this.rig_typ = rig_typ;
	}
	
	@Column(name = "RIG_NAME", nullable = false, length = 50)
	public String getRig_name() {
		return rig_name;
	}
	public void setRig_name(String rig_name) {
		this.rig_name = rig_name;
	}
	
	@Column(name = "WELL_NAME", nullable = false, length = 50)
	public String getWell_name() {
		return well_name;
	}
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	@Column(name = "WELL_TYP", nullable = false, length = 50)
	public String getWell_typ() {
		return well_typ;
	}
	public void setWell_typ(String well_typ) {
		this.well_typ = well_typ;
	}
	
	@Column(name = "PAC", nullable = false, length = 50)
	public String getPac() {
		return pac;
	}
	public void setPac(String pac) {
		this.pac = pac;
	}
	
	@Column(name = "STATUS", nullable = false, length = 50)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "BLOCK", nullable = false, length = 50)
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	
	@Column(name = "PLAN_DAYS", nullable = false)
	public Integer getPlan_days() {
		return plan_days;
	}
	public void setPlan_days(Integer plan_days) {
		this.plan_days = plan_days;
	}
	
	@Column(name = "ACTUAL_DAYS", nullable = true)
	public Integer getActual_days() {
		return actual_days;
	}
	public void setActual_days(Integer actual_days) {
		this.actual_days = actual_days;
	}
	
	@Column(name = "RIG_UP", nullable = true)
	public Date getRig_up() {
		return rig_up;
	}
	public void setRig_up(Date rig_up) {
		this.rig_up = rig_up;
	}
	
	@Column(name = "RIG_DOWN", nullable = true)
	public Date getRig_down() {
		return rig_down;
	}
	public void setRig_down(Date rig_down) {
		this.rig_down = rig_down;
	}
	
	@Column(name = "START_DATE", nullable = false)
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	@Column(name = "END_DATE", nullable = false)
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	
	@Column(name = "CONTRACT_STATUS", nullable = false)
	public String getContract_stat() {
		return contract_stat;
	}
	public void setContract_stat(String contract_stat) {
		this.contract_stat = contract_stat;
	}

}
