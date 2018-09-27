package com.pcsb.rig.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contract", catalog = "rps")
public class Contract {

	private Integer c_id;
	private String contract_status;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "C_ID", unique = true, nullable = false)
	public Integer getC_id() {
		return c_id;
	}

	public void setC_id(Integer c_id) {
		this.c_id = c_id;
	}

	@Column(name = "CONTRACT_STATUS", nullable = false, length = 50)
	public String getContract_status() {
		return contract_status;
	}

	public void setContract_status(String contract_status) {
		this.contract_status = contract_status;
	}

}
