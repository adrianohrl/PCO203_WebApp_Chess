/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author adriano
 */
@Entity
public class SetOfPieces implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    private boolean whiteSet;
    @OneToMany
    private List<Piece> pieces = new ArrayList<>();
    @OneToMany
    private List<Movement> movements = new ArrayList<>();
    @ManyToOne
    private Board board;

    public SetOfPieces() {

    }

    private SetOfPieces(Board board) {
        this.board = board;
    }

    public static SetOfPieces getWhiteSet(Board board) {
        SetOfPieces whiteSet = new SetOfPieces(board);
        List<Piece> pieces = new ArrayList<>();
        try {
            for (int i = 0; i < 8; i++) {
                pieces.add(new Pawn('2', (char) ('a' + i), true, board));
            }
            pieces.add(new Rook('1', 'a', true, board));
            pieces.add(new Knight('1', 'b', true, board));
            pieces.add(new Bishop('1', 'c', true, board));
            pieces.add(new Queen('1', 'd', true, board));
            pieces.add(new King('1', 'e', true, board));
            pieces.add(new Bishop('1', 'f', true, board));
            pieces.add(new Knight('1', 'g', true, board));
            pieces.add(new Rook('1', 'h', true, board));
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
        whiteSet.setPieces(pieces);
        whiteSet.setWhiteSet(true);
        return whiteSet;
    }

    public static SetOfPieces getBlackSet(Board board) {
        SetOfPieces blackSet = new SetOfPieces(board);
        List<Piece> pieces = new ArrayList<>();
        try {
            for (int i = 0; i < 8; i++) {
                pieces.add(new Pawn('7', (char) ('a' + i), false, board));
            }
            pieces.add(new Rook('8', 'a', false, board));
            pieces.add(new Knight('8', 'b', false, board));
            pieces.add(new Bishop('8', 'c', false, board));
            pieces.add(new Queen('8', 'd', false, board));
            pieces.add(new King('8', 'e', false, board));
            pieces.add(new Bishop('8', 'f', false, board));
            pieces.add(new Knight('8', 'g', false, board));
            pieces.add(new Rook('8', 'h', false, board));
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
        blackSet.setPieces(pieces);
        blackSet.setWhiteSet(false);
        return blackSet;
    }

    public void promote(Pawn pawn, Piece promotedPiece) throws GameException {
        if ((pawn.isWhiteSet() && pawn.getRank() != '8') || (!pawn.isWhiteSet() && pawn.getRank() != '1')) {
            throw new GameException("This pawn cannot be promoted!!!");
        }
        pawn.promote();
        promotedPiece.copyFrom(pawn);
        pieces.remove(pawn);
        pieces.add(promotedPiece);
    }
/*
    private int getPieceIndex(Piece piece) {
        int index = 0;
        for (Piece p : pieces) {
            if (p.getRank() == piece.getRank() && p.getFile() == piece.getFile()) {
                return index;
            }
            ++index;
        }
        return index;
    }*/

    @Override
    public String toString() {
        String string = "\t";
        if (this.isWhiteSet()) {
            string += "White Set: \n";
        } else {
            string += "Black Set: \n";
        }
        for (Piece piece : pieces) {
            string += "\t\t" + piece + "\n";
        }
        return string;
    }
    
    @Override
    public SetOfPieces clone() {
        SetOfPieces set = new SetOfPieces();
        set.setCode(code);
        set.setWhiteSet(whiteSet);
        List<Piece> clonedPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            clonedPieces.add(piece.clone());
        }
        set.setPieces(clonedPieces);
        List<Movement> clonedMovements = new ArrayList<>();
        for (Movement movement : movements) {
            clonedMovements.add(movement.clone());
        }
        set.setMovements(clonedMovements);
        return set;
    }

    private Piece getPiece(char code, Class<?> type) throws GameException {
        Piece piece = null;
        code = Character.toLowerCase(code);
        int counter = 0;
        //dealing with a rank code
        if (code >= '1' && code <= '8') {
            for (Piece p : pieces) {
                if (type.isInstance(p) && p.getRank() == code) {
                    if (++counter > 1) {
                        throw new GameException("There is more than one " + type.getSimpleName().toLowerCase() + " in the same rank, use a file code, instead!!!");
                    }
                    piece = p;
                }
            }
            if (piece == null) {
                throw new GameException("There is no " + type.getSimpleName().toLowerCase() + " in the referenced rank!!!");
            }
        } //dealing with a file code
        else if (code >= 'a' && code <= 'h') {
            for (Piece p : pieces) {
                if (type.isInstance(p) && p.getFile() == code) {
                    if (++counter > 1) {
                        throw new GameException("There is more than one " + type.getSimpleName().toLowerCase() + " in the same file, use a rank code, instead!!!");
                    }
                    piece = p;
                }
            }
            if (piece == null) {
                throw new GameException("There is no " + type.getSimpleName().toLowerCase() + " in the referenced file!!!");
            }
        } else {
            throw new GameException("Invalid  code: " + Character.toUpperCase(code) + ", it's not a rank neither a file code!!!");
        }
        return piece;
    }

    public Piece getPiece(char rank, char file) throws GameException {
        rank = Character.toLowerCase(rank);
        file = Character.toLowerCase(file);
        if (rank < '1' || rank > '8') {
            throw new GameException("Invalid rank code: " + Character.toUpperCase(rank) + "!!!");
        } else if (file < 'a' || file > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(file) + "!!!");
        }
        for (Piece piece : pieces) {
            if (piece.getRank() == rank && piece.getFile() == file) {
                return piece;
            }
        }
        throw new GameException("There is no piece at " + Character.toUpperCase(file) + "" + Character.toUpperCase(rank) + "!!!");
    }

    private Piece getPiece(char rank, char file, Class<?> type) throws GameException {
        rank = Character.toLowerCase(rank);
        file = Character.toLowerCase(file);
        if (rank < '1' || rank > '8') {
            throw new GameException("Invalid rank code: " + Character.toUpperCase(rank) + "!!!");
        } else if (file < 'a' || file > 'h') {
            throw new GameException("Invalid file code: " + Character.toUpperCase(file) + "!!!");
        }
        for (Piece piece : pieces) {
            if (type.isInstance(piece) && piece.getRank() == rank && piece.getFile() == file) {
                return piece;
            }
        }
        throw new GameException("There is no " + type.getSimpleName().toLowerCase() + " in the referenced rank!!!");
    }

    public Bishop getBishop(char code) throws GameException {
        return (Bishop) getPiece(code, Bishop.class);
    }

    public Bishop getBishop(char rank, char file) throws GameException {
        return (Bishop) getPiece(rank, file, Bishop.class);
    }
    
    public King getKing() throws GameException {
        return getKings().get(0);
    }

    public King getKing(char code) throws GameException {
        return (King) getPiece(code, King.class);
    }

    public King getKing(char rank, char file) throws GameException {
        return (King) getPiece(rank, file, King.class);
    }

    public Knight getKnight(char code) throws GameException {
        return (Knight) getPiece(code, Knight.class);
    }

    public Knight getKnight(char rank, char file) throws GameException {
        return (Knight) getPiece(rank, file, Knight.class);
    }

    public Pawn getPawn(char code) throws GameException {
        return (Pawn) getPiece(code, Pawn.class);
    }

    public Pawn getPawn(char rank, char file) throws GameException {
        return (Pawn) getPiece(rank, file, Pawn.class);
    }

    public Queen getQueen(char code) throws GameException {
        return (Queen) getPiece(code, Queen.class);
    }

    public Queen getQueen(char rank, char file) throws GameException {
        return (Queen) getPiece(rank, file, Queen.class);
    }

    public Rook getRook(char code) throws GameException {
        return (Rook) getPiece(code, Rook.class);
    }

    public Rook getRook(char rank, char file) throws GameException {
        return (Rook) getPiece(rank, file, Rook.class);
    }
    
    private List<Piece> getPieces(Class<?> type) {
        List<Piece> foundPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if (type.isInstance(piece)) {
                foundPieces.add(piece);
            }
        }
        return foundPieces;
    }
    
    public List<Pawn> getPawns() {
        List<Pawn> foundPawns = new ArrayList<>();
        List<Piece> foundPieces = getPieces(Pawn.class);
        for (Piece foundPiece : foundPieces) {
            foundPawns.add((Pawn) foundPiece);
        }
        return foundPawns;
    }
    
    public List<Rook> getRooks() {
        List<Rook> foundRooks = new ArrayList<>();
        List<Piece> foundPieces = getPieces(Rook.class);
        for (Piece foundPiece : foundPieces) {
            foundRooks.add((Rook) foundPiece);
        }
        return foundRooks;
    }
    
    public List<Knight> getKnights() {
        List<Knight> foundKnights = new ArrayList<>();
        List<Piece> foundPieces = getPieces(Knight.class);
        for (Piece foundPiece : foundPieces) {
            foundKnights.add((Knight) foundPiece);
        }
        return foundKnights;
    }
    
    public List<Bishop> getBishops() {
        List<Bishop> foundBishops = new ArrayList<>();
        List<Piece> foundPieces = getPieces(Bishop.class);
        for (Piece foundPiece : foundPieces) {
            foundBishops.add((Bishop) foundPiece);
        }
        return foundBishops;
    }
    
    public List<Queen> getQueens() {
        List<Queen> foundQueens = new ArrayList<>();
        List<Piece> foundPieces = getPieces(Queen.class);
        for (Piece foundPiece : foundPieces) {
            foundQueens.add((Queen) foundPiece);
        }
        return foundQueens;
    }
    
    public List<King> getKings() {
        List<King> foundKings = new ArrayList<>();
        List<Piece> foundPieces = getPieces(King.class);
        for (Piece foundPiece : foundPieces) {
            foundKings.add((King) foundPiece);
        }
        return foundKings;
    }
    
    public List<Piece> getPawnPieces() {
        return getPieces(Pawn.class);
    }
    
    public List<Piece> getRookPieces() {
        return getPieces(Rook.class);
    }
    
    public List<Piece> getKnightPieces() {
        return getPieces(Knight.class);
    }
    
    public List<Piece> getBishopPieces() {
        return getPieces(Bishop.class);
    }
    
    public List<Piece> getQueenPieces() {
        return getPieces(Queen.class);
    }
    
    public List<Piece> getKingPieces() {
        return getPieces(King.class);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isWhiteSet() {
        return whiteSet;
    }

    public void setWhiteSet(boolean whiteSet) {
        this.whiteSet = whiteSet;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
