package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private List<User> userList;

	@Before
	public void setUp() throws Exception {
		userList = new ArrayList<>();

		Role r1 = new Role("admin");
		r1.setRoleid(1);

		Role r2 = new Role("user");
		r2.setRoleid(2);

		Role r3 = new Role("data");
		r3.setRoleid(3);

		ArrayList<UserRoles> admins = new ArrayList<>();
		admins.add(new UserRoles(new User(), r1));
		admins.add(new UserRoles(new User(), r2));
		admins.add(new UserRoles(new User(), r3));
		User u1 = new User(
			"testadmin",
			"password",
			"admin@lambdaschool.local",
			admins
		);
		u1.setUserid(100);

		u1.getUseremails().add(new Useremail(u1,"admin@email.local"));
		u1.getUseremails().get(0).setUseremailid(110);

		u1.getUseremails().add(new Useremail(u1,"admin@mymail.local"));
		u1.getUseremails().get(1).setUseremailid(120);

		userList.add(u1);

		ArrayList<UserRoles> datas = new ArrayList<>();
		datas.add(new UserRoles(new User(), r3));
		datas.add(new UserRoles(new User(), r2));
		User u2 = new User(
			"testcinnamon",
			"1234567",
			"cinnamon@lambdaschool.local",
			datas
		);
		u2.setUserid(200);

		u2.getUseremails().add(new Useremail(u2,"cinnamon@mymail.local"));
		u2.getUseremails().get(0).setUseremailid(210);

		u2.getUseremails().add(new Useremail(u2,"hops@mymail.local"));
		u2.getUseremails().get(1).setUseremailid(220);

		u2.getUseremails().add(new Useremail(u2,"bunny@email.local"));
		u2.getUseremails().get(2).setUseremailid(230);

		userList.add(u2);

		ArrayList<UserRoles> users = new ArrayList<>();
		users.add(new UserRoles(new User(), r2));
		User u3 = new User(
			"testbarnbarn",
			"ILuvM4th!",
			"barnbarn@lambdaschool.local",
			users
		);
		u3.setUserid(300);

		u3.getUseremails().add(new Useremail(u3,"barnbarn@email.local"));
		u3.getUseremails().get(0).setUseremailid(310);

		userList.add(u3);

		users = new ArrayList<>();
		users.add(new UserRoles(new User(), r2));
		User u4 = new User(
			"testputtat",
			"password",
			"puttat@school.lambda",
			users
		);
		u4.setUserid(400);

		userList.add(u4);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void listAllUsers() throws Exception {
		String apiUrl = "/users/users";
		Mockito.when(userService.findAll()).thenReturn(userList);
		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

		MvcResult r = mockMvc.perform(rb).andReturn();
		String tr = r.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		String er = mapper.writeValueAsString(userList);

		System.out.println("Expect - " + er);
		System.out.println("Actual - " + tr);
		assertEquals("Rest API Returns List", er, tr);
	}

	@Test
	public void getUserById() throws Exception {
		String apiUrl = "/users/user/100";
		Mockito.when(userService.findUserById(100)).thenReturn(userList.get(0));
		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

		MvcResult r = mockMvc.perform(rb).andReturn();
		String tr = r.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		String er = mapper.writeValueAsString(userList.get(0));

		System.out.println("Expect - " + er);
		System.out.println("Actual - " + tr);
		assertEquals("Rest API Returns List", er, tr);
	}

	@Test
	public void getUserByName() throws Exception {
		String apiUrl = "/users/user/name/testputtat";
		Mockito.when(userService.findByName("testputtat")).thenReturn(userList.get(3));
		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

		MvcResult r = mockMvc.perform(rb).andReturn();
		String tr = r.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		String er = mapper.writeValueAsString(userList.get(3));

		System.out.println("Expect - " + er);
		System.out.println("Actual - " + tr);
		assertEquals("Rest API Returns List", er, tr);
	}

	@Test
	public void getUserLikeName() throws Exception {
		String apiUrl = "/users/user/name/like/test";
		Mockito.when(userService.findByNameContaining("test")).thenReturn(userList);
		RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

		MvcResult r = mockMvc.perform(rb).andReturn();
		String tr = r.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		String er = mapper.writeValueAsString(userList);

		System.out.println("Expect - " + er);
		System.out.println("Actual - " + tr);
		assertEquals("Rest API Returns List", er, tr);
	}

	@Test
	public void addNewUser() throws Exception {
		String apiUrl = "/users/user";
		ArrayList<UserRoles> thisRoles = new ArrayList<>();
		User u5 = new User("jimbotest",  "password", "email@email.com", thisRoles);
		u5.setUserid(500);

		ObjectMapper mapper = new ObjectMapper();
		String userString = mapper.writeValueAsString(u5);
		Mockito.when(userService.save(any(User.class))).thenReturn(u5);

		RequestBuilder rb = MockMvcRequestBuilders
			.post(apiUrl)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(userString);

		mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateFullUser() throws Exception {
		String apiUrl = "/users/user/100";
		ArrayList<UserRoles> thisRoles = new ArrayList<>();
		User newUser = new User("jimbotestv2",  "password", "email@email.com", thisRoles);
		newUser.setUserid(100);

		ObjectMapper mapper = new ObjectMapper();
		String userString = mapper.writeValueAsString(newUser);
		Mockito.when(userService.save(any(User.class))).thenReturn(newUser);

		RequestBuilder rb = MockMvcRequestBuilders
			.put(apiUrl, 100L)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(userString);

		mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void updateUser() throws Exception {
		String apiUrl = "/users/user/{userid}";
		ArrayList<UserRoles> thisRoles = new ArrayList<>();
		User newUser = new User("jimbotestv3",  "pass", "dope@email.com", thisRoles);
		newUser.setUserid(200);

		Mockito.when(userService.update(newUser, 200)).thenReturn(newUser);
		ObjectMapper mapper = new ObjectMapper();
		String userString = mapper.writeValueAsString(newUser);

		RequestBuilder rb = MockMvcRequestBuilders
			.patch(apiUrl, 200)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(userString);

		mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void deleteUserById() throws Exception {
		String apiUrl = "/users/user/{userid}";
		RequestBuilder rb = MockMvcRequestBuilders
			.delete(apiUrl, "300")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(rb).andExpect((status().isOk())).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getNumUserEmails() throws Exception {
		// String apiUrl = "/user/email/count";
		// System.out.println(userService.getCountUserEmails());
		// Mockito.when(userService.getCountUserEmails().size()).thenReturn(userList.());
		// RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
		//
		// MvcResult r = mockMvc.perform(rb).andReturn();
		// String tr = r.getResponse().getContentAsString();
		//
		// ObjectMapper mapper = new ObjectMapper();
		// String er = mapper.writeValueAsString(userList.size());
		//
		// System.out.println("Expect - " + er);
		// System.out.println("Actual - " + tr);
		// assertEquals("Rest API Returns List", er, tr);
	}

	@Test
	public void deleteUserRoleByIds() throws Exception {
		String apiUrl = "/user/{userid}/role/{roleid}";
		RequestBuilder rb = MockMvcRequestBuilders
			.delete(apiUrl, 200, 2)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(rb).andExpect((status().isOk())).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void postUserRoleByIds() {
	}
}