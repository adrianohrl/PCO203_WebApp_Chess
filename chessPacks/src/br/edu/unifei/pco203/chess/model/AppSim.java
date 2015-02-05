/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.GameDAO;
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

    public static void main(String[] args) throws GameException {

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the white player name: ");
        String whiteName = scan.nextLine();
        Player white = AppSim.createPlayer(whiteName);

        System.out.println("Enter the black player name: ");
        String blackName = scan.nextLine();
        Player black = AppSim.createPlayer(blackName);

        Game game = new Game(white, black);
        game.getStarted();
        Clock clock = new Clock(white, black, game);
        Board board = game.getBoard();

        AppSim.displayMenu();
        System.out.println("So, let's play chess!!!");
        System.out.println("---------------------------------");
        AppSim.printBoard(board);

        Iterator<String> it = AppSim.testCastling();
        while (!game.isFinished()) {
            SetOfPieces set;
            Player player;
            if (game.isWhiteTurn()) {
                player = white;
                set = board.getWhiteSet();
            } else {
                player = black;
                set = board.getBlackSet();
            }
            System.out.println(player.getName() + ", enter the next movement: ");
            String movement = it.next();//scan.nextLine();
            try {
                Piece piece = Movement.processCode(set, movement);
                char desiredRank;
                char desiredFile;
                if (movement.length() == 4) {
                    desiredRank = movement.charAt(2);
                    desiredFile = movement.charAt(3);
                } else if (movement.length() == 5) {
                    desiredRank = movement.charAt(3);
                    desiredFile = movement.charAt(4);
                } else {
                    continue;
                }
                game.move(piece, desiredRank, desiredFile);
                clock.toggle();
            } catch (GameException e) {
                System.out.println(e.getMessage());
            }
            AppSim.printBoard(board);
            if (!it.hasNext()) {
                game.checkMate();
                clock.gameOver();
            }
        }
        System.out.println("Game Over!!!");
        System.out.println("-------------------- Movements --------------------");
        
        EntityManager em = DataSource.createEntityManager();
        GameDAO gameDAO = new GameDAO(em);
        gameDAO.createFullfilledGame(game);
        AppSim.displayMovements(board);
        
        em.close();
        DataSource.closeEntityManagerFactory();
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
        moves.add("ng3f");/*w*/ moves.add("nb6c");//b
        moves.add("pg3g");/*w*/ moves.add("pd6d");//b
        moves.add("bf2g");/*w*/ moves.add("bc3h");//b
        moves.add("pe4e");/*w*/ moves.add("qd7d");//b
        moves.add("k12e");/*w*/ moves.add("ra8d");//b
        moves.add("k21e");/*w*/ moves.add("rd8a");//b
        moves.add("k11c");//w
        moves.add("k11g");//w
        moves.add("pa3a");/*w*/ moves.add("k88g");//b
        moves.add("k88c");//b
        return moves.iterator();
    }

}
