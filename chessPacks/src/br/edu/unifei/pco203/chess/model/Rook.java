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
public class Rook extends Piece {

    private boolean movedBefore = false;

    public Rook() {
        super();
    }

    public Rook(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public void move(char rank, char file) throws GameException {
        movedBefore = true;
        super.move(rank, file);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        int vDisplacement = Math.abs(desiredRank - getRank());
        int hDisplacement = Math.abs(desiredFile - getFile());
        return vDisplacement * hDisplacement == 0 && super.isValidMovement(desiredRank, desiredFile);
    }

    @Override
    public String toString() {
        if (super.isWhiteSet()) {
            return "R";
        } else {
            return "r";
        }
    }
    
    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Rook && this.equals((Rook) piece);
    }
    
    public boolean equals(Rook rook) {
        return super.equals(rook) && movedBefore == rook.movedBefore;
    }

    @Override
    public Rook clone() {
        Rook rook = new Rook();
        rook.setCode(getCode());
        rook.setRank(getRank());
        rook.setFile(getFile());
        rook.setWhiteSet(isWhiteSet());
        rook.setMovedBefore(isMovedBefore());
        return rook;
    }

    /*@Override
     public String toString() {
     return this.getClass().getSimpleName() + " " + super.toString();
     }*/
    
    public boolean isMovedBefore() {
        return movedBefore;
    }

    public void setMovedBefore(boolean movedBefore) {
        this.movedBefore = movedBefore;
    }

}
