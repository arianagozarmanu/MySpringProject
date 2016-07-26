package dao;

import java.util.List;

import model.User;



public interface UserDao {
	
	public void add(User user);
	public void addRole(String username,String role);
	public void insertLastActionDate(java.util.Date date, int id);
	public User findById(int id);
	public List<User> findAll();
	public void deleteById(int id);
	public User findByName(String name);
	public void update(int iduders, String username, String password, boolean enabled, String email, int age);
}
