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
import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;
import com.bolsadeidead.springboot.backend.fupec.services.ClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.VendedorService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;
import com.bolsadeidead.springboot.backend.fupec.validaciones.Validaciones;

/*
 * Controlador para el crud de Vendedores
 * Configuracion de endoints para el api HTTP
 * 
 * */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class VendedorRestController {

	// instanciamos las clases
	@Autowired
	private ListaErrores listaErrores;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private Validaciones validacionesCampos;
	@Autowired
	private VendedorService vendedorService;
	@Autowired
	private ClienteService clienteService;

	// listar vededores paginador
	@GetMapping("/vendedor/lista/{page}")
	public Page<Vendedor> indicePagina(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		return vendedorService.ObtenerVendedoresPage(pageable);
	}

	// endpoint para las lita de vendedores por emal
	@GetMapping("/vendedor/listaemail/{termino}")
	public ResponseEntity<?> listaVendedoresEmail(@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		List<Vendedor> listaVendedores = vendedorService.listaEmailVendedor(termino);
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen vendedores con ese termino");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}

	// buscar vendedor por id
	@GetMapping("/vendedor/{id}")
	public ResponseEntity<?> buscarVendedorPorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe ese vendedor", "id invalido");
		}
		Vendedor vendedor = vendedorService.buscarIdVendedor(id);
		;
		if (vendedor == null) {
			return respuestaAccion.errorBD(false, "No existe ese vendedor", "id invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos del vendedor", vendedor);
	}

	// paginable de vendedores fitrando nombre
	@GetMapping("/vendedor/nombres/{page}/{termino}")
	public ResponseEntity<?> filtrarVendedorNombre(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Vendedor> listaVendedores = vendedorService.filtrarVendedorNombre(pageable, termino);
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen vendedores con ese termino");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}

	// paginable de vendedores fitrando apellido
	@GetMapping("/vendedor/apellidos/{page}/{termino}")
	public ResponseEntity<?> filtrarVendedorApellidos(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Vendedor> listaVendedores = vendedorService.filtrarVendedorApellido(pageable, termino);
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen vendedores con ese termino");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}

	// paginable de vendedores fitrando email
	@GetMapping("/vendedor/email/{page}/{email}")
	public ResponseEntity<?> filtrarVendedoremail(@PathVariable Integer page, @PathVariable String email) {
		if (email.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Vendedor> listaVendedores = vendedorService.filtrarVendedorEmail(pageable, email);
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existen vendedores con ese email");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}

	// endpoint para las lita de vendedores con clientes
	@GetMapping("/vendedor/clientes")
	public ResponseEntity<?> listaVendedoresClientes() {
		List<Vendedor> listaVendedores = vendedorService.listaVendedoresClientes();
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen vendedores con clientes");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}
	// endpoint para las lita de vendedores por nombre con clientes
	@GetMapping("/vendedor/clientes/nombres/{termino}")
	public ResponseEntity<?> listaVendedoresClientes(@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		List<Vendedor> listaVendedores = vendedorService.listaVendedoresClientesNombres(termino);
		if (listaVendedores.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen vendedores con ese término");
		}
		return respuestaAccion.accionCumplida(true, "Lista de vendedores", listaVendedores);
	}

	// crear datos del nuevo vendedor
	@PostMapping("/vendedor/crear")
	public ResponseEntity<?> crearVendedor(@Valid @RequestBody Vendedor vendedor, BindingResult resultado) {
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// validaciones
		if (validacionesCampos.validarEmail(vendedor.getEmail()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Email ingresado no es válido", "email invalido");
		}
		if (validacionesCampos.validarTelefono(vendedor.getTelefono()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Teléfono o movil ingresado no es válido",
					"télefono inválido");
		}
		Vendedor nuevoVendedor = null;
		Vendedor vendedorEmail = vendedorService.buscarEmailVendedor(vendedor.getEmail());
		if (vendedorEmail != null) {
			return respuestaAccion.errorBD(false, "Ya existe un vendedor con ese Email", "email duplicado");
		}
		// si se guarda correctamente el vendedor
		try {
			vendedor.persisteciaFechaGradabo();
			nuevoVendedor = vendedorService.crearVendedor(vendedor);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar un nuevo vendedor",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Vendedor guardado con éxito", nuevoVendedor);
	}

	// actualizamos vendedor
	@PutMapping("/vendedor/act/{id}")
	public ResponseEntity<?> actualizarVendedor(@Valid @RequestBody Vendedor vendedor, BindingResult resultado,
			@PathVariable Long id) {
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// validaciones
		if (validacionesCampos.validarEmail(vendedor.getEmail()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Email ingresado no es válido", "email inválido");
		}
		if (validacionesCampos.validarTelefono(vendedor.getTelefono()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Teléfono o movil ingresado no es válido",
					"télefono inválido");
		}
		Vendedor vendedorActual = vendedorService.buscarIdVendedor(id);
		Vendedor vendedorActualizado = null;
		if (vendedorActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe ese vendedor en la Base de Datos", vendedorActual);
		}
		Vendedor vendedorEmail = vendedorService.buscarEmailVendedor(vendedor.getEmail());
		if (vendedorEmail != null && vendedorEmail.getId() != id) {
			return respuestaAccion.errorBD(false, "Ya existe un vendedor con ese Email", "email duplicado");
		}
		// verificamos si se actualiza el vendedor
		try {
			vendedorActual.setApellidos(vendedor.getApellidos());
			vendedorActual.setEliminado(vendedor.getEliminado());
			vendedorActual.setNombres(vendedor.getNombres());
			vendedorActual.setEmail(vendedor.getEmail());
			vendedorActual.setTelefono(vendedor.getTelefono());
			vendedorActualizado = vendedorService.crearVendedor(vendedorActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el Vendedor en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Vendedor actualizado con éxito", vendedorActualizado);
	}

	// borrar vendedor poner eliminado en false
	@DeleteMapping("/vendedor/borrar/{id}")
	public ResponseEntity<?> borrarVendedor(@PathVariable Long id) {
		Vendedor vendedor = vendedorService.buscarIdVendedor(id);
		if (vendedor == null) {
			return respuestaAccion.errorBD(false, "No existe ese vendedor", "id inválido");
		}
		// verificamos si un vendedor esta relacionado con 1 o mas clientes
		List<Cliente> clientesVendedor = clienteService.buscarVendedorClientes(vendedor);
		if (clientesVendedor != null && clientesVendedor.size() > 0) {
			return respuestaAccion.respuestaValidacion(false, "Acción denegada: ",
					"Este vendedor tiene " + clientesVendedor.size() + " cliente(s) asignado(s).");
		}
		// cambiamos el estado de eliminado
		try {
			vendedor.setEliminado(false);
			vendedorService.crearVendedor(vendedor);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar al vendedor de la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Vendedor borrado con éxito", "borrado");
	}

}
