package com.duoc.seguridadcalidad;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
class RutasSeguridadTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@BeforeEach
	void preparar() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	void inicioEsPublico() throws Exception {
		mockMvc.perform(get("/recetas/home").contextPath("/recetas"))
				.andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	void registroEsPublico() throws Exception {
		mockMvc.perform(get("/recetas/registro").contextPath("/recetas"))
				.andExpect(status().isOk())
				.andExpect(view().name("registro"));
	}

	@Test
	void eventosPideLogin() throws Exception {
		mockMvc.perform(get("/recetas/eventos").contextPath("/recetas"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/recetas/login"));
	}

	@Test
	void perfilPideLogin() throws Exception {
		mockMvc.perform(get("/recetas/perfil").contextPath("/recetas"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/recetas/login"));
	}

	@Test
	void loginEsPublico() throws Exception {
		mockMvc.perform(get("/recetas/login").contextPath("/recetas"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	void perfilAutenticadoSeRenderiza() throws Exception {
		mockMvc.perform(get("/recetas/perfil").contextPath("/recetas").with(user("ana").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("perfil"));
	}

	@Test
	void usuarioAutenticadoVeElCatalogo() throws Exception {
		mockMvc.perform(get("/recetas/eventos").contextPath("/recetas").with(user("ana").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("eventos"));
	}

	@Test
	void fichaDeEventoEsPrivadaYExiste() throws Exception {
		mockMvc.perform(get("/recetas/eventos/1").contextPath("/recetas").with(user("carlos").roles("USER")))
				.andExpect(status().isOk())
				.andExpect(view().name("evento"));
	}
}
