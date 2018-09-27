package com.pcsb.rig.bo.impl;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.pcsb.rig.bo.UserBo;
import com.pcsb.rig.dao.UserDao;
import com.pcsb.rig.model.User;

@Named
public class UserBoImpl implements UserBo{
	
	@Autowired
	UserDao userDao;
 
	public String getMessage() {
		
		return "JSF 2 + Spring Integration";
	
	}

	@Override
	public User findUserLogin(String id, String password) {
		// TODO Auto-generated method stub
		return userDao.findUserLogin(id, password);
	}
 
	
}