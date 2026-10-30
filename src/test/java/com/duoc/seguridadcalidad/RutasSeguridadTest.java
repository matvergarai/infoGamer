package com.duoc.seguridadcalidad;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

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
	void busquedaDeEventosEsPublica() throws Exception {
		mockMvc.perform(get("/recetas/eventos").contextPath("/recetas").param("videojuego", "Valorant"))
				.andExpect(status().isOk())
				.andExpect(view().name("eventos"));
	}

	@Test
	void fichaDeEventoPideLogin() throws Exception {
		mockMvc.perform(get("/recetas/eventos/1").contextPath("/recetas"))
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

	@Test
	void apiDeLoginDevuelveJwtYLaFichaPrivadaLoAcepta() throws Exception {
		MvcResult login = mockMvc.perform(post("/recetas/api/login").contextPath("/recetas")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"ana\",\"password\":\"ana123\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.tipo").value("Bearer"))
				.andExpect(jsonPath("$.token").isNotEmpty())
				.andReturn();
		String token = JsonPath.read(login.getResponse().getContentAsString(), "$.token");

		mockMvc.perform(get("/recetas/api/eventos/1").contextPath("/recetas"))
				.andExpect(status().isUnauthorized());

		mockMvc.perform(get("/recetas/api/eventos/1").contextPath("/recetas")
				.header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.titulo").value("Copa Valorant Sur"))
				.andExpect(jsonPath("$.requisitos").isNotEmpty());
	}

	@Test
	void busquedaApiEsPublica() throws Exception {
		mockMvc.perform(get("/recetas/api/eventos").contextPath("/recetas").param("modalidad", "Online"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[?(@.nombre == 'Taller de speedrunning')]").exists())
				.andExpect(jsonPath("$[0].requisitos").doesNotExist());
	}
}
