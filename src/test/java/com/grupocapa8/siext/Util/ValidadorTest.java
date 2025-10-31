/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.grupocapa8.siext.Util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author Matias
 */
public class ValidadorTest {
    
    private static String campo;
    private static int min;
    private static int max;
    
    @BeforeAll
    public static void setUpClass() {
        campo = "";
        min = 3;
        max = 50;
    }

    /**
     * Testea que las Ids válidas funcionen bien.
     */
    @Test
    @DisplayName("Debería aceptar ids positivos")
    public void testIdValida() {
        assertDoesNotThrow(() -> {
            Validador.validarId(1, campo);
        });
    }
    
    /**
     * Testea que una Id nula tire la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el id es nulo")
    public void testIdNoDeberiaSerNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarId(null, campo);
        });
    }
    
    /**
     * Testea que una Id negativa tire la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el id es negativo")
    public void testIdNoDeberiaSerNegativa() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarId(-1, campo);
        });
    }
    
    /**
     * Testea que una Id igual a cero tire la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el id es cero")
    public void testIdNoDeberiaSerCero() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarId(0, campo);
        });
    }

    /**
     * Testea que los Strings válidos funcionen bien.
     */
    @Test
    @DisplayName("Debería aceptar Strings con longitud válida")
    public void testStringValido() {
        assertDoesNotThrow(() -> {
            Validador.validarString("abc", campo, min, max);
        });
        assertDoesNotThrow(() -> {
            Validador.validarString("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx", campo, min, max);
        });
    }
    
    /**
     * Testea que un String nulo tire la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el String es nulo")
    public void testStringNoDeberiaSerNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarString(null, campo, min, max);
        });
    }
    
    /**
     * Testea que los Strings demasiado cortos tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el String es demasiado corto")
    public void testStringNoDeberiaSerMenorQueMin() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarString("ab", campo, min, max);
        });
    }
    
    /**
     * Testea que los Strings demasiado largos tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si el String es demasiado largo")
    public void testStringNoDeberiaSerMayorQueMax() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarString("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy", campo, min, max);
        });
    }

    /**
     * Testea que las contraseñas válidas funcionen bien.
     */
    @Test
    @DisplayName("Debería aceptar contraseñas que cumplan con todos los requisitos")
    public void testContraseñaValida() {
        assertDoesNotThrow(() -> {
            Validador.validarContraseña("Ab1!Ab1!");
        });
        assertDoesNotThrow(() -> {
            Validador.validarContraseña("ibJBa$T4k#jsbAqeLDpXbp6eEf9@8oQA8Dnhsdj4CRN4&W!BANMSveyy5cPAp#G*");
        });
    }
    
    /**
     * Testea que una contraseña nula tire la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña es nula")
    public void testContraseñaNoDeberiaSerNula() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña(null);
        });
    }
    
    /**
     * Testea que las contraseñas demasiado cortas tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña es demasiado corta")
    public void testContraseñaNoDeberiaSerMenorQueMin() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("Hjkl!1!");
        });
    }
    
    /**
     * Testea que las contraseñas demasiado largas tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña es demasiado larga")
    public void testContraseñaNoDeberiaSerMayorQueMax() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("MmG#Kj6bz4Ga4fLmoavb&5Uhj5EovG3a%#r4kD^N5!KHs$TbEZWU#pe4BGXQoz7J2");
        });
    }
    
    /**
     * Testea que las contraseñas sin mayúsculas tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña no tiene mayúsculas")
    public void testContraseñaNoDeberiaSerSinMayus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("7*in*r3n");
        });
    }
    
    /**
     * Testea que las contraseñas sin minúsculas tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña no tiene minúsculas")
    public void testContraseñaNoDeberiaSerSinMinus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("NSX3*NU*");
        });
    }
    
    /**
     * Testea que las contraseñas sin números tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña no tiene números")
    public void testContraseñaNoDeberiaSerSinNums() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("qk@$FYNL");
        });
    }
    
    /**
     * Testea que las contraseñas sin símbolos tiren la excepción correspondiente.
     */
    @Test
    @DisplayName("Debería lanzar excepción si la contraseña no tiene símbolos")
    public void testContraseñaNoDeberiaSerSinSimbolos() {
        assertThrows(IllegalArgumentException.class, () -> {
            Validador.validarContraseña("wpkhA2cS");
        });
    }
    
}
