package dev.mateusz.barber.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;
import dev.mateusz.barber.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="janusz123",roles={"CUSTOMER","MODERATOR","ADMIN"})
public class CrmControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
    public void listCustomer_shouldAddCustomerToModelAndRenderCustomerListView() throws Exception {
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		roles.add(new Role("ROLE_MODERATOR"));
		roles.add(new Role("ROLE_ADMIN"));
		
		User first = new User("jan", "asd", "Janusz", "Tracz", 666666666, "janusz@tracz.pl", roles);
		User secound = new User("Andrzeje", "asd", "Andrzej", "Tracz", 666556666, "andrzej@tracz.pl", roles);
		
		List<User> users = new ArrayList<User>();
		users.add(first);
		users.add(secound);
		
		Mockito.when(userService.getUsers()).thenReturn(users);
		
		mockMvc.perform(get("/crm/list"))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(view().name("list-customers"))
               .andExpect(forwardedUrl("/WEB-INF/view/list-customers.jsp"))
               .andExpect(model().attribute("users", is(users)));
    }
	
	
	@Test
	public void showFormForUpdate_shouldReturnExpected() throws Exception {
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		
		User theUser = new User("jan", "asd", "Janusz", "Tracz", 666666666, "janusz@tracz.pl", roles);
		theUser.setIdUser(10);
		
		Mockito.when(userService.getUserById(10)).thenReturn(theUser);
		
		CrmUser crmUser = new CrmUser();

		crmUser.setIdUser(theUser.getIdUser());
		crmUser.setUserName(theUser.getUserName());
		crmUser.setPassword(theUser.getPassword());
		crmUser.setMatchingPassword(theUser.getPassword());
		crmUser.setFirstName(theUser.getFirstName());
		crmUser.setLastName(theUser.getLastName());
		crmUser.setEmail(theUser.getEmail());
		crmUser.setPhoneNumber(theUser.getPhoneNumber());
		crmUser.setRoles(theUser.getRoles());
		
		mockMvc.perform(get("/crm/showFormForUpdate").param("idUser", "10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("customer-form"))
				.andExpect(forwardedUrl("/WEB-INF/view/customer-form.jsp"))
				.andExpect(model().attribute("crmUser", samePropertyValuesAs(crmUser)));
	}
	
}
