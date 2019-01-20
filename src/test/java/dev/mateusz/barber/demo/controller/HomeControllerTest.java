package dev.mateusz.barber.demo.controller;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dev.mateusz.barber.demo.dto.DtoOrder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "janusz123", roles = { "CUSTOMER", "MODERATOR", "ADMIN" })
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	public void shouldReturnExpectedForLoginPage() throws Exception {
		mockMvc.perform(get("/showLoginPage"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(view().name("fancy-login"))
				.andExpect(forwardedUrl("/WEB-INF/view/fancy-login.jsp"));
	}

	@Test
	public void shouldReturnExpectedForHomePage() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("home"))
				.andExpect(forwardedUrl("/WEB-INF/view/home.jsp"))
				.andExpect(model().attribute("dtoOrder", samePropertyValuesAs(new DtoOrder())));
	}

	@Test
	public void shouldReturnExpectedForLogoutPage() throws Exception {
		mockMvc.perform(get("/logout"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("fancy-login"))
				.andExpect(forwardedUrl("/WEB-INF/view/fancy-login.jsp"));
	}

}
