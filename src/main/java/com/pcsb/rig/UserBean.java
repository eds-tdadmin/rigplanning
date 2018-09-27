package com.pcsb.rig;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.pcsb.rig.bo.UserBo;
import com.pcsb.rig.model.User;

@ManagedBean(value = "userBean")
@SessionScoped
public class UserBean {

	@Inject
	UserBo userBo;

	private String user_name;
	private String user_password;

	public void setUserBo(UserBo userBo) {
		this.userBo = userBo;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String printMsgFromSpring() {
		return userBo.getMessage();
	}

	public String login() {
		String page = "login";
		User user = new User();
		user = userBo.findUserLogin(user_name, user_password);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
		if (user.getUser_roles() != null) {
			page = "default";
		} else {
			page = "login";
		}

		return page;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";

	}

}