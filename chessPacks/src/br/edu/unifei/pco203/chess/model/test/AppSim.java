/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model.test;

import br.edu.unifei.pco203.chess.model.*;
import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.GameDAO;
import br.edu.unifei.pco203.chess.control.dao.MovementDAO;
import br.edu.unifei.pco203.chess.control.dao.PieceDAO;
import br.edu.unifei.pco203.chess.control.dao.PlayerDAO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
public class AppSim {

    private final static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws GameException {

        EntityManager em = DataSource.createEntityManager();

        List<Iterator<String>> its = new ArrayList<>();
        its.add(AppSim.testCastling());
        its.add(AppSim.testCheck());
        its.add(AppSim.testKingMovementLimitations());
        its.add(AppSim.testPromotion());
        its.add(AppSim.testPuttingKingInCheck());
        its.add(AppSim.testFalseCheckMate());
        for (Iterator<String> it : its) {

            System.out.println("Enter the white player name: ");
            String whiteName = "Adriano";//scan.nextLine();
            Player white = AppSim.createPlayer(whiteName);

            System.out.println("Enter the black player name: ");
            String blackName = "Mônica";//scan.nextLine();
            Player black = AppSim.createPlayer(blackName);

            Game game = new Game(white, black);
            game.getStarted();
            Clock clock = new Clock(white, black, game);
            Board board = game.getBoard();

            AppSim.displayMenu();
            System.out.println("So, let's play chess!!!");
            System.out.println("---------------------------------");
            AppSim.printBoard(board);

            GameDAO gameDAO = new GameDAO(em);
            gameDAO.createFullfilledGame(game);

            while (!game.isFinished()) {
                System.out.println(game.getPlayerTurn() + ", enter the next movement: ");
                String code = it.next();//scan.nextLine();
                try {
                    Movement movement = Movement.process(code, game);
                    game.move(movement);
                    AppSim.printBoard(board);
                    if (movement.isPromotionMovement()) {
                        AppSim.promote((Pawn) movement.getPiece());
                    }
                    King opponentKing = board.getOpponentSet(movement.getPiece()).getKings().get(0);
                    if (opponentKing.isCheckMate()) {
                        game.checkMate();
                        System.out.println("Check Mate!!!");
                        System.out.println(game.getWinner().getName() + " wins!!!");
                        System.out.println("Game Over!!!");
                    } else if (opponentKing.isInCheck()) {
                        System.out.println(game.getPlayerTurn().getName() + "'s king is in check!!!");
                    }
                    MovementDAO moveDAO = new MovementDAO(em);
                    moveDAO.create(movement);
                    gameDAO.update(game);
                    clock.toggle();
                } catch (GameException e) {
                    System.out.println(e.getMessage());
                }
                if (!game.isFinished() && !it.hasNext()) {
                    game.pause();
                    clock.pauseGame();
                    System.out.println("Paused Game!!!");
                    break;
                }
            }
            
            gameDAO.update(game);
            System.out.println("-------------------- Movements --------------------");

            AppSim.displayMovements(board);

        }

        em.close();
        DataSource.closeEntityManagerFactory();
    }

    private static void promote(Pawn pawn) {
        char p;
        System.out.println("Pawn promoted!!!");
        Piece promotedPiece = null;
        do {
            System.out.println("Choose between (R) for rook, (N) for knight, (B) for bishop or (Q) for queen.");
            String promotion = scan.nextLine();
            p = Character.toLowerCase(promotion.charAt(0));
            switch (p) {
                case 'r':
                    promotedPiece = new Rook();
                    break;
                case 'n':
                    promotedPiece = new Knight();
                    break;
                case 'b':
                    promotedPiece = new Bishop();
                    break;
                case 'q':
                    promotedPiece = new Queen();
                    break;
                default:
                    System.out.println("Invalid input!!!");
            }
        } while ((p != 'r') && (p != 'n') && (p != 'b') && (p != 'q'));
        AppSim.registerPromotion(pawn, promotedPiece);
        AppSim.printBoard(pawn.getBoard());
    }

