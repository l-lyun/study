package tobyspring.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tobyspring.user.domain.User;

public class IndependentUserDao {

	private final ConnectionMaker connectionMaker;

	public IndependentUserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeNewConnection();
		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values (?, ?, ?)"
		);
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeNewConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	public void deleteAll() throws SQLException, ClassNotFoundException {
		Connection c = connectionMaker.makeNewConnection();
		PreparedStatement ps = c.prepareStatement ("delete from users");
		ps.executeUpdate();
		ps.close();
		c.close();
	}

	public int getCount() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeNewConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		rs.close();
		ps.close();
		c.close();

		return count;
	}
}
