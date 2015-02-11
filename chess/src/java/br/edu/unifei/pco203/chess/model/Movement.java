/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author adriano
 */
@Entity
public class Movement implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    private char currentRank;
    private char currentFile;
    private char nextRank;
    private char nextFile;
    private boolean capture;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Calendar currentDate = new GregorianCalendar();
    @ManyToOne
    private Piece piece;
    @ManyToOne
    private Player player;
    @ManyToOne
    private Game game;

    public Movement() {
    }
    
    protected Movement(char desiredRank, char desiredFile, Piece piece) {
        this.currentRank = piece.getRank();
        this.currentFile = piece.getFile();
        this.nextRank = desiredRank;
        this.nextFile = desiredFile;
        this.piece = piece;
        this.player = null;
        this.game = null;
        this.capture = isCaptureMovement();
    }

    public Movement(char desiredRank, char desiredFile, Piece piece, Player player, Game game) throws GameException {
        desiredRank = Character.toLowerCase(desiredRank);
        desiredFile = Character.toLowerCase(desiredFile);
        if (desiredRank < '1' || desiredRank > '8') {
            throw new GameException("Invalid desired rank: " + desiredRank + "!!!");
        }
        if (desiredFile < 'a' || desiredFile > 'h') {
            throw new GameException("Invalid desired file: " + desiredFile + "!!!");
        }
        if (desiredRank == piece.getRank() && desiredFile == piece.getFile()) {
            throw new GameException("It is not a movement. Rank and file are both still the same!!!");
        }
        this.currentRank = piece.getRank();
        this.currentFile = piece.getFile();
        this.nextRank = desiredRank;
        this.nextFile = desiredFile;
        this.piece = piece;
        this.player = player;
        this.game = game;
        this.capture = isCaptureMovement();
    }

    protected void move() throws GameException {
        Board board = piece.getBoard();
        if (isValid()) {
            if (isEnPassantCaptureMovement()) {
                Pawn pawn = (Pawn) piece;
                board.enPassantCapture(pawn);
            } else if (isCastlingMovement()) {
                King king = (King) piece;
                Rook rook = king.getCastlingRook(this);
                board.castlingMove(king, rook);
            } else if (isCapture()) {
                SetOfPieces opponentSet = board.getOpponentSet(piece);
                board.capture(piece, opponentSet.getPiece(nextRank, nextFile));
            } else {
                board.move(piece, nextRank, nextFile);
            }
            currentDate = new GregorianCalendar();
        } else {
            throw new GameException("Invalid movement!!! Try again.");
        }
    }
    
    protected void moveInFuture() throws GameException {
        Board board = piece.getBoard();
        if (piece.isValidMovement(nextRank, nextFile)) {
            if (isEnPassantCaptureMovement()) {
                Pawn pawn = (Pawn) piece;
                board.enPassantCapture(pawn);
            } else if (isCastlingMovement()) {
                King king = (King) piece;
                Rook rook = king.getCastlingRook(this);
                board.castlingMove(king, rook);
            } else if (isCapture()) {
                SetOfPieces opponentSet = board.getOpponentSet(piece);
                board.capture(piece, opponentSet.getPiece(nextRank, nextFile));
            } else {
                board.move(piece, nextRank, nextFile);
            }
            currentDate = new GregorianCalendar();
        } else {
            throw new GameException("Invalid movement!!! Try again.");
        }
    }

    protected boolean isValid() {
        boolean valid = piece.isValidMovement(nextRank, nextFile);
        if (piece instanceof King) {
            valid &= piece.isValidMovement(nextRank, nextFile) && !((King) piece).willBeInCheckAt(nextRank, nextFile);
        }
        return valid && !Piece.willPutMyKingInCheck(nextRank, nextFile, piece);
    }

    protected boolean isCaptureMovement() {
        return piece.getBoard().isThereAnyOpponentPieceAt(nextRank, nextFile, piece) || isEnPassantCaptureMovement();
    }

    protected boolean isEnPassantCaptureMovement() {
        Pawn pawn = null;
        if (piece instanceof Pawn) {
            pawn = (Pawn) piece;
        }
        Board board = piece.getBoard();
        return pawn != null && pawn.isEnPassantAllowed() && board.isThereAnyOpponentPawnAt(currentRank, nextFile, piece)
                && !board.isThereAnyPieceAt(nextRank, nextFile);
    }

    protected boolean isCastlingMovement() {
        King king = null;
        if (piece instanceof King) {
            king = (King) piece;
        }
        return king != null && !king.isMovedBefore() && Math.abs(currentFile - nextFile) == 2;
    }
    
    public boolean isPromotionMovement() {
        Pawn pawn = null;
        if (piece instanceof Pawn) {
            pawn = (Pawn) piece;
        }
        return pawn != null && ((pawn.isWhiteSet() && nextRank == '8') || (!pawn.isWhiteSet() && nextRank == '1'));
    }

    public static Movement process(String code, Game game) throws GameException {
        if (code.length() != 5) {
            throw new GameException("Invalid movement code length!!!");
        }
        SetOfPieces set = game.getSetTurn();
        Piece piece = null;
        switch (Character.toLowerCase(code.charAt(0))) {
            case 'b':
                piece = set.getBishop(code.charAt(1), code.charAt(2));
                break;
            case 'k':
                piece = set.getKing(code.charAt(1), code.charAt(2));
                break;
            case 'n':
                piece = set.getKnight(code.charAt(1), code.charAt(2));
                break;
            case 'p':
                piece = set.getPawn(code.charAt(1), code.charAt(2));
                break;
            case 'q':
                piece = set.getQueen(code.charAt(1), code.charAt(2));
                break;
            case 'r':
                piece = set.getRook(code.charAt(1), code.charAt(2));
                break;
            default:
                throw new GameException("Invalid piece code!!!");
        }
        return new Movement(code.charAt(3), code.charAt(4), piece, game.getPlayerTurn(), game);
    }

    public static Piece processCode(SetOfPieces set, String movement) throws GameException {
        if (movement.length() < 4) {
            throw new GameException("Invalid movement code, too small!!!");
        } else if (movement.length() > 5) {
            throw new GameException("Invalid movement code, too big!!!");
        }
        Piece piece = null;
        switch (Character.toLowerCase(movement.charAt(0))) {
            case 'b':
                if (movement.length() == 4) {
                    piece = set.getBishop(movement.charAt(1));
                } else {
                    piece = set.getBishop(movement.charAt(1), movement.charAt(2));
                }
                break;
            case 'k':
                if (movement.length() == 4) {
                    piece = set.getKing(movement.charAt(1));
                } else {
                    piece = set.getKing(movement.charAt(1), movement.charAt(2));
                }
                break;
            case 'n':
                if (movement.length() == 4) {
                    piece = set.getKnight(movement.charAt(1));
                } else {
                    piece = set.getKnight(movement.charAt(1), movement.charAt(2));
                }
                break;
            case 'p':
                if (movement.length() == 4) {
                    piece = set.getPawn(movement.charAt(1));
                } else {
                    piece = set.getPawn(movement.charAt(1), movement.charAt(2));
                }
                break;
            case 'q':
                if (movement.length() == 4) {
                    piece = set.getQueen(movement.charAt(1));
                } else {
                    piece = set.getQueen(movement.charAt(1), movement.charAt(2));
                }
                break;
            case 'r':
                if (movement.length() == 4) {
                    piece = set.getRook(movement.charAt(1));
                } else {
                    piece = set.getRook(movement.charAt(1), movement.charAt(2));
                }
                break;
            default:
                throw new GameException("Invalid piece code!!!");
        }
        return piece;
    }

    @Override
    public String toString() {
        String string = "Black ";
        if (piece.isWhiteSet()) {
            string = "White ";
        }
        string += piece.getClass().getSimpleName().toLowerCase() + " from "
                + Character.toUpperCase(currentFile) + "" + Character.toUpperCase(currentRank) + " to "
                + Character.toUpperCase(nextFile) + "" + Character.toUpperCase(nextRank);
        return string;
    }

    @Override
    public Movement clone() {
        Movement movement = new Movement();
        movement.setCode(code);
        movement.setCurrentRank(currentRank);
        movement.setCurrentFile(currentFile);
        movement.setNextRank(nextRank);
        movement.setNextFile(nextFile);
        movement.setCurrentDate(currentDate);
        movement.setCapture(capture);
        movement.setPiece(piece.clone());
        movement.setPlayer(player.clone());
        return movement;
    }

    public int compareTo(Movement movement) {
        return currentDate.compareTo(movement.getCurrentDate());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public char getCurrentRank() {
        return currentRank;
    }

    public void setCurrentRank(char currentRank) {
        this.currentRank = currentRank;
    }

    public char getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(char currentFile) {
        this.currentFile = currentFile;
    }

    public char getNextRank() {
        return nextRank;
    }

    public void setNextRank(char nextRank) {
        this.nextRank = nextRank;
    }

    public char getNextFile() {
        return nextFile;
    }

    public void setNextFile(char nextFile) {
        this.nextFile = nextFile;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }

    public Calendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
