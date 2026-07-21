package tobyspring.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;

import tobyspring.user.domain.User;

public class IndependentUserDao {

	private final ConnectionMaker connectionMaker;

	public IndependentUserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {

		// 로컬 클래스로 이관
		class AddStatement implements StatementStrategy {

			@Override
			public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(
					"insert into users(id, name, password) values (?, ?, ?)"
				);
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		}

		StatementStrategy strategy = new AddStatement();
		jdbcContextStatementStrategy(strategy);
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeNewConnection();

		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		User user = null;

		if(rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		c.close();

		if (user == null)
			throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deleteAll() throws SQLException, ClassNotFoundException {

		// 선점한 전략 클래스의 오브젝트 생성
		StatementStrategy strategy = new DeleteAllStatement();
		// 컨텍스트 호출. 전략 오브젝트 전달
		jdbcContextStatementStrategy(strategy);
		
	}

	public void jdbcContextStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = connectionMaker.makeNewConnection();

			ps = stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public int getCount() throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeNewConnection();
		ResultSet rs = null;
		
		PreparedStatement ps = null;

		try {
			c = connectionMaker.makeNewConnection();

			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
