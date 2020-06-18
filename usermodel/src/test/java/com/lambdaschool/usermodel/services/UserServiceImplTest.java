package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.UserModelApplicationTests;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void aFindUserById() {
		assertEquals("test admin", userService.findUserById(4).getUsername());
	}

	@Test
	public void bFindByNameContaining() {
		assertEquals(5, userService.findByNameContaining("test").size());
	}

	@Test
	public void cFindAll() {
		assertEquals(30, userService.findAll().size());
	}

	@Test
	public void dDelete() {
		userService.delete(4);
		assertEquals(29, userService.findAll().size());
	}

	@Test
	public void eFindByName() {
		assertEquals("test puttat", userService.findByName("test puttat").getUsername());
	}

	@Test
	public void gSave() {
		List<UserRoles> thisRoles = new ArrayList<>();
		User newUser = new User("jimbotest", "password", "email@email.com", thisRoles);
		newUser.getUseremails().add(new Useremail(newUser, "email2@email.com"));

		User addUser = userService.save(newUser);
		assertNotNull(addUser);

		User foundUser = userService.findUserById(addUser.getUserid());
		assertEquals(addUser.getUsername(), foundUser.getUsername());
	}

	@Test
	public void hUpdate() {
		List<UserRoles> thisRoles = new ArrayList<>();
		User newUser = new User("jimbotestv2", "password", "email@email.com", thisRoles);
		newUser.getUseremails().add(new Useremail(newUser, "email2@email.com"));

		User updateUser = userService.update(newUser, userService.findByName("jimbotest").getUserid());
		assertEquals("jimbotestv2", updateUser.getUsername());
	}

	@Test
	public void iGetCountUserEmails() {
		assertEquals(28, userService.getCountUserEmails().size());
	}

	@Test
	public void jDeleteUserRole() {
		// System.out.println(userService.findUserById(7).getRoles());
	}

	@Test
	public void kAddUserRole() {
	}
}