package com.duoc.seguridadcalidad.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistroForm {

	@NotBlank(message = "Ingresa tu nombre")
	@Size(max = 60, message = "El nombre no puede superar 60 caracteres")
	private String nombre;

	@NotBlank(message = "Ingresa tu correo")
	@Email(message = "El correo no es válido")
	private String email;

	@NotBlank(message = "Ingresa una contraseña")
	@Size(min = 6, max = 40, message = "La contraseña debe tener entre 6 y 40 caracteres")
	private String password;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
