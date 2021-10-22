package jcg.zheng.demo.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PersonTest {

	@Test
	public void test_person_default_constructor() {
		Person testClass = new Person();
		
		testClass.setmName("Shan");
		assertEquals("Shan", testClass.getmName());

		testClass.setfName("Mary");
		assertEquals("Mary", testClass.getfName());

		testClass.setlName("Zheng");
		assertEquals("Zheng", testClass.getlName());
	}

}
