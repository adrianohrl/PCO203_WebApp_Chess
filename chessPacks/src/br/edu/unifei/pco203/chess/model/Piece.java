/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.Calendar;
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
public abstract class Piece implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    private char rank; // denoted with numbers 1 to 8
    private char file; // denoted with letters a to h
    private boolean whiteSet;
    @ManyToOne
    private Board board;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar promotionDate;

    public Piece() {

    }

    public Piece(char rank, char file, boolean whiteSet, Board board) throws GameException {
        rank = Character.toLowerCase(rank);
        file = Character.toLowerCase(file);
        if (rank < '1' || rank > '8') {
            throw new GameException("Invalid rank: " + rank + "!!!");
        }
        if (file < 'a' || file > 'h') {
            throw new GameException("Invalid file: " + file + "!!!");
        }
        this.rank = rank;
        this.file = file;
        this.whiteSet = whiteSet;
        this.board = board;
    }

    public void move(char rank, char file) throws GameException {
        rank = Character.toLowerCase(rank);
        file = Character.toLowerCase(file);
        if (rank < '1' || rank > '8') {
            throw new GameException("Invalid rank: " + rank + "!!!");
        }
        if (file < 'a' || file > 'h') {
            throw new GameException("Invalid file: " + file + "!!!");
        }
        this.rank = rank;
        this.file = file;
    }

    public boolean isValidMovement(char desiredRank, char desiredFile) {
        if (board.isThereAnyColleaguePieceAt(desiredRank, desiredFile, this)) {
            return false;
        }
        int vDisplacement = desiredRank - rank;
        int hDisplacement = desiredFile - file;
        char auxRank = rank;
        char auxFile = file;
        int rDisplacement = Math.max(Math.abs(vDisplacement), Math.abs(hDisplacement));
        for (int counter = 1; counter < rDisplacement; counter++) {
            auxRank += Integer.signum(vDisplacement);
            auxFile += Integer.signum(hDisplacement);
            if (board.isThereAnyPieceAt(auxRank, auxFile)) {
                return false;
            }
        }
        return true;
    }

    protected static boolean willPutMyKingInCheck(char desiredRank, char desiredFile, Piece piece) {
        try {
            Board board = piece.getBoard().clone();
            SetOfPieces mySet = board.getMySet(piece);
            Piece clonnedPiece = mySet.getPiece(piece.getRank(), piece.getFile());
            King myKing = board.getKing(mySet);
            Movement movement = new Movement(desiredRank, desiredFile, clonnedPiece);
            movement.moveInFuture();
            return myKing.isInCheck();
        } catch (GameException e) {
            return false;
        }
    }

    public void copyFrom(Piece piece) {
        this.rank = piece.rank;
        this.file = piece.file;
        this.whiteSet = piece.whiteSet;
        this.board = piece.board;
        this.promotionDate = piece.promotionDate;
    }

    public boolean isPromoted() {
        return promotionDate != null;
    }

    @Override
    public String toString() {
        return "@ " + Character.toUpperCase(this.file) + "" + this.rank;// + " on board: " + board;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece && this.equals((Piece) obj);
    }

    public boolean equals(Piece piece) {
        return piece != null && code == piece.code && rank == piece.rank && file == piece.file && whiteSet == piece.whiteSet;
    }

    @Override
    protected abstract Piece clone();

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

    public Calendar getPromotionDate() {
        return promotionDate;
    }

    public void setPromotionDate(Calendar promotionDate) {
        this.promotionDate = promotionDate;
    }

}
