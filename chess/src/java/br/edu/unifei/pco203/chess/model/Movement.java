/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author adriano
 */
@Entity
public class Movement implements Serializable {
    
    @Id
    @GeneratedValue
    private int code;
    private char currentRank;
    private char currentFile;
    private char nextRank;
    private char nextFile;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar currentDate = new GregorianCalendar();
    @ManyToOne
    private Piece piece;
    @ManyToOne
    private Player player;
    @ManyToOne 
    private Game game;

    public Movement() {
    }
    
    public Movement(char desiredRank, char desiredFile, Piece piece, Player player, Game game) {
        this.currentRank = piece.getRank();
        this.currentFile = piece.getFile();
        this.nextRank = desiredRank;
        this.nextFile = desiredFile;
        this.piece = piece;
        this.player = player;
        this.game = game;
    }
    
    public boolean isValid() {
        return piece.isValidMovement(nextRank, nextFile);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public char getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(char currentRank) {
        this.currentRank = currentRank;
    }

    public char getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(char currentFile) {
        this.currentFile = currentFile;
    }

    public char getNextRank() {
        return nextRank;
    }

    public void setNextRank(char nextRank) {
        this.nextRank = nextRank;
    }

    public char getNextFile() {
        return nextFile;
    }

    public void setNextFile(char nextFile) {
        this.nextFile = nextFile;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
}
