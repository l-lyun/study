package tobyspring.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {

	public int count = 0;
	private final ConnectionMaker realConnectionMaker;

	// DI를 통한 부가 기능 추가
	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnectionMaker = realConnectionMaker;
	}

	@Override
	public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
		count++;
		return realConnectionMaker.makeNewConnection();
	}

	public int getCount() {
		return count;
	}
}
