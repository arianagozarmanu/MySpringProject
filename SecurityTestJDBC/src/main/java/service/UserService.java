package service;

import java.util.List;
import java.util.Set;

import model.User;

public interface UserService {
	
	public void addUser(User user);
	public User findUserById(int id);
	public List<User> findAllUsers();
	public void deleteUserById(int id);
	public User findUserByName(String name);
	public void updateUser(int iduders, String username, String password, boolean enabled, String email, int age);
	public void registerUser(User user);
	public Set<Integer> getUsersIds(List<User> users);
}
