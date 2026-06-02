package tobyspring.user.dao;

public class DaoFactory {
	public IndependentUserDao userDao() {
		return new IndependentUserDao(connectionMaker());
	}

	public IndependentUserDao accountDao() {
		return new IndependentUserDao(connectionMaker());
	}

	public IndependentUserDao messageDao() {
		return new IndependentUserDao(connectionMaker());
	}

	public ConnectionMaker connectionMaker() {
		// 분리하여 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
		return new SimpleConnectionMaker();
	}
}
