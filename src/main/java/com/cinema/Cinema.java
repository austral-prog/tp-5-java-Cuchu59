package com.cinema;

import java.util.ArrayList;

/**
 * Clase que representa una sala de cine.
 */
public class Cinema {

    private Seat[][] seat_grid;

    /**
     * Construye una sala de cine. Se le pasa como dato un arreglo cuyo tamaño
     * es la cantidad de filas y los enteros que tiene son el número de butacas en cada fila.
     */
    public Cinema(int[] rows) {
        seat_grid = new Seat[rows.length][];
        initSeats(rows);
    }

    /**
     * Inicializa las butacas de la sala de cine.
     *
     * @param rows arreglo que contiene la cantidad de butacas en cada fila
     */
    private void initSeats(int[] rows) {
        for (int i = 0; i < rows.length; i++) {
            seat_grid[i] = new Seat[rows[i]];
        }
        for (int i = 0; i < seat_grid.length; i++) {
            for (int j = 0; j < seat_grid[i].length; j++) {
                seat_grid[i][j] = new Seat(i, j);
            }
        }
    }

    /**
     * Cuenta la cantidad de seats disponibles en el cine.
     */
    public int countAvailableSeats() {
        int free_seats = 0;
        for(int r = 0; r < this.seat_grid.length; r++){
            for(int c = 0; c < this.seat_grid[r].length; c++) {
                if(seat_grid[r][c].isAvailable()) { free_seats++; }
            }
        }
        return free_seats;
    }

    /**
     * Busca la primera butaca libre dentro de una fila o null si no encuentra.
     */
    public Seat findFirstAvailableSeatInRow(int row) {
        if(row < seat_grid.length) {
            for (Seat current_seat : seat_grid[row]) {
                if(current_seat.isAvailable()) {return current_seat;}
            }
        }
        return null;
    }

    /**
     * Busca la primera butaca libre o null si no encuentra.
     */
    public Seat findFirstAvailableSeat() {
        for (int r = 0; r < seat_grid.length; r++){
            if(findFirstAvailableSeatInRow(r) != null){ return findFirstAvailableSeatInRow(r); }
        }
        return null;
    }

    /**
     * Busca las N butacas libres consecutivas en una fila. Si no hay, retorna null.
     *
     * @param row    fila en la que buscará las butacas.
     * @param amount el número de butacas necesarias (N).
     * @return La primer butaca de la serie de N butacas, si no hay retorna null.
     */
    public Seat getAvailableSeatsInRow(int row, int amount) {
        
        int consec_seats = 0;
        int seat_count = 0;
        int first_index = 0;

        for(int s = 0; s < this.seat_grid[row].length; s++) {
            
            if(seat_grid[row][s].isAvailable()) {
                seat_count++;
            } else {
                seat_count = 0;
            }
            
            consec_seats = seat_count > consec_seats ? seat_count : consec_seats;
            
            if(consec_seats == amount) { 
                first_index = (s+1) - consec_seats;  
                Seat free_seat = new Seat(row, first_index);
                return free_seat;
            }
        }
        return null;
        
    }

    /**
     * Busca en toda la sala N butacas libres consecutivas. Si las encuentra
     * retorna la primer butaca de la serie, si no retorna null.
     *
     * @param amount el número de butacas pedidas.
     */
    public Seat getAvailableSeats(int amount) {
        for(int r = 0; r < seat_grid.length; r ++) {
            if(getAvailableSeatsInRow(r, amount) != null){ return getAvailableSeatsInRow(r, amount);}
        }
        return null;
    }

    /**
     * Marca como ocupadas la cantidad de butacas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a reservar.
     */
    public void takeSeats(Seat seat, int amount) {
        for(int s = seat.getSeatNumber(); s < seat.getSeatNumber()+amount; s++) {
            seat_grid[seat.getRow()][s].takeSeat();
        }
    }

    /**
     * Libera la cantidad de butacas consecutivas empezando por la que se le pasa.
     *
     * @param seat   butaca inicial de la serie.
     * @param amount la cantidad de butacas a liberar.
     */
    public void releaseSeats(Seat seat, int amount) {
        for(int s = seat.getSeatNumber(); s < seat.getSeatNumber()+amount; s++) {
            seat_grid[seat.getRow()][s].releaseSeat();
        }
    }
}
