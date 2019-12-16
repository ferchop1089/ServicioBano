package com.ceiba.adn.serviciobano.infraestructura.controlador;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCobrar;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoCuenta;
import com.ceiba.adn.serviciobano.aplicacion.comando.ComandoRespuesta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorActualizarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCobrarCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorConsultasCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorCrearCuenta;
import com.ceiba.adn.serviciobano.aplicacion.manejador.ManejadorPagarCuenta;

@CrossOrigin
@RestController
@RequestMapping("/servicio-bano/cuenta")
public class ComandoControladorCuenta {

	private ManejadorCrearCuenta crearCuenta;
	private ManejadorActualizarCuenta actualizarCuenta;
	private ManejadorCobrarCuenta cobrarCuenta;
	private ManejadorPagarCuenta pagarCuenta;
	private ManejadorConsultasCuenta consultarCuenta;

	public ComandoControladorCuenta(ManejadorCrearCuenta crearCuenta, ManejadorActualizarCuenta actualizarCuenta,
			ManejadorCobrarCuenta cobrarCuenta, ManejadorPagarCuenta pagarCuenta,
			ManejadorConsultasCuenta consultarCuenta) {
		this.crearCuenta = crearCuenta;
		this.actualizarCuenta = actualizarCuenta;
		this.cobrarCuenta = cobrarCuenta;
		this.pagarCuenta = pagarCuenta;
		this.consultarCuenta = consultarCuenta;
	}

	@PostMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<Long> crear(@RequestBody ComandoCuenta cuenta) {
		return crearCuenta.ejecutar(cuenta);
	}

	@PutMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void actualizar(@RequestBody ComandoCuenta cuenta) {
		actualizarCuenta.ejecutar(cuenta);
	}

	@GetMapping(value = "/{idBano}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<ComandoCuenta> consultarPorIdBano(@PathVariable Long idBano) {
		return consultarCuenta.ejecutar(idBano);
	}

	@GetMapping(value = "/cobrar/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ComandoRespuesta<ComandoCobrar> cobrar(@PathVariable Long id) {
		return cobrarCuenta.ejecutar(id);
	}

	@PutMapping(value = "/pagar", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public void pagar(@RequestBody ComandoCuenta cuenta) {
		pagarCuenta.ejecutar(cuenta);
	}

}
