package com.bolsadeidead.springboot.backend.fupec.validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

/*
 * Servicios para validacion del telefono y cedula 
 * 
 * */

@Service
public class Validaciones {
	// validamos emial
	public Boolean validarEmail(String email) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(email);
		if (mather.find() != true) {
			return false; // si es falso si contiene
		}
		return true;
	}

	// <------------ semivalidacion de numero de telefono ----------------->
	public Boolean validarTelefono(String telefono) {
		if (telefono.length() == 10 && campoNumerico(telefono) && telefono.substring(0, 2).equals("09")) {
			return true;
		}
		if (telefono.length() == 7 && campoNumerico(telefono) && !telefono.substring(0, 1).equals("0")) {
			return true;
		}
		if (telefono.length() == 9 && campoNumerico(telefono) && verificarCodigoProvincia(telefono.substring(0, 2))) {
			return true;
		}
		return false;
	}

	// validamos si tiene codigo de provincia
	public Boolean verificarCodigoProvincia(String valor) {
		String[] codigos = { "02", "03", "04", "05", "06", "07" };
		for (int i = 0; i < codigos.length; i++) {
			if (codigos[i].equals(valor)) {
				return true;
			}
		}
		return false;
	}

	// validamos si es un numero
	public Boolean campoNumerico(String valor) {
		Pattern p = Pattern.compile("^[0-9]*$");
		Matcher val = p.matcher(valor);
		if (val.find() != true) {
			return false; // si es falso contiene caracteres especiales
		}
		return true;
	}

	// < ----------------------------- Validacion cedula ------------------------ >
	public Boolean validarCedula(String cedula) {
		// verificamos cedula 
		if (cedula.trim().length() != 10) return false;
		int codigoProvincia = Integer.parseInt(cedula.substring(0,2));
		if (codigoProvincia < 0 || codigoProvincia > 24 ) return false; // verificamos codigo provincia
		int tercerDigito = Integer.parseInt(cedula.substring(2,3));
		if (tercerDigito < 0 || tercerDigito > 5 ) return false;
		// validamos el ultimo digito
		return algoritmoModulo10(cedula.substring(0, 9), Integer.parseInt(String.valueOf(cedula.charAt(9))));
	}
	
	// algoritomo para validar el ultimo digito
	public boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador){
        Integer [] arrayCoeficientes = new Integer[]{2,1,2,1,2,1,2,1,2};
        Integer [] digitosInicialesTMP = new Integer[digitosIniciales.length()];
        int indice=0;
        for(char valorPosicion: digitosIniciales.toCharArray()){
        	digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
        	indice++;
        }
        int total = 0;
        int key = 0;
        for(Integer valorPosicion: digitosInicialesTMP){
        	if(key<arrayCoeficientes.length){
        		valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key] );
        		if (valorPosicion >= 10) {
                	char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
                    valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0]))) + (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));
                }
                total = total + valorPosicion;
        	}
            key++;
        }
        int residuo =  total % 10;
        int resultado ;

        if (residuo == 0) {
            resultado = 0;        
        } else {
            resultado = 10 - residuo;
        }

        if (resultado != digitoVerificador) {
           return false;
        }
        return true;
    }
}