    private static void registerPromotion(Pawn pawn, Piece promotedPiece) {
        EntityManager em = DataSource.createEntityManager();
        Board board = pawn.getBoard();
        try {
            board.promote(pawn, promotedPiece);
        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
        PieceDAO pDAO = new PieceDAO(em);
        pDAO.create(promotedPiece);
    }

    private static Player createPlayer(String name) {
        EntityManager em = DataSource.createEntityManager();
        PlayerDAO pDAO = new PlayerDAO(em);
        Player player = pDAO.find(name);
        if (player == null) {
            player = new Player(name);
        }
        em.close();
        return player;
    }

    public static void printBoard(Board board) {
        String[][] matrix = new String[8][];
        for (int i = 0; i < 8; i++) {
            matrix[i] = new String[8];
            for (int j = 0; j < 8; j++) {
                matrix[i][j] = "   ";
                if (Boolean.logicalXor(i % 2 != 0, j % 2 != 0)) {
                    matrix[i][j] = " * ";
                }
            }
        }
        for (Piece piece : board.getWhiteSet().getPieces()) {
            int row = 7 - (int) (piece.getRank() - '1');
            int column = (int) (piece.getFile() - 'a');
            matrix[row][column] = " " + piece + " ";
        }
        for (Piece piece : board.getBlackSet().getPieces()) {
            int row = 7 - (int) (piece.getRank() - '1');
            int column = (int) (piece.getFile() - 'a');
            matrix[row][column] = " " + piece + " ";
        }
        String string = "     A  B  C  D  E  F  G  H  \n\n";
        for (int i = 0; i < matrix.length; i++) {
            Integer rank = 8 - i;
            string += rank + "   ";
            for (int j = 0; j < matrix[i].length; j++) {
                string += matrix[i][j];
            }
            string += "   " + rank + "\n";
        }
        string += "\n     A  B  C  D  E  F  G  H";
        System.out.println(string);
    }

    private static void displayMenu() {
        System.out.println("---------------------------------");
        System.out.println("Menu: ");
        System.out.println("Input format: P(r)(f)RF");
        System.out.println("\t(P)iece code: ");
        System.out.println("\t\tB for Bishop");
        System.out.println("\t\tK for King");
        System.out.println("\t\tN for Knight");
        System.out.println("\t\tP for Pawn");
        System.out.println("\t\tQ for Queen");
        System.out.println("\t\tR for Rook");
        System.out.println("\t(r)ank code: [current rank - optional]");
        System.out.println("\t\t1 to 8");
        System.out.println("\t(f)ile code: [current file - optional]");
        System.out.println("\t\tA to H");
        System.out.println("\t(R)ank code: [desired rank]");
        System.out.println("\t\t1 to 8");
        System.out.println("\t(F)ile code: [desired file]");
        System.out.println("\t\tA to H");
        System.out.println("---------------------------------");
    }

    private static void displayMovements(Board board) {
        Iterator<Movement> whiteSetMovements = board.getWhiteSet().getMovements().iterator();
        Iterator<Movement> blackSetMovements = board.getBlackSet().getMovements().iterator();
        int counter = 0;
        while (whiteSetMovements.hasNext() || blackSetMovements.hasNext()) {
            if (whiteSetMovements.hasNext()) {
                System.out.println(++counter + ": " + whiteSetMovements.next());
            }
            if (blackSetMovements.hasNext()) {
                System.out.println(++counter + ": " + blackSetMovements.next());
            }
        }
    }

    private static Iterator<String> testCastling() {
        List<String> moves = new ArrayList<>();
        moves.add("n1g3f");/*w*/ moves.add("n8b6c");//b
        moves.add("p2g3g");/*w*/ moves.add("p7d6d");//b
        moves.add("b1f2g");/*w*/ moves.add("b8c3h");//b
        moves.add("p2e4e");/*w*/ moves.add("q8d7d");//b
        moves.add("k1e2e");/*w*/ moves.add("r8a8d");//b
        moves.add("k2e1e");/*w*/ moves.add("r8d8a");//b
        moves.add("k1e1c");//w
        moves.add("k1e1g");//w
        moves.add("p2a3a");/*w*/ moves.add("k8e8g");//b
        moves.add("k8e8c");//b
        return moves.iterator();
    }

    private static Iterator<String> testPromotion() {
        List<String> moves = new ArrayList<>();
        moves.add("p2a4a");/*w*/ moves.add("n8b6a");//b
        moves.add("p4a5a");/*w*/ moves.add("p7b5b");//b
        moves.add("p5a6b");/*w*/ moves.add("p7h6h");//b
        moves.add("p6b7b");/*w*/ moves.add("p7g5g");//b
        moves.add("p7b8b");/*w*/ moves.add("p7f6f");//b
        moves.add("q8b8c");/*w*/

        return moves.iterator();
    }

    private static Iterator<String> testCheck() {
        List<String> moves = new ArrayList<>();
        moves.add("p2e3e");/*w*/ moves.add("p7a6a");//b
        moves.add("q1d3f");/*w*/ moves.add("p6a5a");//b
        moves.add("b1f4c");/*w*/ moves.add("p7b6b");//b
        moves.add("q3f7f");/*w*/

        return moves.iterator();
    }

    private static Iterator<String> testPuttingKingInCheck() {
        List<String> moves = new ArrayList<>();
        moves.add("p2c3c");/*w*/ moves.add("p7a6a");//b
        moves.add("q1d4a");/*w*/ moves.add("p7d6d");//b
        return moves.iterator();
    }

    private static Iterator<String> testKingMovementLimitations() {
        List<String> moves = new ArrayList<>();
        moves.add("p2e3e");/*w*/ moves.add("p7e6e");//b
        moves.add("k1e2e");/*w*/ moves.add("k8e7e");//b
        moves.add("k2e3f");/*w*/ moves.add("k7e6f");//b
        moves.add("k3f4f");/*w*/ moves.add("k6f5f");//b
        return moves.iterator();
    }

    private static Iterator<String> testFalseCheckMate() {
        List<String> moves = new ArrayList<>();
        moves.add("p2e3e");/*w*/ moves.add("p7e5e");//b
        moves.add("q1d3f");/*w*/ moves.add("q8d7e");//b
        moves.add("b1f4c");/*w*/ moves.add("p7a5a");//b
        moves.add("q3f7f");/*w*/ moves.add("q7e8d");//b
                                 moves.add("k8e8d");//b
        moves.add("q7f8f");/*w*/ moves.add("q7e8e");//b
        moves.add("b4c7f");/*w*/ moves.add("n8g7e");//b
        moves.add("q8f8h");/*w*/ moves.add("p7b6b");//b
        moves.add("q8h8e");/*w*/ moves.add("p5e4e");//b
        return moves.iterator();
    }

}
