package com.lambdaschool.usermodel.controllers;

import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void listAllUsers() {
	}

	@Test
	public void getUserById() {
	}

	@Test
	public void getUserByName() {
	}

	@Test
	public void getUserLikeName() {
	}

	@Test
	public void addNewUser() {
	}

	@Test
	public void updateFullUser() {
	}

	@Test
	public void updateUser() {
	}

	@Test
	public void deleteUserById() {
	}

	@Test
	public void getNumUserEmails() {
	}

	@Test
	public void deleteUserRoleByIds() {
	}

	@Test
	public void postUserRoleByIds() {
	}
}