package com.pcsb.rig.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pcsb.rig.dao.UserDao;
import com.pcsb.rig.model.User;
import com.pcsb.rig.util.CustomHibernateDaoSupport;

@Repository("userDao")
@Transactional(readOnly = false)
public class UserDaoImpl extends CustomHibernateDaoSupport implements UserDao {

	@Override
	public User findUserLogin(String id, String password) {
		// TODO Auto-generated method stub
		List list = getHibernateTemplate().find("from User where user_name=? and user_password=?",id,password);
		return (User) list.get(0);
	}

}
