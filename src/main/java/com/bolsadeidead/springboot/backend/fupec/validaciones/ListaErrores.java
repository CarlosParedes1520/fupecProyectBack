package com.bolsadeidead.springboot.backend.fupec.validaciones;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/*
 * Servicios para validacion de los campos de un modelo
 * 
 * */

@Service
public class ListaErrores {
	// metodo par validar si los datos recibidos desde el fron son rechazados por la
	// bd
	public Map<String, Object> enviarErrorFrontEnd(BindingResult resultado) {
		Map<String, Object> response = new HashMap<>();
		List<String> errores = resultado.getFieldErrors().stream()
				.map(error -> "El campo '" + error.getField() + "' " + error.getDefaultMessage())
				.collect(Collectors.toList());
		response.put("ok", false);
		response.put("mensaje", "campos inválidos");
		response.put("errores", errores);
		return response;
	}
	

}
