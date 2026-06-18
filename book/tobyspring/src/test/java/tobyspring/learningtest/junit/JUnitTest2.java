package tobyspring.learningtest.junit;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JUnitTest2 {

	static Set<JUnitTest2> testObjects = new HashSet<>();

	@Test
	void test1() {
		Assertions.assertThat(testObjects).doesNotContain(this);
		testObjects.add(this);

	}

	@Test
	void test2() {
		Assertions.assertThat(testObjects).doesNotContain(this);
		testObjects.add(this);
	}

	@Test
	void test3() {
		Assertions.assertThat(testObjects).doesNotContain(this);
		testObjects.add(this);
	}

}
