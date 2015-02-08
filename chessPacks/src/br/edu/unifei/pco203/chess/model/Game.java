/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author adriano
 */
@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar startDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar pauseDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar endDate;
    private boolean whiteTurn = true;
    @OneToOne
    private Board board;
    @OneToOne
    private Player white;
    @OneToOne
    private Player black;
    @ManyToOne
    private Player winner;

    public Game() {

    }

    public Game(Player white, Player black) {
        this.white = white;
        this.black = black;
    }

    public void getStarted() {
        if (startDate != null) {
            return;
        }
        white.setLastGame(this);
        black.setLastGame(this);
        board = new Board();
        board.setup();
        startDate = new GregorianCalendar();
    }

    public void move(Piece piece, char desiredRank, char desiredFile) throws GameException {
        Movement movement = new Movement(desiredRank, desiredFile, piece, getPlayerTurn(), this);
        move(movement);
    }
    
    public void move(Movement movement) throws GameException {
        Piece piece = movement.getPiece();
        if (piece.isWhiteSet() != whiteTurn) {
            throw new GameException("It is " + getPlayerTurn().getName() + "'s turn!!!");
        }
        movement.move();
        getSetTurn().getMovements().add(movement);
        toggle();
    }
    
    public Player getPlayerTurn() {
        if (whiteTurn) {
            return white;
        } else {
            return black;
        }
    }
    
    public SetOfPieces getSetTurn() {
        if (whiteTurn) {
            return board.getWhiteSet();
        } else {
            return board.getBlackSet();
        }
    }

    private void toggle() {
        whiteTurn = !whiteTurn;
    }

    public void checkMate() {
        if (endDate != null) {
            return;
        }
        pauseDate = new GregorianCalendar();
        endDate = new GregorianCalendar();
        winner = getPlayerTurn();
    }
    
    public void pause() {
        pauseDate = new GregorianCalendar();
    }

    private void draw() {
        winner = null;
    }

    public boolean isFinished() {
        return endDate != null;
    }

    public boolean isPaused() {
        return pauseDate != null;
    }

    public int compareTo(Game anotherGame) {
        if (pauseDate != null) {
            return pauseDate.compareTo(anotherGame.getPauseDate());
        } else {
            return startDate.compareTo(anotherGame.getStartDate());
        }
    }

    public boolean hasPlayed(Player player) {
        return white.equals(player) || black.equals(player);
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String string = "Game: \n"
                + "   Start Date: ";
        if (startDate != null) {
            string += fmt.format(startDate.getTime()) + "\n";
        } else {
            string += "---\n";
        }
        string += "   End Date: ";
        if (endDate != null) {
            string += fmt.format(endDate.getTime()) + "\n";
        } else {
            string += "---\n";
        }
        string += "   White Player: " + white + "\n"
                + "   Black Player: " + black + "\n"
                + "   Winner Player Name: ";
        if (winner != null) {
            string += winner.getName() + "\n";
        } else {
            string += "---\n";
        }
        string += "   " + board;
        return string;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(Calendar pauseDate) {
        this.pauseDate = pauseDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

}
