package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mapper.ProductRowMapper;
import model.Product;

@Repository(value="productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);		
	}
	
	public void add(Product product) {
		String sql = "INSERT INTO products(idproduct, name, price, iduser) VALUES (?,?,?,?)";

		jdbcTemplate.update(sql,
				new Object[] { product.getIdproduct(), product.getName(), product.getPrice(), product.getIduser() });

		System.out.println("Product with id=" + product.getIdproduct() + " was insterted");

	}

	public void deleteById(Product product) {
		String sql = "DELETE FROM products WHERE idproduct=?";
	
		int rows= jdbcTemplate.update(sql, new Object[] {product.getIdproduct()});
		System.out.println(rows + " row(s) deleted in Product Table.");
	}

	public void update(Product product) {
		String sql = "UPDATE products SET name=?, price=?, iduser=? WHERE idproduct=?";

		int rows = jdbcTemplate.update(sql, new Object[] { product.getName(), product.getPrice(), product.getIduser(), product.getIdproduct() });
		System.out.println(rows + " row(s) updated in Product Table.");

	}

	@SuppressWarnings({ "unchecked" })
	public Product findById(int id) {
		String sql = "SELECT * FROM products WHERE idproduct=?";

		Product product = (Product) jdbcTemplate.queryForObject(sql, new Object[] { id }, new ProductRowMapper());

		return product;
	}

	public List<Product> findByUserId(int id) {
		String sql = "SELECT * FROM products";

		List<Product> products = new ArrayList<Product>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map row : rows) {
			Product prod = new Product();
			prod.setIdproduct(Integer.parseInt(String.valueOf(row.get("idproduct"))));
			prod.setName((String) row.get("name"));
			prod.setPrice(Double.parseDouble(String.valueOf(row.get("price"))));
			prod.setIduser(Integer.parseInt(String.valueOf(row.get("iduser"))));
			if (prod.getIduser() == id)
				products.add(prod);
		}

		return products;
	}

	public List<Product> findAll() {
		String sql = " SELECT * FROM products";

		List<Product> products = new ArrayList<Product>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map row : rows) {
			Product prod = new Product();
			prod.setIdproduct(Integer.parseInt(String.valueOf(row.get("idproduct"))));
			prod.setName((String) row.get("name"));
			prod.setPrice(Double.parseDouble(String.valueOf(row.get("price"))));
			prod.setIduser(Integer.parseInt(String.valueOf(row.get("iduser"))));
			products.add(prod);
		}

		return products;
	}


}
