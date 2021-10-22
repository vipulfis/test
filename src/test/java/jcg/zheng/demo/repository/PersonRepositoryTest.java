package jcg.zheng.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import jcg.zheng.demo.entity.Person;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

	@Rule
	public Timeout appTimeout = Timeout.millis(2000);

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private PersonRepository personDao;

	@Before
	public void setup() {
		assertNotNull(entityManager);
		assertNotNull(personDao);

		// prepare two persons
		Person mary = new Person();
		mary.setfName("Mary");
		mary.setCompanyName("Test");
		entityManager.persist(mary);

		Person alex = new Person();
		alex.setfName("Alex");
		alex.setCompanyName("Alex company");
		entityManager.persist(alex);

	}

	@Test
	public void findAll_return_list_when_found() {
		List<Person> found = personDao.findAll();

		assertNotNull(found);
		assertEquals(2, found.size());
	}

	@Test
	public void findByCompany_return_person_when_found() {
		List<Person> found = personDao.findByCompany("Test");

		assertNotNull(found);
		assertEquals("Mary", found.get(0).getfName());
	}

	@Test
	public void findByCompany_return_emptylist_when_not_found() {
		List<Person> found = personDao.findByCompany("Test-notExist");

		assertNotNull(found);
		assertTrue(found.isEmpty());

	}

	@Test
	public void findOne_return_null_when_not_found() {
		Person found = personDao.findOne(-9);

		assertNull(found);
	}

}
