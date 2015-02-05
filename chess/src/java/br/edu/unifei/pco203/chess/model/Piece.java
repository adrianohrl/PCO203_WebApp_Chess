/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adriano
 */
@Entity
public abstract class Piece implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    private char rank; // denoted with numbers 1 to 8
    private char file; // denoted with letters a to h
    private boolean whiteSet;
    @ManyToOne
    private Board board;

    public Piece() {
        
    }
    
    public Piece(char rank, char file, boolean whiteSet, Board board) {
        this.rank = rank;
        this.file = file;
        this.whiteSet = whiteSet;
        this.board = board;
    }

    public void move(char rank, char file) throws GameException {
        if (!isValidMovement(rank, file)) {
            throw new GameException("Invalid Movement!!!");
        }
        this.rank = rank;
        this.file = file;
    }

    public abstract boolean isValidMovement(char desiredRank, char desiredFile);
    
    public void copyFrom(Piece piece) {
        this.rank = piece.rank;
        this.file = piece.file;
        this.whiteSet = piece.whiteSet;
        this.board = piece.board;
    }

    @Override
    public String toString() {
        return "@ " + Character.toUpperCase(this.file) + "" + this.rank;// + " on board: " + board;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public char getFile() {
        return file;
    }

    public void setFile(char file) {
        this.file = file;
    }

    public boolean isWhiteSet() {
        return whiteSet;
    }

    public void setWhiteSet(boolean whiteSet) {
        this.whiteSet = whiteSet;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
