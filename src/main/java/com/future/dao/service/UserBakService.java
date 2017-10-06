package com.future.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.dao.LoginLogDao;
import com.future.dao.UserBakDao;
import com.future.domain.LoginLog;
import com.future.domain.User;

@Service
public class UserBakService {
    
	@Autowired
	private UserBakDao userBakDao;
	
	@Autowired
	private LoginLogDao loginLogDao;

	public boolean hasMatchUser(String userName, String password) {
		int matchCount = userBakDao.getMatchCount(userName, password);
		return matchCount > 0;
	}
	
	public User findUserByUserName(String userName) {
		return userBakDao.findUserByUserName(userName);
	}
	
	public void loginSuccess(User user) {
		user.setCredits( 5 + user.getCredits());
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(user.getLastVisit());
        userBakDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
	}	

}
