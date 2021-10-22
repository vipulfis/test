package jcg.zheng.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import jcg.zheng.demo.entity.Person;
import jcg.zheng.demo.exception.UserNotFoundException;
import jcg.zheng.demo.repository.PersonRepository;

@RunWith(MockitoJUnitRunner.class)
public class MockUserServiceImplTest {

	private static final String MARY = "Mary";
	private static final String TEST_COMPANY = "Test";
	private Person person = new Person();
	@Mock
	private PersonRepository personDao;

	@InjectMocks
	private UserServiceImpl testClass;

	@Mock
	private TransformService transformer;

	private User user = new User();

	@Test
	public void findById_found() {
		doReturn(person).when(personDao).findOne(Integer.valueOf(1));
		doReturn(user).when(transformer).toUserDomain(person);

		User user = testClass.findById(Integer.valueOf(1));
		assertEquals(MARY, user.getFirstName());
	}

	@Test(expected = UserNotFoundException.class)
	public void findById_not_found() {
		doReturn(null).when(personDao).findOne(Integer.valueOf(1));

		testClass.findById(Integer.valueOf(1));
	}
	
	@Test 
	public void findById_not_found_default_user() {
		doReturn(null).when(personDao).findOne( Matchers.any(Integer.class));
		 
		doReturn(user).when(transformer).toUserDomain(Matchers.any(Person.class));
		
		User default_user = testClass.findById(Integer.valueOf(1));
		assertNotNull(default_user);
		 
	}

	@Test
	public void searchByCompanyName_found() {
		List<Person> persons = new ArrayList<>();
		persons.add(person);
		doReturn(persons).when(personDao).findByCompany(TEST_COMPANY);
		doReturn(user).when(transformer).toUserDomain(person);

		List<User> users = testClass.searchByCompanyName(TEST_COMPANY);
		assertEquals(1, users.size());
		assertEquals(MARY, users.get(0).getFirstName());
	}

	@Test
	public void searchByCompanyName_not_found() {
		List<Person> persons = new ArrayList<>();
		doReturn(persons).when(personDao).findByCompany(TEST_COMPANY);
		doReturn(user).when(transformer).toUserDomain(person);

		List<User> users = testClass.searchByCompanyName(TEST_COMPANY);
		assertTrue(users.isEmpty());
	}

	@Test
	public void deleteById_is_done_by_dao_delete() {
		doNothing().when(personDao).delete(Matchers.any(Integer.class));

		testClass.deleteById(Integer.valueOf(1));

		verify(personDao, times(1)).delete(Integer.valueOf(1));
		;
	}

	@Test(expected = Exception.class)
	public void mock_db_exception() {
		doThrow(new Exception("bad db")).when(personDao).delete(Matchers.any(Integer.class));
	}

	@Before
	public void setup() {
		person.setfName(MARY);
		user.setFirstName(MARY);
	}
}
