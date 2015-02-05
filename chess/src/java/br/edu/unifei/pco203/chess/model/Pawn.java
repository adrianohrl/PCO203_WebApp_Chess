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
public class Pawn extends Piece {
    
    private boolean movedPreviously = false;

    public Pawn() {
        super();
    }
    
    public Pawn(char rank, char file, boolean whiteSet, Board board) {
        super(rank, file, whiteSet, board);
    }
    
    @Override
    public void move(char rank, char file) throws GameException {
        movedPreviously = true;
        super.move(rank, file);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        return true; ///////////////////////////
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }

    public boolean isMovedPreviously() {
        return movedPreviously;
    }

    public void setMovedPreviously(boolean movedPreviously) {
        this.movedPreviously = movedPreviously;
    }

}
