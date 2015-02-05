/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author adriano
 */
@Entity
public class SetOfPieces implements Serializable {
    
    @Id
    @GeneratedValue
    private int code;
    private boolean whiteSet;
    @OneToMany
    private List<Piece> pieces = new ArrayList<>();
    @OneToMany
    private List<Movement> movements = new ArrayList<>();
    @ManyToOne
    private Board board;
    
    public SetOfPieces() {
        
    }
    
    private SetOfPieces(Board board) {
        this.board = board;
    }
    
    public static SetOfPieces getWhiteSet(Board board) {
        SetOfPieces whiteSet = new SetOfPieces(board);
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn('2', (char) ('a' + i), true, board));
        }
        pieces.add(new Rook('1', 'a', true, board));
        pieces.add(new Knight('1', 'b', true, board));
        pieces.add(new Bishop('1', 'c', true, board));
        pieces.add(new Queen('1', 'd', true, board));
        pieces.add(new King('1', 'e', true, board));
        pieces.add(new Bishop('1', 'f', true, board));
        pieces.add(new Knight('1', 'g', true, board));
        pieces.add(new Rook('1', 'h', true, board));
        whiteSet.setPieces(pieces);
        whiteSet.setWhiteSet(true);
        return whiteSet;
    }
    
    public static SetOfPieces getBlackSet(Board board) {
        SetOfPieces blackSet = new SetOfPieces(board);
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pieces.add(new Pawn('7', (char) ('a' + i), false, board));
        }
        pieces.add(new Rook('8','a',  false, board));
        pieces.add(new Knight('8', 'b', false, board));
        pieces.add(new Bishop('8', 'c', false, board));
        pieces.add(new Queen('8', 'd', false, board));
        pieces.add(new King('8', 'e', false, board));
        pieces.add(new Bishop('8', 'f', false, board));
        pieces.add(new Knight('8', 'g', false, board));
        pieces.add(new Rook('8', 'h', false, board));
        blackSet.setPieces(pieces);
        blackSet.setWhiteSet(false);
        return blackSet;
    }
    
    public void promotePawn(char startFile, Piece promotedPiece) {
        Piece pawn = this.getPawn(startFile);
        int index = this.getPieceIndex(pawn);
        promotedPiece.copyFrom(pawn);
        pieces.set(index, promotedPiece);        
    }
    
    private int getPieceIndex(Piece piece) {
        int index = 0;
        for (Piece p : pieces) {
            if (p.getRank() == piece.getRank() && p.getFile() == piece.getFile()) {
                return index;
            }
            ++index;
        }
        return index;
    }
    
    @Override
    public String toString() {
        String string = "\t";
        if (this.isWhiteSet()) {
            string += "White Set: \n";
        }
        else {
            string += "Black Set: \n";
        }
        for (Piece piece : pieces) {
            string += "\t\t" + piece + "\n";
        }
        return string;
    }
    
    public Pawn getPawn(char startFile) throws GameException {
        Pawn pawn = null;
        startFile = Character.toLowerCase(startFile);
        if (startFile < 'a' || startFile > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(startFile) + "!!!");
        }
        int index = startFile - 'a';
        if (pieces.get(index) instanceof Pawn) {
            pawn = (Pawn) pieces.get(index);
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return pawn;
    }
    
    public Rook getRook(char startFile) throws GameException {
        Rook rook = null;
        startFile = Character.toLowerCase(startFile);
        if (startFile < 'a' || startFile > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(startFile) + "!!!");
        }
        int index = 8 + startFile - 'a';
        if ((startFile == 'a' || startFile == 'h') && pieces.get(index) instanceof Rook) {
            rook = (Rook) pieces.get(index);
        }
        else if (startFile != 'a' && startFile == 'h') {
            throw new GameException("There is not a rook starting at " + Character.toUpperCase(startFile) + " file!!!");
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return rook;
    }
    
    public Knight getKnight(char startFile) throws GameException {
        Knight knight = null;
        startFile = Character.toLowerCase(startFile);
        if (startFile < 'a' || startFile > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(startFile) + "!!!");
        }
        int index = 8 + startFile - 'a';
        if ((startFile == 'b' || startFile == 'g') && pieces.get(index) instanceof Knight) {
            knight = (Knight) pieces.get(index);
        }
        else if (startFile != 'b' && startFile == 'g') {
            throw new GameException("There is not a knight starting at " + Character.toUpperCase(startFile) + " file!!!");
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return knight;
    }
    
    public Bishop getBishop(char startFile) throws GameException {
        Bishop bishop = null;
        startFile = Character.toLowerCase(startFile);
        if (startFile < 'a' || startFile > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(startFile) + "!!!");
        }
        int index = 8 + startFile - 'a';
        if ((startFile == 'c' || startFile == 'f') && pieces.get(index) instanceof Bishop) {
            bishop = (Bishop) pieces.get(index);
        }
        else if (startFile != 'c' && startFile == 'f') {
            throw new GameException("There is not a bishop starting at " + Character.toUpperCase(startFile) + " file!!!");
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return bishop;
    }
    
    public Queen getQueen() throws GameException {
        Queen queen = null;
        if (pieces.get(11) instanceof Queen) {
            queen = (Queen) pieces.get(11);
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return queen;
    }
    
    public King getKing() throws GameException {
        King king = null;
        if (pieces.get(12) instanceof King) {
            king = (King) pieces.get(12);
        }
        else {
            throw new GameException("This set is corrupted!!!");
        }
        return king;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isWhiteSet() {
        return whiteSet;
    }

    public void setWhiteSet(boolean whiteSet) {
        this.whiteSet = whiteSet;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
}
