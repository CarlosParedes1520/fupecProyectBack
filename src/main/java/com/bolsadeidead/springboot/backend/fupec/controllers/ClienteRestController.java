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
import com.bolsadeidead.springboot.backend.fupec.models.UserAuth;
import com.bolsadeidead.springboot.backend.fupec.models.UsuarioCliente;
import com.bolsadeidead.springboot.backend.fupec.models.Vendedor;
import com.bolsadeidead.springboot.backend.fupec.services.ClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.TarjetaService;
import com.bolsadeidead.springboot.backend.fupec.services.UserService;
import com.bolsadeidead.springboot.backend.fupec.services.UsuarioClienteService;
import com.bolsadeidead.springboot.backend.fupec.services.VendedorService;
import com.bolsadeidead.springboot.backend.fupec.validaciones.ListaErrores;
import com.bolsadeidead.springboot.backend.fupec.validaciones.RespuestaAccion;
import com.bolsadeidead.springboot.backend.fupec.validaciones.Validaciones;

/*
 * Controlador para el crud de cliente
 * Configuracion de endpoints para el api HTTP
 * 
 * */

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	// instanciamos clases
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private UsuarioClienteService usuarioClienteService;
	@Autowired
	private VendedorService vendedorService;
	@Autowired
	private TarjetaService tarjetaService;
	@Autowired
	private UserService userService;
	@Autowired
	private RespuestaAccion respuestaAccion;
	@Autowired
	private ListaErrores listaErrores;
	@Autowired
	private Validaciones validacionesCampos;

	// listar clientes paginador
	@GetMapping("/cliente/lista/{page}")
	public Page<Cliente> indicePagina(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		return clienteService.obtenerClientesPage(pageable);
	}

	// paginador de clientes activos
	@GetMapping("/cliente/seguimiento/{page}")
	public Page<Cliente> indiceSeguimientos(@PathVariable Integer page) {

		Pageable pageable = PageRequest.of(page, 10);
		return clienteService.obtenerClientesSegumientos(pageable);
	}

	// buscar cliente por id
	@GetMapping("/cliente/buscar/{id}")
	public ResponseEntity<?> buscarClientePorId(@PathVariable Long id) {
		if (id == null || id <= 0) {
			return respuestaAccion.errorBD(false, "No existe ese cliente", "id invalido");
		}
		Cliente cliente = clienteService.buscarIdCliente(id);
		if (cliente == null) {
			return respuestaAccion.errorBD(false, "No existe ese cliente", "id invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos del cliente", cliente);
	}

	// lista clientes por nombre filtrado
	@GetMapping("/cliente/lista/nombre/{termino}")
	public ResponseEntity<?> listarClientesPorNombre(@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		List<Cliente> clientes = clienteService.listarClientesPorNombre(termino);
		if (clientes.size() == 0) {
			return respuestaAccion.errorBD(false, "No existe clientes con ese termino", "termino invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos clientes", clientes);
	}

	// lista clientes por cedula filtrado
	@GetMapping("/cliente/lista/cedula/{termino}")
	public ResponseEntity<?> listarClientesPorCedula(@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		List<Cliente> clientes = clienteService.listarClientesPorCedula(termino);
		if (clientes.size() == 0) {
			return respuestaAccion.errorBD(false, "No existe clientes con ese termino", "termino invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos clientes", clientes);
	}

	// paginable seguiminetos de clientes por nombre filtrado
	@GetMapping("/cliente/seguimiento/nombre/{page}/{termino}")
	public ResponseEntity<?> buscarSeguimientoClientePorNombre(@PathVariable Integer page,
			@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Cliente> listaSeguiminetosClientes = clienteService.obtenerClientesSegumientosPorNombre(pageable, termino);
		if (listaSeguiminetosClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No existe seguimientos con ese cliente", "termino invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos clientes", listaSeguiminetosClientes);
	}

	// paginable seguiminetos de clientes por cedula filtrado
	@GetMapping("/cliente/seguimiento/cedula/{page}/{termino}")
	public ResponseEntity<?> buscarSeguimientoClientePorCedula(@PathVariable Integer page,
			@PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Cliente> listaSeguiminetosClientes = clienteService.obtenerClientesSegumientosPorCedula(pageable, termino);
		if (listaSeguiminetosClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No existe clientes con ese termino", "termino invalido");
		}
		return respuestaAccion.accionCumplida(true, "Datos clientes", listaSeguiminetosClientes);
	}

	// lista clientes filtrando nombre
	@GetMapping("/cliente/nombre/{page}/{termino}")
	public ResponseEntity<?> filtrarClientesNombre(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Cliente> listaClientes = clienteService.filtrarNombreUsuarioClientes(pageable, termino);
		if (listaClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen clientes con ese termino");
		}
		return respuestaAccion.accionCumplida(true, "Lista de clientes", listaClientes);
	}

	// lista clientes filtrando usuario email
	@GetMapping("/cliente/email/{page}/{termino}")
	public ResponseEntity<?> filtrarClientesUsuario(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Cliente> listaClientes = clienteService.filtrarNombreEmailClientes(pageable, termino);
		if (listaClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "no existen clientes con ese termino");
		}
		return respuestaAccion.accionCumplida(true, "Lista de clientes", listaClientes);
	}

	// lista de clientes fitrando cedula
	@GetMapping("/cliente/cedula/{page}/{termino}")
	public ResponseEntity<?> filtrarVendedoremail(@PathVariable Integer page, @PathVariable String termino) {
		if (termino.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "termino de busqueda vacío");
		}
		Pageable pageable = PageRequest.of(page, 10);
		Page<Cliente> listaClientes = clienteService.filtrarClienteCedula(pageable, termino);
		if (listaClientes.isEmpty()) {
			return respuestaAccion.errorBD(false, "No hay datos", "No existen clientes con esa cedula");
		}
		return respuestaAccion.accionCumplida(true, "Lista de clientes", listaClientes);
	}

	// guardar datos del nuevo clinete
	@PostMapping("/cliente/crear")
	public ResponseEntity<?> crearCliente(@Valid @RequestBody Cliente cliente, BindingResult resultado) {
		return accionVerificarDatosCliente(cliente, resultado, Long.parseLong("0"));
	}

	// actualizar datos del cliente
	@PutMapping("/cliente/act/{id}")
	public ResponseEntity<?> actualizarClienteId(@Valid @RequestBody Cliente cliente, BindingResult resultado,
			@PathVariable Long id) {
		return accionVerificarDatosCliente(cliente, resultado, id);
	}

	// metodo para verificar campos vacios
	public ResponseEntity<?> accionVerificarDatosCliente(Cliente cliente, BindingResult resultado, Long id) {
		if (resultado.hasErrors()) { // verificamos si existen errores en los campos
			return new ResponseEntity<Map<String, Object>>(listaErrores.enviarErrorFrontEnd(resultado),
					HttpStatus.BAD_REQUEST);
		}

		if (validacionesCampos.validarCedula(cliente.getCedula()) == false) {
			return respuestaAccion.respuestaValidacion(false, "Cédula ingresada no es válida", "cédula inválida");
		}
		// buscamos usuario en tabla_usuarios y verificamos si existe
		UsuarioCliente usuarioCliente = usuarioClienteService.buscarUsuarioClienteId(cliente.getUsuario().getId());
		if (usuarioCliente == null) {
			return respuestaAccion.listaDatosVacia(false, "No hay datos de ese usuario", "usuario inexistente");
		}
		// buscamos vendedor y verificamos si existe

		Vendedor vendedor = vendedorService.buscarIdVendedor(cliente.getVendedor().getId());
		if (vendedor == null) {
			return respuestaAccion.listaDatosVacia(false, "No hay datos de ese vendedor", "vendedor inexistente");
		}
		// buscamos tarjeta y verificamos si existe
		Tarjeta tarjeta = tarjetaService.buscarIdTarjeta(cliente.getTarjeta().getId());
		if (tarjeta == null) {
			return respuestaAccion.listaDatosVacia(false, "No hay datos de esa Tarjeta", "tarjeta inexistente");
		}
		cliente.setUsuario(usuarioCliente);
		cliente.setVendedor(vendedor);
		cliente.setTarjeta(tarjeta);
		// para ver si se actualiza o se gurda un cliente
		if (id == 0) {
			return guardarCliente(cliente);
		} else {
			return actualizarCliente(cliente, id);
		}
	}

	// metodo para validar y guardar el cliente
	public ResponseEntity<?> guardarCliente(Cliente cliente) {
		Cliente clienteVerificar = clienteService.buscarCedulaCliente(cliente.getCedula());
		// validamos si hay otra cedula
		if (clienteVerificar != null) {
			return respuestaAccion.errorBD(false, "Ya existe un cliente con esa Cédula", "cédula duplicada");
		}
		clienteVerificar = clienteService.buscarTarjetaCliente(cliente.getTarjeta());
		if (clienteVerificar != null) { // validaciones de la relación solo debe haber una tarjeta
			return respuestaAccion.errorBD(false, "Ya existe un cliente con esa tarjeta", "Tarjeta duplicada");
		}
		clienteVerificar = clienteService.buscarUsuarioCliente(cliente.getUsuario());
		if (clienteVerificar != null) { // solo debe de haber un solo cliente con usuario
			return respuestaAccion.errorBD(false, "Ya existe un cliente con ese usuario", "Usuario duplicada");
		}
		// para pasar los datos del usuario que creo el cliente
		UserAuth user = userService.findById(cliente.getUser().getId());
		Cliente nuevoCliente = null;
		// si se guarda correctamente el cliente
		try {
			cliente.setUser(user);
			cliente.persisteciaFechaGradabo();
			nuevoCliente = clienteService.crearCliente(cliente);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al guardar un nuevo cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Cliente guardado con éxito", nuevoCliente);
	}

	// metodo para validar y actualizar el cliente
	public ResponseEntity<?> actualizarCliente(Cliente cliente, Long id) {
		// verificamos que el cliente exista
		Cliente clienteActual = clienteService.buscarIdCliente(id);
		if (clienteActual == null) {
			return respuestaAccion.listaDatosVacia(false, "No existe ese cliente en la Base de Datos", "id inválido");
		}
		Cliente clienteVerificar = clienteService.buscarCedulaCliente(cliente.getCedula());
		// validamos si hay otra cedula
		if (clienteVerificar != null && clienteVerificar.getId() != id) {
			return respuestaAccion.errorBD(false, "Ya existe un cliente con esa Cédula", "cédula duplicada");
		}
		clienteVerificar = clienteService.buscarTarjetaCliente(cliente.getTarjeta());
		if (clienteVerificar != null && clienteVerificar.getId() != id) {// validaciones de la relación solo debe haber
																			// una tarjeta
			return respuestaAccion.errorBD(false, "Ya existe un cliente con esa tarjeta", "Tarjeta duplicada");
		}
		clienteVerificar = clienteService.buscarUsuarioCliente(cliente.getUsuario());
		if (clienteVerificar != null && clienteVerificar.getId() != id) { // solo debe de haber un solo cliente con
																			// usuario
			return respuestaAccion.errorBD(false, "Ya existe un cliente con ese usuario", "Usuario duplicada");
		}
		// si se actualiza correctamente el cliente
		try {
			clienteActual.setCedula(cliente.getCedula());
			clienteActual.setPuntos(cliente.getPuntos());
			clienteActual.setGenero(cliente.getGenero());
			clienteActual.setOcupacion(cliente.getOcupacion());
			clienteActual.setEliminado(cliente.getEliminado());
			clienteActual.setTipoSangre(cliente.getTipoSangre());
			clienteActual.setUsuario(cliente.getUsuario());
			clienteActual.setVendedor(cliente.getVendedor());
			clienteActual.setTarjeta(cliente.getTarjeta());
			clienteActual.setIntereses(cliente.getIntereses());
			clienteService.crearCliente(clienteActual);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al actualizar el cliente",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		}
		return respuestaAccion.accionCumplida(true, "Cliente actualizado con éxito", clienteActual);
	}

	// borrar cliente, poner eliminado en false
	@DeleteMapping("/cliente/borrar/{id}")
	public ResponseEntity<?> borrarCliente(@PathVariable Long id) {
		Cliente cliente = clienteService.buscarIdCliente(id);
		if (cliente == null) {
			return respuestaAccion.errorBD(false, "No existe ese cliente", "id inválido");
		}
		try {
			cliente.setEliminado(false);
			clienteService.crearCliente(cliente);
		} catch (DataAccessException e) {
			return respuestaAccion.errorBD(false, "Error al borrar el cliente de la base de datos",
					e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

		}
		return respuestaAccion.accionCumplida(true, "Cliente borrado con éxito", "borrado");
	}
}
