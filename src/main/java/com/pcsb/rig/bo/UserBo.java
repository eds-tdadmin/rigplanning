package com.pcsb.rig.bo;

import com.pcsb.rig.model.User;

public interface UserBo{
 
	public String getMessage();
	
	public User findUserLogin(String id, String password);
}