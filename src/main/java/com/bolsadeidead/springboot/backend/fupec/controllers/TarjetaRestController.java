package com.bolsadeidead.springboot.backend.fupec.controllers;

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

import com.bolsadeidead.springboot.backend.fupec.models.Cliente;
import com.bolsadeidead.springboot.backend.fupec.models.Tarjeta;
import com.bolsadeidead.springboot.backend.fupec.services.ClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.TarjetaService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;

/*
 * Controlador para el crud de Tarjetas
 * Configuracion de endoints para el api HTTP
 * 
 * */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TarjetaRestController {
	@Autowired
	private ListaErrores listaErrores;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private TarjetaService tarjetaService;
	@Autowired
	private ClienteService clienteService;

	// paginable de tarjetas activas
	@GetMapping("/tarjeta/lista/{page}")
	public Page<Tarjeta> indicePagina(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);// pasamos el numero de pagina y la cantidad de elementos
		return tarjetaService.ObtenerTarjetasPage(pageable);
	}

	// buscar tarjeta por id
	@GetMapping("/tarjeta/{id}")
	public ResponseEntity<?> buscarTarjetaPorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe esa tarjeta", "id invalido");
		}
		Tarjeta tarjeta = tarjetaService.buscarIdTarjeta(id);
		if (tarjeta == null) {
			return respuestaAccion.errorBD(false, "No existe ese tarjeta", "id invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos del tarjeta", tarjeta);
	}

	// lista de tarjetas activas por codigo
	@GetMapping("/tarjeta/codigo/{termino}")
	public ResponseEntity<?> listaTarjetasCodigo(@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		List<Tarjeta> listaTarjetas = tarjetaService.listaEmailVendedor(termino);
		if (listaTarjetas.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existen tarjetas con ese código");
		}
		return respuestaAccion.accionCumplida(true, "Lista de tarjetas", listaTarjetas);
	}

	// paginable de tarjetas por codigo
	@GetMapping("/tarjeta/buscar/{page}/{termino}")
	public ResponseEntity<?> filtrarTarjetaCodigo(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de búsqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Tarjeta> listaTarjetas = tarjetaService.filtrarTarjetasCodigo(pageable, termino);
		if (listaTarjetas.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen tarjetas con ese código");
		}
		return respuestaAccion.accionCumplida(true, "Lista de tarjetas", listaTarjetas);
	}

	// creamos tarjeta
	@PostMapping("/tarjeta/crear")
	public ResponseEntity<?> crearTarjeta(@Valid @RequestBody Tarjeta tarjeta, BindingResult resultado) {
		Tarjeta nuevaTarjeta = null;
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// validamos que no exista el mismo codigo
		Tarjeta tarjetaCodigo = tarjetaService.buscarcodigoTarjeta(tarjeta.getCodigo());
		if (tarjetaCodigo != null) {
			return respuestaAccion.errorBD(false, "Ya existe una tarjeta con ese código", "Código existente");
		}
		// creamos una nueva tarjeta
		try {
			tarjeta.peristenciaFechaGrabado();
			nuevaTarjeta = tarjetaService.crearTarjeta(tarjeta);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar la tarjeta",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}

		return respuestaAccion.accionCumplida(true, "Tarjeta guardada con éxito", nuevaTarjeta);
	}

	// actualizamos tarjeta
	@PutMapping("/tarjeta/act/{id}")
	public ResponseEntity<?> actualizarTarjeta(@Valid @RequestBody Tarjeta tarjeta, BindingResult resultado,
			@PathVariable Long id) {
		Tarjeta tarjetaActual = tarjetaService.buscarIdTarjeta(id);
		Tarjeta tarjetaActualizada = null;
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// si la tarjeta no existe
		if (tarjetaActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe esa tarjeta en la Base de Datos", tarjetaActual);
		}
		// si existe otra tarjeta con el mismo codigo
		Tarjeta tarjetaCodigo = tarjetaService.buscarcodigoTarjeta(tarjeta.getCodigo());
		if (tarjetaCodigo != null && tarjetaCodigo.getId() != id) {
			return respuestaAccion.errorBD(false, "Ya existe una tarjeta con ese código", "Código existente");
		}
		// verificamos que se actualice la tarjeta
		try {
			tarjetaActual.setActivo(tarjeta.getActivo());
			tarjetaActual.setCodigo(tarjeta.getCodigo());
			tarjetaActual.setFechaCaducar(tarjeta.getFechaCaducar());
			tarjetaActualizada = tarjetaService.crearTarjeta(tarjetaActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar la tarjeta en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Tarjeta actualizada con éxito", tarjetaActualizada);
	}

	// para desactivar la tarjeta como inactivo
	@DeleteMapping("/tarjeta/borrar/{id}")
	public ResponseEntity<?> borrarTarjeta(@PathVariable Long id) {
		Tarjeta tarjeta = tarjetaService.buscarIdTarjeta(id);
		if (tarjeta == null) {
			return respuestaAccion.errorBD(false, "No existe esa tarjeta en la Base de Datos", "id inválido");
		}
		// verifica si una tarjeta tiene asigando un cliente
		Cliente clienteTarjeta = clienteService.buscarTarjetaCliente(tarjeta);
		if (clienteTarjeta != null) {
			return respuestaAccion.respuestaValidacion(false, "Acción denegada: ",
					"Esta tarjeta tiene asigando un cliente: ".concat(clienteTarjeta.getUsuario().getNombre()));
		}
		// cambia el estado de eliminado
		try {
			tarjeta.setActivo(false);
			tarjetaService.crearTarjeta(tarjeta);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar la tarjeta en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Tarjeta borrada con éxito", "borrada");
	}

}
