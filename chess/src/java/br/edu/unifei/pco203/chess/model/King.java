/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import javax.persistence.Entity;

/**
 *
 * @author adriano
 */
@Entity
public class King extends Piece {

    private boolean movedBefore = false;
    
    public King() {
        super();
    }
    
    public King(char rank, char file, boolean whiteSet, Board board) {
        super(rank, file, whiteSet, board);
    }

    public void castlingMove(boolean smallestOne) throws GameException {
        if (!isCastlingAllowed(smallestOne)) {
            throw new GameException("Castling Move is not allowed!!!");
        }
        Board board = this.getBoard();
        if (smallestOne) {
            this.setFile('g'); 
            Rook rook;
            if (this.isWhiteSet()) {
               rook = board.getWhiteSet().getRook('h');
            }
            else {
               rook = board.getBlackSet().getRook('h');
            }
            ////////////////////////////////
            rook.setFile('f');
        }
        
    }

    public boolean isCastlingAllowed(boolean smallestOne) {
        return true;
    }

    @Override
    public void move(char rank, char file) throws GameException {
        movedBefore = true;
        super.move(rank, file);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }

    public boolean isMovedBefore() {
        return movedBefore;
    }

    public void setMovedBefore(boolean movedBefore) {
        this.movedBefore = movedBefore;
    }

}
