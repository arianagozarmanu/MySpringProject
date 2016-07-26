package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.User;

@SuppressWarnings("rawtypes")
public class UserRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setIduders(rs.getInt("idusers"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEnabled(rs.getBoolean("enabled"));
		user.setEmail(rs.getString("email"));
		user.setAge(rs.getInt("age"));
		user.setLastOperationDate(rs.getTimestamp("lastOperationDate"));
		return user;
	}

}
