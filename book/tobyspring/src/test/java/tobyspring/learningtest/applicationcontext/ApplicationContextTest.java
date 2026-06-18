package tobyspring.learningtest.applicationcontext;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
public class ApplicationContextTest {

	@Autowired
	ApplicationContext context;

	static Set<ApplicationContextTest> testObjects = new HashSet<>();
	static ApplicationContext contextObject = null;

	@Test
	public void test1() {
		Assertions.assertThat(testObjects).doesNotContain(this);
		testObjects.add(this);
		Assertions.assertThat(contextObject == null || this.context == contextObject).isTrue();
		contextObject = this.context;
	}

	@Test
	// test2()가 먼저 실행되면 테스트 실패
	public void test2() {
		Assertions.assertThat(testObjects).doesNotContain(this);
		testObjects.add(this);
		Assertions.assertThat(this.context == contextObject).isTrue();
	}

}
