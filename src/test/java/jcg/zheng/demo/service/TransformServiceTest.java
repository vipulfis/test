package jcg.zheng.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import jcg.zheng.demo.entity.Person;

public class TransformServiceTest {

	private TransformService testClass = new TransformService() ;

	@Test
	public void test_toDomain() {
		Person person = new Person();
		person.setCompanyName("test company");
		person.setfName("Mary");
		person.setlName("Zheng");
		person.setmName("shan");
		person.setPersonId(1);
		User user = testClass.toUserDomain(person);

		assertNotNull(user);
		assertEquals("test company", user.getCompanyName());
		assertEquals("Mary", user.getFirstName());
		assertEquals("Zheng", user.getLastName());
		assertEquals(1, user.getUserId().intValue());
	}

	@Test
	public void test_toEntity() {
		User user = new User();

		user.setCompanyName("test company");
		user.setFirstName("Mary");
		user.setLastName("Zheng");
		user.setUserId(Integer.valueOf(1));

		Person person = testClass.toUserEntity(user);

		assertNotNull(user);
		assertEquals("test company", person.getCompanyName());
		assertEquals("Mary", person.getfName());
		assertEquals("Zheng", person.getlName());
		assertEquals(1, person.getPersonId());
	}

}
