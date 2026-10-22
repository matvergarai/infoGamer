package com.duoc.seguridadcalidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeguridadcalidadApplication {

	public static void main(String[] args) {
		String wallet = System.getenv("ORACLE_TNS_ADMIN");
		if (wallet == null || wallet.isBlank()) {
			wallet = "D:/Datos/Escritorio/SeguridadyCalidadenDS/wallet";
		}
		wallet = wallet.replace('\\', '/');
		System.setProperty("oracle.net.tns_admin", wallet);
		System.setProperty("TNS_ADMIN", wallet);
		SpringApplication.run(SeguridadcalidadApplication.class, args);
	}

}
