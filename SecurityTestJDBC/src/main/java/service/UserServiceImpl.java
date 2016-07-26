package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDaoImpl;
		
	public void insertLastActionDate(java.util.Date date, int id){
		userDaoImpl.insertLastActionDate(date, id);
		System.out.println("I'm in user service layer");
	}
}
