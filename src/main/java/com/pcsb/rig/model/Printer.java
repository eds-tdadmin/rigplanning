package com.pcsb.rig.model;

import java.util.Date;

public class Printer {
	
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

	public Printer(Integer rid_id , String rig_typ, String rig_name, String well_name, String well_typ, String status, String block,
			Date rig_up, Date rig_down, Date start_date, Date end_date, String contract_stat) {
		
		this.rp_id = rid_id;
		this.rig_typ = rig_typ;
		this.rig_name = rig_name;
		this.well_name = well_name;
		this.well_typ = well_typ;
		this.status = status;
		this.block = block;
		this.rig_up = rig_up;
		this.rig_down = rig_down;
		this.start_date = start_date;
		this.end_date = end_date;
		this.contract_stat = contract_stat;
	}


	public Integer getRp_id() {
		return rp_id;
	}


	public void setRp_id(Integer rp_id) {
		this.rp_id = rp_id;
	}


	public String getRig_typ() {
		return rig_typ;
	}


	public void setRig_typ(String rig_typ) {
		this.rig_typ = rig_typ;
	}


	public String getWell_name() {
		return well_name;
	}


	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}


	public String getRig_name() {
		return rig_name;
	}


	public void setRig_name(String rig_name) {
		this.rig_name = rig_name;
	}


	public String getWell_typ() {
		return well_typ;
	}


	public void setWell_typ(String well_typ) {
		this.well_typ = well_typ;
	}


	public String getPac() {
		return pac;
	}


	public void setPac(String pac) {
		this.pac = pac;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getBlock() {
		return block;
	}


	public void setBlock(String block) {
		this.block = block;
	}


	public Integer getPlan_days() {
		return plan_days;
	}


	public void setPlan_days(Integer plan_days) {
		this.plan_days = plan_days;
	}


	public Integer getActual_days() {
		return actual_days;
	}


	public void setActual_days(Integer actual_days) {
		this.actual_days = actual_days;
	}


	public Date getRig_up() {
		return rig_up;
	}


	public void setRig_up(Date rig_up) {
		this.rig_up = rig_up;
	}


	public Date getRig_down() {
		return rig_down;
	}


	public void setRig_down(Date rig_down) {
		this.rig_down = rig_down;
	}


	public Date getStart_date() {
		return start_date;
	}


	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}


	public Date getEnd_date() {
		return end_date;
	}


	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}


	public String getContract_stat() {
		return contract_stat;
	}

	public void setContract_stat(String contract_stat) {
		this.contract_stat = contract_stat;
	}
}
