/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.util.List;
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

    public King(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public void move(char rank, char file) throws GameException {
        super.move(rank, file);
        movedBefore = true;
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        int vDisplacement = Math.abs(desiredRank - getRank());
        int hDisplacement = Math.abs(desiredFile - getFile());
        Board board = getBoard();
        return ((vDisplacement <= 1 && hDisplacement <= 1) || (hDisplacement == 2 && isCastlingAllowed(desiredFile)))
                && !board.isThereAnyColleaguePieceAt(desiredRank, desiredFile, this)
                && !isTheOpponentKingSurroundMe() && !willBeInCheckAt(desiredRank, desiredFile);//////////////////////////////////
    }

    private boolean isCastlingAllowed(char kingNextFile) {
        Rook rook;
        try {
            rook = getCastlingRook(kingNextFile);
        } catch (GameException e) {
            return false;
        }
        if (rook == null) {
            return false;
        }
        Board board = getBoard();
        int hDisplacement = rook.getFile() - getFile();
        char auxRank = getRank();
        char auxFile = getFile();
        int rDisplacement = Math.abs(hDisplacement);
        for (int counter = 1; counter < rDisplacement; counter++) {
            auxFile += Integer.signum(hDisplacement);
            if (board.isThereAnyPieceAt(auxRank, auxFile)) {
                return false;
            }
        }
        return !movedBefore && !rook.isMovedBefore();
    }

    public Rook getCastlingRook(char kingNextFile) throws GameException {
        int hDisplacement = kingNextFile - getFile();
        SetOfPieces mySet = getBoard().getMySet(this);
        Rook rook = null;
        if (hDisplacement > 0) {
            rook = mySet.getRook(getRank(), 'h');
        } else if (hDisplacement < 0) {
            rook = mySet.getRook(getRank(), 'a');
        }
        return rook;
    }

    public Rook getCastlingRook(Movement kingMovement) throws GameException {
        if (kingMovement == null) {
            throw new GameException("King's movement must not be null!!!");
        }
        return getCastlingRook(kingMovement.getNextFile());
    }

    private boolean isTheOpponentKingSurroundMe() {
        return false; //////////////////////////////
    }

    private boolean isInCheck() {
        List<Piece> opponentPieces = getBoard().getOpponentSet(this).getPieces();
        for (Piece opponentPiece : opponentPieces) {
            try {
            Movement movement = new Movement(getRank(), getFile(), opponentPiece, null, null);
            if (isInCheck(movement)) {
                return true;
            }
            } catch (GameException e) {
            }
        }
        return false;
    }

    public boolean isInCheck(Movement lastMovement) {
        Piece piece = lastMovement.getPiece();
        Game game = lastMovement.getGame();
        try {
            Movement checkMovement = new Movement(getRank(), getFile(), piece, null, null);
            if (checkMovement.isValid()) {
                return true;
            }
        } catch (GameException e) {
        }
        return false;
    }

    private boolean willBeInCheckAt(char rank, char file) {
        return false; //////////////////////////
    }

    @Override
    public String toString() {
        if (super.isWhiteSet()) {
            return "K";
        } else {
            return "k";
        }
    }

    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Bishop && this.equals((King) piece);
    }

    public boolean equals(King king) {
        return super.equals(king) && movedBefore == king.movedBefore;
    }

    @Override
    public King clone() {
        King king = new King();
        king.setCode(getCode());
        king.setRank(getRank());
        king.setFile(getFile());
        king.setWhiteSet(isWhiteSet());
        king.setMovedBefore(isMovedBefore());
        return king;
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
