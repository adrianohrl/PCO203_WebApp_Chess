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
public class Bishop extends Piece {

    public Bishop() {
        super();
    }
    
    public Bishop(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        int vDisplacement = Math.abs(desiredRank - getRank());
        int hDisplacement = Math.abs(desiredFile - getFile());
        return vDisplacement == hDisplacement && super.isValidMovement(desiredRank, desiredFile);
    }
    
    @Override
    public String toString() {
        if (super.isWhiteSet()) {
            return "B";
        } else {
            return "b";
        }
    }
    
    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Bishop;
    }
    
    /*@Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }*/
    
}
