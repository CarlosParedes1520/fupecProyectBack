package com.bolsadeidead.springboot.backend.fupec.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.models.SeguimientoCliente;
import com.bolsadeidead.springboot.backend.fupec.models.UserAuth;
import com.bolsadeidead.springboot.backend.fupec.services.SeguimientoClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.UserService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;

/*
 * Controlador para el crud de Seguimientos
 * Configuracion de endoints para el api HTTP
 * 
 * */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SeguimientoClienteRestController {

	// instaciomos las clases
	@Autowired
	private SeguimientoClienteService seguimientoClienteService;
	@Autowired
	private UserService userService;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private ListaErrores listaErrores;

	// metodo para buscar por id
	@GetMapping("/seguimiento/buscar/{id}")
	public ResponseEntity<?> buscarSeguimientoClientePorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe ese seguimiento", "id invalido");
		}
		SeguimientoCliente seguimientoCliente = seguimientoClienteService.buscarIdSeguimientoCliente(id);
		if (seguimientoCliente == null) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existe datos con id: " + id);
		}
		return respuestaAccion.accionCumplida(true, "Datos de seguimiento", seguimientoCliente);
	}

	// paginar intereses activos
	@GetMapping("/seguimiento/lista/{page}")
	public Page<SeguimientoCliente> indicePagina(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);// pasamos el numero de pagina y la cantidad de elementos
		return seguimientoClienteService.obtenerSeguimientoClientePage(pageable);
	}

	// paginable de seguimientos por rango de fechas
	@GetMapping("seguimiento/buscar/fecha/{page}/{desde}/{hasta}")
	public ResponseEntity<?> buscarSeguimientosPorFecha(@PathVariable Integer page, @PathVariable Date desde,
			@PathVariable Date hasta) {
		Page<SeguimientoCliente> listaSeguimientos;
		try {
			Pageable pageable = PageRequest.of(page, 10);
			listaSeguimientos = seguimientoClienteService.buscarseguimientosPorFecha(pageable, desde, hasta);
		} catch (Exception e) {
			return respuestaAccion.errorBD(false, "No existen datos", "Fecha ingresada erronea");
		}
		if (listaSeguimientos.isEmpty()) {
			return respuestaAccion.errorBD(false, "No existe seguimientos con esa fecha", "Fecha inválida");
		}
		return respuestaAccion.accionCumplida(true, "Datos seguimientos", listaSeguimientos);
	}

	// para reporte por rango fechas y nivel satisfaccion
	@GetMapping("seguimiento/reporte/{desde}/{hasta}/{nivel}")
	public ResponseEntity<?> buscarSeguimientosPorFechaNivelSatisfaccionReporte(@PathVariable Date desde,
			@PathVariable Date hasta, @PathVariable Integer nivel) {
		List<SeguimientoCliente> listaSeguimientos;
		try {
			listaSeguimientos = seguimientoClienteService.buscarseguimientosPorFechaNivelReporte(desde, hasta, nivel);
		} catch (Exception e) {
			return respuestaAccion.errorBD(false, "No existen datos", "Fecha ingresada erronea");
		}
		if (listaSeguimientos.isEmpty()) {
			return respuestaAccion.errorBD(false, "No existe seguimientos con esa fecha", "Fecha inválida");
		}
		return respuestaAccion.accionCumplida(true, "Datos seguimientos", listaSeguimientos);
	}

	// crear un seguimiento nuevo
	@PostMapping("/seguimiento/crear")
	public ResponseEntity<?> crearSeguimientoCliente(@Valid @RequestBody SeguimientoCliente seguimientoCliente,
			BindingResult resultado) {
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		Integer nivelSatisfaccion = seguimientoCliente.getNivelSatisfaccion();
		if (nivelSatisfaccion > 5 || nivelSatisfaccion < 1) {
			return respuestaAccion.respuestaValidacion(false, "Nivel de satisfacción debe de ser entre 1-5 ",
					"Nivel satisfacción invalido");
		}
		// para pasar los datos del usuario que creo el cliente
		UserAuth user = userService.findById(seguimientoCliente.getUser().getId());
		SeguimientoCliente nuevoSeguimientoCliente = null;
		// si se guarda correctamente el seguimiento
		try {
			seguimientoCliente.setUser(user);
			seguimientoCliente.persisteciaFechaGradabo();
			nuevoSeguimientoCliente = seguimientoClienteService.crearSeguimientoCliente(seguimientoCliente);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar un nuevo interés",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Datos guardados con éxito", nuevoSeguimientoCliente);
	}

	// actualizamos seguimiento
	@PutMapping("/seguimiento/act/{id}")
	public ResponseEntity<?> actualizarSeguimientoCliente(@Valid @RequestBody SeguimientoCliente seguimientoCliente,
			BindingResult resultado, @PathVariable Long id) {
		SeguimientoCliente seguimientoClienteActual = seguimientoClienteService.buscarIdSeguimientoCliente(id);
		SeguimientoCliente seguimientoClienteActualizado = null;
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		Integer nivelSatisfaccion = seguimientoCliente.getNivelSatisfaccion();
		if (nivelSatisfaccion > 5 || nivelSatisfaccion < 1) {
			return respuestaAccion.respuestaValidacion(false, "Nivel de satisfacción debe de ser entre 1-5 ",
					"Nivel satisfacción invalido");
		}
		if (seguimientoClienteActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe ese seguimiento en la Base de Datos",
					seguimientoClienteActual);
		}
		// verificamos que se actualice el SeguimientoCliente
		try {
			seguimientoClienteActual.setFecha(seguimientoCliente.getFecha());
			seguimientoClienteActual.setNivelSatisfaccion(seguimientoCliente.getNivelSatisfaccion());
			seguimientoClienteActual.setRazonInconveniente(seguimientoCliente.getRazonInconveniente());
			seguimientoClienteActual.setSugerenciaCliente(seguimientoCliente.getSugerenciaCliente());
			seguimientoClienteActual.setObservacion(seguimientoCliente.getObservacion());
			seguimientoClienteActual.setEliminado(seguimientoCliente.getEliminado());
			seguimientoClienteActual.setCliente(seguimientoCliente.getCliente());
			seguimientoClienteActualizado = seguimientoClienteService.crearSeguimientoCliente(seguimientoClienteActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el seguimiento en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Datos actualizados con éxito", seguimientoClienteActualizado);
	}

	// eliminamos seguimiento por id
	@DeleteMapping("/seguimiento/borrar/{id}")
	public ResponseEntity<?> borrarSeguimientoCliente(@PathVariable Long id) {
		SeguimientoCliente seguimientoCliente = seguimientoClienteService.buscarIdSeguimientoCliente(id);
		if (seguimientoCliente == null) {
			return respuestaAccion.errorBD(false, "No existe ese seguimiento en la Base de Datos", "id inválido");
		}
		try {
			seguimientoClienteService.eliminarPorId(id);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el seguimiento en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Seguimiento borrado con éxito", "borrado");
	}
}
