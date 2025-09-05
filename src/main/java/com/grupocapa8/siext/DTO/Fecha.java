/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupocapa8.siext.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Fecha {
    private LocalDate fecha;

    // Constructor a partir de año, mes y día
    public Fecha(int anio, int mes, int dia) {
        this.fecha = LocalDate.of(anio, mes, dia);
    }

    // Constructor a partir de un String con formato dd/MM/yyyy
    public Fecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            this.fecha = LocalDate.parse(fechaStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/MM/yyyy.");
        }
    }

    // Obtener la fecha como objeto LocalDate
    public LocalDate getFecha() {
        return fecha;
    }

    // Obtener la fecha como String
    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }

    // Comparar con otra fecha
    public boolean esAnteriorA(Fecha otra) {
        return this.fecha.isBefore(otra.getFecha());
    }

    public boolean esPosteriorA(Fecha otra) {
        return this.fecha.isAfter(otra.getFecha());
    }

    // Validar si es año bisiesto
    public boolean esBisiesto() {
        return fecha.isLeapYear();
    }

    // Sumar días
    public Fecha agregarDias(int dias) {
        return new Fecha(this.fecha.plusDays(dias).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    // Restar días
    public Fecha restarDias(int dias) {
        return new Fecha(this.fecha.minusDays(dias).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Override
    public String toString() {
        return getFechaFormateada();
    }

    // Método de prueba
    public static void main(String[] args) {
        Fecha f1 = new Fecha("05/09/2025");
        Fecha f2 = new Fecha(2024, 12, 31);

        System.out.println("Fecha 1: " + f1);
        System.out.println("Fecha 2: " + f2);

        System.out.println("¿f1 es anterior a f2? " + f1.esAnteriorA(f2));
        System.out.println("¿f2 es bisiesto? " + f2.esBisiesto());

        Fecha f3 = f1.agregarDias(10);
        System.out.println("f1 + 10 días: " + f3);

        Fecha f4 = f1.restarDias(7);
        System.out.println("f1 - 7 días: " + f4);
    }
}
