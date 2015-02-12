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
public class Queen extends Piece {

    public Queen () {
        super();
    }
    
    public Queen(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        int vDisplacement = Math.abs(desiredRank - getRank());
        int hDisplacement = Math.abs(desiredFile - getFile());
        return (vDisplacement * hDisplacement == 0 || vDisplacement == hDisplacement) && super.isValidMovement(desiredRank, desiredFile);
    }
    
    @Override
    public String getNotationCode() {
        return "q";
    }
    
    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Queen;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }

    @Override
    public Queen clone() {
        Queen queen = new Queen();
        queen.setCode(getCode());
        queen.setRank(getRank());
        queen.setFile(getFile());
        queen.setWhiteSet(isWhiteSet());
        return queen;
    }
    
}
