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
public class Knight extends Piece {

    public Knight() {
        super();
    }

    public Knight(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        if (getBoard().isThereAnyColleaguePieceAt(desiredRank, desiredFile, this)) {
            return false;
        }
        int vDisplacement = Math.abs(desiredRank - getRank());
        int hDisplacement = Math.abs(desiredFile - getFile());
        return vDisplacement * hDisplacement == 2;
    }

    @Override
    public String toString() {
        if (super.isWhiteSet()) {
            return "N";
        } else {
            return "n";
        }
    }
    
    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Knight;
    }

    @Override
    public Knight clone() {
        Knight knight = new Knight();
        knight.setCode(getCode());
        knight.setRank(getRank());
        knight.setFile(getFile());
        knight.setWhiteSet(isWhiteSet());
        return knight;
    }

    /*@Override
     public String toString() {
     return this.getClass().getSimpleName() + " " + super.toString();
     }*/
}
