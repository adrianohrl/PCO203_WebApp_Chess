/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author adriano
 */
@Entity
public class Board implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    @OneToOne
    private SetOfPieces whiteSet;
    @OneToOne
    private SetOfPieces blackSet;

    public Board() {

    }

    public void promote(Pawn pawn, Piece promotedPiece)  throws GameException {
        getMySet(pawn).promote(pawn, promotedPiece);
    }

    public void enPassantCapture(Pawn hunter) throws GameException {
        if (hunter == null) {
            throw new GameException("It is not allowed to make an en passant movement!!!");
        }
        Pawn target = hunter.getEnPassantPawn();
        char rank = target.getRank();
        char file = target.getFile();
        if (hunter.isWhiteSet()) {
            ++rank;
        } else {
            --rank;
        }
        hunter.setRank(rank);
        hunter.setFile(file);
        if (hunter.isWhiteSet()) {
            blackSet.getPieces().remove(target);
        } else {
            whiteSet.getPieces().remove(target);
        }
    }

    public void castlingMove(King king, Rook rook) throws GameException {
        if (king == null) {
            throw new GameException("King must not be null!!!");
        }
        if (rook == null) {
            throw new GameException("Rook must not be null!!!");
        }
        int hDisplacement = rook.getFile() - king.getFile();
        char file = (char) (king.getFile() + 2 * Integer.signum(hDisplacement));
        king.setFile(file);
        file -= Integer.signum(hDisplacement);
        rook.setFile(file);
    }

    public void capture(Piece hunter, Piece target) throws GameException {
        if (hunter == null) {
            throw new GameException("Hunter must be not null!!!");
        }
        if (target == null) {
            throw new GameException("Target must not be null!!!");
        }
        hunter.setRank(target.getRank());
        hunter.setFile(target.getFile());
        if (hunter.isWhiteSet()) {
            blackSet.getPieces().remove(target);
        } else {
            whiteSet.getPieces().remove(target);
        }
    }

    public void move(Piece piece, char nextRank, char nextFile) throws GameException {
        if (piece == null) {
            throw new GameException("Piece must not be null!!!");
        }
        piece.move(nextRank, nextFile);
    }

    public void setup() {
        whiteSet = SetOfPieces.getWhiteSet(this);
        blackSet = SetOfPieces.getBlackSet(this);
    }

    public King getKing(SetOfPieces set) {
        King king = null;
        try {
            king = set.getKing();
        } catch (GameException e) {
        }
        return king;
    }

    public King getWhiteKing() {
        return getKing(whiteSet);
    }

    public King getBlackKing() {
        return getKing(blackSet);
    }

    public SetOfPieces getMySet(Piece piece) {
        SetOfPieces mySet = blackSet;
        if (piece.isWhiteSet()) {
            mySet = whiteSet;
        }
        return mySet;
    }

    public SetOfPieces getOpponentSet(Piece piece) {
        SetOfPieces opponentSet = whiteSet;
        if (piece.isWhiteSet()) {
            opponentSet = blackSet;
        }
        return opponentSet;
    }

    public SetOfPieces getOpponentSet(SetOfPieces colleagueSet) {
        SetOfPieces opponentSet = whiteSet;
        if (colleagueSet.isWhiteSet()) {
            opponentSet = blackSet;
        }
        return opponentSet;
    }

    private boolean isThereAnyPieceAt(char rank, char file, SetOfPieces set) {
        try {
            return set.getPiece(rank, file) != null;
        } catch (GameException e) {
            return false;
        }
    }

    public boolean isThereAnyPieceAt(char rank, char file) {
        return isThereAnyPieceAt(rank, file, whiteSet) || isThereAnyPieceAt(rank, file, blackSet);
    }

    public boolean isThereAnyColleaguePieceAt(char rank, char file, Piece piece) {
        return isThereAnyPieceAt(rank, file, getMySet(piece));
    }

    public boolean isThereAnyColleaguePieceAt(char rank, char file, SetOfPieces mySet) {
        return isThereAnyPieceAt(rank, file, mySet);
    }

    public boolean isThereAnyOpponentPieceAt(char rank, char file, Piece piece) {
        return isThereAnyPieceAt(rank, file, getOpponentSet(piece));
    }

    public boolean isThereAnyOpponentPieceAt(char rank, char file, SetOfPieces mySet) {
        return isThereAnyPieceAt(rank, file, getOpponentSet(mySet));
    }

    private boolean isThereAnyPawnAt(char rank, char file, SetOfPieces set) {
        try {
            return set.getPawn(rank, file) != null;
        } catch (GameException e) {
            return false;
        }
    }

    public boolean isThereAnyOpponentPawnAt(char rank, char file, Piece piece) {
        return isThereAnyPawnAt(rank, file, getOpponentSet(piece));
    }

    public boolean isThereAnyOpponentPawnAt(char rank, char file, SetOfPieces mySet) {
        return isThereAnyPawnAt(rank, file, getOpponentSet(mySet));
    }

    private boolean isTheKingAt(char rank, char file, SetOfPieces set) {
        try {
            return set.getKing(rank, file) != null;
        } catch (GameException e) {
            return false;
        }
    }

    public boolean isTheOpponentKingAt(char rank, char file, Piece piece) {
        return isTheKingAt(rank, file, getOpponentSet(piece));
    }

    public boolean isTheOpponentKingAt(char rank, char file, SetOfPieces mySet) {
        return isTheKingAt(rank, file, getOpponentSet(mySet));
    }

    @Override
    public String toString() {
        return "Board:\n" + whiteSet + blackSet;
    }

    @Override
    public Board clone() {
        Board board = new Board();
        board.setCode(code);
        board.setWhiteSet(whiteSet.clone());
        board.getWhiteSet().setBoard(board);
        List<Piece> whitePieces = board.getWhiteSet().getPieces();
        for (Piece whitePiece : whitePieces) {
            whitePiece.setBoard(board);
        }
        board.setBlackSet(blackSet.clone());
        board.getBlackSet().setBoard(board);
        List<Piece> blackPieces = board.getBlackSet().getPieces();
        for (Piece blackPiece : blackPieces) {
            blackPiece.setBoard(board);
        }
        return board;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SetOfPieces getWhiteSet() {
        return whiteSet;
    }

    public void setWhiteSet(SetOfPieces whiteSet) {
        this.whiteSet = whiteSet;
    }

    public SetOfPieces getBlackSet() {
        return blackSet;
    }

    public void setBlackSet(SetOfPieces blackSet) {
        this.blackSet = blackSet;
    }

}
