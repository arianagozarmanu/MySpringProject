package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import model.Product;

@SuppressWarnings("rawtypes")
public class ProductRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product prod = new Product();
		prod.setIdproduct(rs.getInt("idproduct"));
		prod.setName(rs.getString("name"));
		prod.setPrice(rs.getDouble("price"));
		prod.setIduser(rs.getInt("iduser"));
		return prod;
	}

}
