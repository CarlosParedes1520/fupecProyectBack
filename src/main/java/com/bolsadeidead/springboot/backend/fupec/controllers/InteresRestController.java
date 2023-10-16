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
import com.bolsadeidead.springboot.backend.fupec.models.Interes;
import com.bolsadeidead.springboot.backend.fupec.services.ClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.InteresService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;

/*
 * Controlador para el crud de intereses
 * Configuracion de endoints para el api HTTP
 * 
 * */

@CrossOrigin("*")
@RestController
@RequestMapping("/api")

public class InteresRestController {
	
	// instanciamos clases
	@Autowired
	private InteresService interesService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private ListaErrores listaErrores;

	// metodo para buscar por id
	@GetMapping("/interes/buscar/{id}")
	public ResponseEntity<?> buscarInteresPorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe ese interés", "id invalido");
		}
		Interes interes = interesService.buscarIdInteres(id);
		if (interes == null) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existe datos con id: " + id);
		}
		return respuestaAccion.accionCumplida(true, "Datos de interés", interes);
	}

	// paginar los intereses
	@GetMapping("/interes/lista/{page}")
	public Page<Interes> indicePagina(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);// pasamos el numero de pagina y la cantidad de elementos
		return interesService.obtenerInteresPage(pageable);
	}

	// lista intereses por nombre
	@GetMapping("/intereses")
	public ResponseEntity<?> listarInteresesPorNombre() {
		List<Interes> listaIntereses= interesService.listaInteresFiltradoPorNombre();
		if (listaIntereses.size() == 0) {
			return respuestaAccion.errorBD(false, "No existe intereses", "tabla vacía");
		}
		return respuestaAccion.accionCumplida(true, "Datos intereses", listaIntereses);
	}

	// paginable de interes por nombre filtrado
	@GetMapping("/interes/nombre/{page}/{termino}")
	public ResponseEntity<?> filtrarInteresNombre(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de búsqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Interes> listaIntereses = interesService.filtrarInteresNombre(pageable, termino);
		if (listaIntereses.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen intereses con ese nombre");
		}
		return respuestaAccion.accionCumplida(true, "Lista de intereses", listaIntereses);
	}

	// crear un interes nuevo
	@PostMapping("/interes/crear")
	public ResponseEntity<?> crearInteres(@Valid @RequestBody Interes interes, BindingResult resultado) {
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		Interes nuevoInteres = null;
		// si se guarda correctamente el interes
		try {
			interes.persisteciaFechaGradabo();
			nuevoInteres = interesService.crearInteres(interes);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar un nuevo interés",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Datos guardados con éxito", nuevoInteres);
	}

	// actualizamos interes
	@PutMapping("/interes/act/{id}")
	public ResponseEntity<?> actualizarInteres(@Valid @RequestBody Interes interes, BindingResult resultado,
			@PathVariable Long id) {
		Interes interesActual = interesService.buscarIdInteres(id);
		Interes interesActualizada = null;
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		if (interesActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe ese interés en la Base de Datos", interesActual);
		}
		// verificamos que se actualice el interes
		try {
			interesActual.setNombre(interes.getNombre());
			interesActual.setDescripcion(interes.getDescripcion());
			interesActual.setEliminado(interes.getEliminado());
			interesActualizada = interesService.crearInteres(interesActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el interés en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Datos actualizados con éxito", interesActualizada);
	}

	// para desactivar el interes como inactivo
	@DeleteMapping("/interes/borrar/{id}")
	public ResponseEntity<?> borrarInteres(@PathVariable Long id) {
		Interes interes = interesService.buscarIdInteres(id);
		if (interes == null) {
			return respuestaAccion.errorBD(false, "No existe ese interes en la Base de Datos", "id inválido");
		}
		// verificamos si algun cliente tiene asignado el interes a eliminar
		List<Cliente> listaInteresClientes = clienteService.buscarInteresClientes(interes);
		if (listaInteresClientes != null && listaInteresClientes.size() > 0) {
			return respuestaAccion.respuestaValidacion(false, "Acción denegada: ",
					"Este interes tiene " + listaInteresClientes.size() + " cliente(s) asignado(s).");
		}
		// cambiamos el valor de eliminado
		try {
			interes.setEliminado(false);
			interesService.crearInteres(interes);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el interés en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Interés borrado con éxito", "borrada");
	}
}
