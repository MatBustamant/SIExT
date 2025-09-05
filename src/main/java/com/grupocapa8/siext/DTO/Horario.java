package com.grupocapa8.siext.DTO;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Horario {
    private LocalTime hora;

    // Constructor a partir de hora, minuto y segundo
    public Horario(int horas, int minutos, int segundos) {
        this.hora = LocalTime.of(horas, minutos, segundos);
    }

    // Constructor a partir de String con formato HH:mm:ss
    public Horario(String horaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            this.hora = LocalTime.parse(horaStr, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm:ss (ej: 14:30:00).");
        }
    }

    // Obtener como LocalTime
    public LocalTime getHora() {
        return hora;
    }

    // Obtener como String
    public String getHoraFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return hora.format(formatter);
    }

    // Comparar horarios
    public boolean esAnteriorA(Horario otro) {
        return this.hora.isBefore(otro.getHora());
    }

    public boolean esPosteriorA(Horario otro) {
        return this.hora.isAfter(otro.getHora());
    }

    // Sumar minutos
    public Horario agregarMinutos(int minutos) {
        return new Horario(this.hora.plusMinutes(minutos).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    // Restar minutos
    public Horario restarMinutos(int minutos) {
        return new Horario(this.hora.minusMinutes(minutos).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    // Validar que esté dentro de un rango
    public boolean estaEnRango(Horario inicio, Horario fin) {
        return (hora.equals(inicio.getHora()) || hora.isAfter(inicio.getHora())) &&
               (hora.equals(fin.getHora()) || hora.isBefore(fin.getHora()));
    }

    @Override
    public String toString() {
        return getHoraFormateada();
    }

    // Método de prueba
    public static void main(String[] args) {
        Horario h1 = new Horario("14:30:00");
        Horario h2 = new Horario(16, 0, 0);

        System.out.println("Horario 1: " + h1);
        System.out.println("Horario 2: " + h2);

        System.out.println("¿h1 es anterior a h2? " + h1.esAnteriorA(h2));

        Horario h3 = h1.agregarMinutos(45);
        System.out.println("h1 + 45 min: " + h3);

        Horario h4 = h2.restarMinutos(30);
        System.out.println("h2 - 30 min: " + h4);

        System.out.println("¿14:30 está entre 14:00 y 18:00? " +
                h1.estaEnRango(new Horario("14:00:00"), new Horario("18:00:00")));
    }
}
