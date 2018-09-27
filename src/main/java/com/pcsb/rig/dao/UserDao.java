package com.pcsb.rig.dao;

import com.pcsb.rig.model.User;

public interface UserDao {
	
	public User findUserLogin(String id, String password);

}
