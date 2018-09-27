package com.pcsb.rig.model;

public class Timeline {

	private Integer id;
	private String rigtype;
	private String rigname;
	private String objname;
	

	public Timeline(Integer id, String rigtype, String rigname, String objname) {
		this.id = id;
		this.rigtype = rigtype;
		this.rigname = rigname;
		this.objname = objname;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRigtype() {
		return rigtype;
	}

	public void setRigtype(String rigtype) {
		this.rigtype = rigtype;
	}

	public String getRigname() {
		return rigname;
	}

	public void setRigname(String rigname) {
		this.rigname = rigname;
	}

	public String getObjname() {
		return objname;
	}

	public void setObjname(String objname) {
		this.objname = objname;
	}

}
