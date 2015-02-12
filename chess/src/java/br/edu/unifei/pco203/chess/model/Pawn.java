/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author adriano
 */
@Entity
public class Pawn extends Piece {

    private boolean atHome = true;
    @OneToOne
    private Pawn enPassantPawn;

    public Pawn() {
        super();
    }

    public Pawn(char rank, char file, boolean whiteSet, Board board) throws GameException {
        super(rank, file, whiteSet, board);
    }

    @Override
    public void move(char rank, char file) throws GameException {
        char previousRank = getRank();
        super.move(rank, file);
        atHome = false;
        enPassantPawn = null;
        if (Math.abs(previousRank - rank) == 2) {
            SetOfPieces opponentSet = getBoard().getOpponentSet(this);
            try {
                setEnPassantTo(opponentSet.getPawn(rank, (char) (file - 1)));
            } catch (GameException e) {
            }
            try {
                setEnPassantTo(opponentSet.getPawn(rank, (char) (file + 1)));
            } catch (GameException e) {
            }
        }
    }

    public void promote() {
        if (getPromotionDate() == null) {
            setPromotionDate(new GregorianCalendar());
        }
    }

    @Override
    public boolean isValidMovement(char desiredRank, char desiredFile) {
        try {
            char rank = getRank();
            char file = getFile();
            Board board = getBoard();
            int vDisplacement = Math.abs(desiredRank - getRank());
            int hDisplacement = Math.abs(desiredFile - getFile());
            SetOfPieces opponentSet = board.getOpponentSet(this);
            return !board.isThereAnyColleaguePieceAt(desiredRank, desiredFile, this)
                    && ((isWhiteSet() && desiredRank > rank) || (!isWhiteSet() && desiredRank < rank))
                    && ((!isAtHome() && vDisplacement == 1)
                    || (isAtHome()
                    && (vDisplacement == 1
                    || (vDisplacement == 2 && desiredFile == file
                    && !board.isThereAnyPieceAt((char) ((desiredRank + rank) / 2), desiredFile)))))
                    && ((hDisplacement == 0 && !board.isThereAnyPieceAt(desiredRank, desiredFile))
                    || (hDisplacement == 1
                    && (board.isThereAnyOpponentPieceAt(desiredRank, desiredFile, this)
                    || (isEnPassantAllowed() && board.isThereAnyOpponentPawnAt(rank, desiredFile, this)
                    && enPassantPawn.equals(opponentSet.getPawn(rank, desiredFile))
                    && !board.isThereAnyOpponentPieceAt(desiredRank, desiredFile, this)))));
        } catch (GameException e) {
            return false;
        }
    }

    private void setEnPassantTo(Pawn pawn) {
        pawn.setEnPassantPawn(this);
    }

    public boolean isEnPassantAllowed() {
        return enPassantPawn != null;
    }

    @Override
    public String getNotationCode() {
        return "p";
    }

    @Override
    public boolean equals(Piece piece) {
        return super.equals(piece) && piece instanceof Pawn && this.equals((Pawn) piece);
    }

    public boolean equals(Pawn pawn) {
        return super.equals(pawn) && atHome == pawn.atHome
                && ((enPassantPawn != null && enPassantPawn.equals(pawn.enPassantPawn) || enPassantPawn == pawn.enPassantPawn));
    }

    @Override
    public Pawn clone() {
        Pawn pawn = new Pawn();
        pawn.setCode(getCode());
        pawn.setRank(getRank());
        pawn.setFile(getFile());
        pawn.setAtHome(atHome);
        if (enPassantPawn != null) {
            pawn.setEnPassantPawn(enPassantPawn.clone());
        }
        return pawn;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + super.toString();
    }

    public boolean isAtHome() {
        return atHome;
    }

    public void setAtHome(boolean atHome) {
        this.atHome = atHome;
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

}
