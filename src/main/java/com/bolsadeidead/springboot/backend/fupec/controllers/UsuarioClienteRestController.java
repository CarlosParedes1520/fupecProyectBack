package com.bolsadeidead.springboot.backend.fupec.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;
import com.bolsadeidead.springboot.backend.fupec.services.UsuarioClienteService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;
import com.bolsadeidead.springboot.backend.fupec.validaciones.Validaciones;

/*
 * Controlador para el crud de usuario cliente
 * Configuracion de endoints para el api HTTP
 * 
 * */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UsuarioClienteRestController {
	
	// instanciamos clases
	@Autowired
	private UsuarioClienteService usuarioClienteService;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private Validaciones validacionesCampos;
	@Autowired
	private ListaErrores listaErrores;

	// metodo para buscar por id
	@GetMapping("usucli/buscar/{id}")
	public ResponseEntity<?> buscarUsuarioClientePorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe ese Usuario ", "id invalido");
		}
		UsuarioCliente usuarioCliente = usuarioClienteService.buscarUsuarioClienteId(id);
		if (usuarioCliente == null) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existe datos con id: " + id);
		}
		return respuestaAccion.accionCumplida(true, "Datos del usuario", usuarioCliente);
	}

	// metodo para buscar por nombre, regresa una lista
	@GetMapping("usucli/nombre/{nombre}")
	public ResponseEntity<?> listaUsuariosClientesPorNombre(@PathVariable String nombre) {
		if (nombre.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de búsqueda vacío");
		}
		List<UsuarioCliente> listaUsuariosClientes = usuarioClienteService.listaUsuariosClientesNombre(nombre);
		if (listaUsuariosClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existe datos con : ".concat(nombre));
		}
		return respuestaAccion.accionCumplida(true, "Datos de usuarios por nombre", listaUsuariosClientes);
	}

	// metodo para buscar por nombre usuario en este caso el usuarioes el email
	@GetMapping("usucli/usuario/{usuario}")
	public ResponseEntity<?> listaUsuariosClientesPorUsuario(@PathVariable String usuario) {
		if (usuario.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de búsqueda vacío");
		}
		List<UsuarioCliente> listaUsuariosClientes = usuarioClienteService.listaUsuariosClientesUsuario(usuario);
		if (listaUsuariosClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existe datos con : ".concat(usuario));
		}
		return respuestaAccion.accionCumplida(true, "Datos de usuarios por nombre usuario", listaUsuariosClientes);
	}
	// crear un usuarioCliente nuevo
	@PostMapping("/usucli/crear")
	public ResponseEntity<?> crearUsuarioCliente(@Valid @RequestBody UsuarioCliente usuarioCliente,
			BindingResult resultado) {
		System.out.println(usuarioCliente.getTelefono());
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// validaciones
		// validación: Email
		if (validacionesCampos.validarEmail(usuarioCliente.getUsuario()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Email ingresado no es válido", "email invalido");
		}
		// validación: Telefono
		if (validacionesCampos.validarTelefono(usuarioCliente.getTelefono()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Teléfono o movil ingresado no es válido",
					"télefono inválido");
		}
		UsuarioCliente nuevoUsuarioCliente = null;
		// si se guarda correctamente el usuariocliente 
		try {
			nuevoUsuarioCliente = usuarioClienteService.crearUsuarioCliente(usuarioCliente);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar un nuevo usuario",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Usuario guardado con éxito", nuevoUsuarioCliente);
	}

	// actualizamos los datos del usuariocliente nuevo
	@PutMapping("/usucli/act/{id}")
	public ResponseEntity<?> actualizarUsuarioCliente(@Valid @RequestBody UsuarioCliente usuarioCliente,
			BindingResult resultado, @PathVariable Long id) {
		if (resultado.hasErrors()) {
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}
		// validaciones
		// validación: Email
		if (validacionesCampos.validarEmail(usuarioCliente.getUsuario()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Email del usuario ingresado no es válido",
					"Email del usuario inválido");
		}
		// validación: Telefono
		if (validacionesCampos.validarTelefono(usuarioCliente.getTelefono()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Teléfono o movil ingresado no es válido",
					"télefono inválido");
		}
		// verificamos que exista el usuario a actualizar
		UsuarioCliente usuarioClilenteActual = usuarioClienteService
				.buscarUsuarioClienteId(id);
		UsuarioCliente usuarioClienteActualizado = null;
		if (usuarioClilenteActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe ese usuario en la Base de Datos",
					"Id invalido");
		}
		// verificamos si se actualiza el usuarioCliente
		try {
			usuarioClilenteActual.setNombre(usuarioCliente.getNombre());
			usuarioClilenteActual.setUsuario(usuarioCliente.getUsuario());
			usuarioClilenteActual.setTelefono(usuarioCliente.getTelefono());
			usuarioClilenteActual.setClave(usuarioCliente.getClave());
			usuarioClienteActualizado = usuarioClienteService.crearUsuarioCliente(usuarioClilenteActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el Usuario en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Usuario actualizado con éxito", usuarioClienteActualizado);
	}
	// eliminamos usuario cliente
	@DeleteMapping("usucli/borrar/{id}")
	public ResponseEntity<?> borrarUsuarioCliente(@PathVariable Long id) {
		try {
			UsuarioCliente usuarioCliente = usuarioClienteService.buscarUsuarioClienteId(id);
			usuarioClienteService.eliminarUsuarioCliente(usuarioCliente.getId());
		}catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al eliminar el Usuario en la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Usuario borrado con éxito", "borrado"); 
	}
}
