package tobyspring.user.dao;

public class DaoFactory {
	public IndependentUserDao userDao() {
		ConnectionMaker connectionMaker = new SimpleConnectionMaker();
		IndependentUserDao userDao = new IndependentUserDao(connectionMaker);
		return userDao;
	}
}
