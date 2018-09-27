package com.pcsb.rig.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rig", catalog = "rps")
public class Rig {

	private Integer r_id;
	private String rig_typ;
	
	public Rig() {
		
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "R_ID", unique = true, nullable = false)
	public Integer getR_id() {
		return r_id;
	}
	public void setR_id(Integer r_id) {
		this.r_id = r_id;
	}
	
	@Column(name = "RIG_TYP", nullable = false, length = 50)
	public String getRig_typ() {
		return rig_typ;
	}
	public void setRig_typ(String rig_typ) {
		this.rig_typ = rig_typ;
	}
}
