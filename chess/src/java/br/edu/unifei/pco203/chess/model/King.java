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
                && !isTheOpponentKingCloseTo(desiredRank, desiredFile);//////////////////////////////////
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

    private boolean isTheOpponentKingCloseTo(char rank, char file) {
        Board board = getBoard();
        SetOfPieces opponentSet = board.getOpponentSet(this);
        King king = board.getKing(opponentSet);
        return Math.abs(rank - king.getRank()) < 2 && Math.abs(file - king.getFile()) < 2;
    }

    public boolean isInCheck() {
        List<Piece> opponentPieces = getBoard().getOpponentSet(this).getPieces();
        for (Piece opponentPiece : opponentPieces) {
            Movement movement = new Movement(getRank(), getFile(), opponentPiece);
            if (isInCheck(movement)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInCheck(Movement lastMovement) {
        Piece piece = lastMovement.getPiece();
        return piece.isValidMovement(getRank(), getFile());
    }

    public boolean isCheckMate() {
        if (!isInCheck()) {
            return false;
        }
        List<Piece> pieces = getBoard().getMySet(this).getPieces();
        for (Piece piece : pieces) {
            for (char rank = '1'; rank <= '8'; rank++) {
                for (char file = 'a'; file <= 'h'; file++) {
                    if (piece.isValidMovement(rank, file)) {
                        if (!King.willBeInCheckOrIsInvalid(rank, file, piece)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    protected boolean willBeInCheckAt(char desiredRank, char desiredFile) {
        try {
            Board clonedBoard = getBoard().clone();
            SetOfPieces mySet = clonedBoard.getMySet(this);
            King king = mySet.getKing(getRank(), getFile());
            Movement movement = new Movement(desiredRank, desiredFile, king);
            movement.moveInFuture();
            return king.isInCheck();
        } catch (GameException e) {
            return false;
        }
    }

    protected static boolean willBeInCheckOrIsInvalid(char nextRank, char nextFile, Piece originalPiece) {
        Board clonedBoard = originalPiece.getBoard().clone();
        SetOfPieces mySet = clonedBoard.getMySet(originalPiece);
        Piece piece = null;
        try {
            piece = mySet.getPiece(originalPiece.getRank(), originalPiece.getFile());
            if (mySet.getPiece(nextRank, nextFile) != null) {
                return true;
            }
        } catch (GameException e) {
        }
        try {
            Movement movement = new Movement(nextRank, nextFile, piece);
            movement.moveInFuture();
            King king = mySet.getKing();
            return king.isInCheck();
        } catch (GameException e) {
            StackTraceElement topSTE = e.getStackTrace()[0];
            return !topSTE.getMethodName().equals("move");
        }
    }

    @Override
    public String getNotationCode() {
        return "k";
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
