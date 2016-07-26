package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mapper.UserRowMapper;
import model.User;

@Repository(value = "userDaoImpl")
public class UserDaoImpl implements UserDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Transactional
	public void add(User user) {
		String sql = "INSERT INTO users(idusers, username, password, enabled, email, age, lastOperationDate ) VALUES (?,?,?,?,?,?,?)";

		int enabled = 0;
		if (user.isEnabled() == true)
			enabled = 1;

		jdbcTemplate.update(sql, new Object[] { user.getIduders(), user.getUsername(), user.getPassword(), enabled,
				user.getEmail(), user.getAge(), user.getLastOperationDate() });

		System.out.println("User with id=" + user.getIduders() + " was insterted");

	}
	
	@Transactional
	public void addRole(String username, String role) {
		String sql = "INSERT INTO user_roles(username, role ) VALUES (?,?)";

		jdbcTemplate.update(sql, new Object[] { username, role });

		System.out.println("User with username=" + username + " was insterted with ROLE_USER");
	}

	@SuppressWarnings({ "unchecked" })
	public User findById(int id) {
		String sql = "SELECT * FROM users WHERE idusers=?";

		User user = (User) jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserRowMapper());

		return user;
	}

	@SuppressWarnings({ "unchecked" })
	public User findByName(String name) {
		String sql = "SELECT * FROM users WHERE username=?";

		User user = (User) jdbcTemplate.queryForObject(sql, new Object[] { name }, new UserRowMapper());

		return user;
	}

	public List<User> findAll() {
		String sql = " SELECT * FROM users";

		List<User> users = new ArrayList<User>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map row : rows) {
			User user = new User();
			user.setIduders(Integer.parseInt(String.valueOf(row.get("idusers"))));
			user.setUsername((String) row.get("username"));
			user.setPassword((String) row.get("password"));
			user.setEmail((String) row.get("email"));
			user.setAge(Integer.parseInt(String.valueOf(row.get("age"))));
			users.add(user);
		}

		return users;
	}

	public void deleteById(int id) {
		String sql = " DELETE FROM users WHERE idusers=?";

		Object[] params = { id };

		int rows = jdbcTemplate.update(sql, params);
		System.out.println(rows + " row(s) deleted.");

	}

	public void update(int iduders, String username, String password, boolean enabled, String email, int age) {
		String sql = "UPDATE users SET username=?, password=?, enabled=?, email=?, age=? WHERE idusers=?";

		int enable = 0;
		if (enabled = true)
			enable = 1;

		Object[] params = { username, password, enable, email, age, iduders };

		int rows = jdbcTemplate.update(sql, params);
		System.out.println(rows + " row(s) updated in User Table.");

	}
	
	@Transactional
	public void insertLastActionDate(java.util.Date date, int id) {
		String sql = "UPDATE users SET lastOperationDate=? WHERE idusers=?";

		int rows = jdbcTemplate.update(sql, new Object[] {date, id });
		System.out.println(rows + " row(s) updated with new data in User Table.");
	}

}
