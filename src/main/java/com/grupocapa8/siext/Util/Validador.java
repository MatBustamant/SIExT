/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.Util;

/**
 *
 * @author Matias
 */
public class Validador {

    private Validador() {}
    
    public static void validarId(Integer id, String campo) {
        if (id == null) {
            throw new IllegalArgumentException(
                    String.format("El campo %s no puede ser nulo.", campo)
            );
        }
        if (id <= 0) {
            throw new IllegalArgumentException(
                    String.format("El campo %s debe ser un número entero positivo.", campo)
            );
        }
    }
    
    public static void validarString(String valor, String campo, int min, int max) {
        if (valor == null) {
            throw new IllegalArgumentException(
            String.format("El campo %s no puede estar vacío.", campo)
            );
        }
        if (valor.trim().length() < min || valor.trim().length() > max) {
            throw new IllegalArgumentException(
            String.format("El campo %s debe tener entre %d y %d caracteres.", campo, min, max)
            );
        }
    }
    
    public static void validarContraseña(String contraseña){
        // 0. Campo no nulo
        if (contraseña==null) {
            throw new IllegalArgumentException("El campo contraseña no puede ser nulo.");
        }
        // 1. Longitud mínima y máxima
        if (contraseña.length() < 8 || contraseña.length() > 64) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 64 caracteres.");
        }
        // 2. Al menos una mayúscula
        if (!contraseña.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula.");
        }

        // 3. Al menos una minúscula
        if (!contraseña.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra minúscula.");
        }

        // 4. Al menos un número
        if (!contraseña.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un número.");
        }

        // 5. Al menos un signo o símbolo
        if (!contraseña.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("La contraseña debe contener al menos un carácter especial.");
        }  
    }  
    
}
