package com.ceiba.adn.serviciobano.infraestructura.configuracion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.ceiba.adn.serviciobano.comun.puerto.Propiedades;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioBano;
import com.ceiba.adn.serviciobano.dominio.puerto.repositorio.RepositorioCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioActualizarBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioConsultasBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioCrearBano;
import com.ceiba.adn.serviciobano.dominio.servicio.bano.ServicioEliminarBano;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioActualizarCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCobrarCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioConsultasCuenta;
import com.ceiba.adn.serviciobano.dominio.servicio.cuenta.ServicioCrearCuenta;
import com.ceiba.adn.serviciobano.infraestructura.adaptador.propiedades.FabricaPropiedades;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

@Configuration
public class BeanServicio {

	@Bean
	public Propiedades crearPropiedadMensajesPorDefecto() {
		return FabricaPropiedades.propiedadMensajesPorDefecto();
	}

	@Bean
	public Propiedades crearPropiedadConfiguracionPorDefecto() {
		return FabricaPropiedades.propiedadConfiguracionPorDefecto();
	}

	@Bean
	public ServicioActualizarBano crearServicioActualizarBano(RepositorioBano repositorio,
			@Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg) {
		return new ServicioActualizarBano(repositorio, propMsg);
	}

	@Bean
	public ServicioConsultasBano crearServicioConsultasBano(RepositorioBano repositorio) {
		return new ServicioConsultasBano(repositorio);
	}

	@Bean
	public ServicioCrearBano crearServicioCrearBano(RepositorioBano repositorio,
			@Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg) {
		return new ServicioCrearBano(repositorio, propMsg);
	}

	@Bean
	public ServicioEliminarBano crearServicioEliminarBano(RepositorioBano repositorio,
			@Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg) {
		return new ServicioEliminarBano(repositorio, propMsg);
	}

	@Bean
	public ServicioCobrarCuenta crearServicioCobrarCuenta(RepositorioCuenta repositorio,
			@Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg,
			@Qualifier("crearPropiedadConfiguracionPorDefecto") Propiedades propConfig) {
		return new ServicioCobrarCuenta(repositorio, propMsg, propConfig);
	}

	@Bean
	public ServicioCrearCuenta crearServicioCrearCuenta(RepositorioCuenta repositorio, RepositorioBano repositorioBano,
			@Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg) {
		return new ServicioCrearCuenta(repositorio, repositorioBano, propMsg);
	}

	@Bean
	public ServicioActualizarCuenta crearServicioActualizarCuenta(RepositorioCuenta repositorio,
			RepositorioBano repositorioBano, @Qualifier("crearPropiedadMensajesPorDefecto") Propiedades propMsg) {
		return new ServicioActualizarCuenta(repositorio, repositorioBano, propMsg);
	}
	
	@Bean
	public ServicioConsultasCuenta crearServicioConsultasCuenta(RepositorioCuenta repositorio) {
		return new ServicioConsultasCuenta(repositorio);
	}

	public static final String FORMATO_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMATO_LOCAL_DATE = "yyyy-MM-dd";

	@Bean
	public ObjectMapper crearObjectMapper() {
		ObjectMapper objectMapper;

		JavaTimeModule module = new JavaTimeModule();
		LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(
				DateTimeFormatter.ofPattern(FORMATO_LOCAL_DATE_TIME));
		module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

		objectMapper = Jackson2ObjectMapperBuilder.json().modules(module)
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).build();

		return objectMapper;
	}

}
