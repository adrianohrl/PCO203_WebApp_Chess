/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model.test;

import javax.persistence.EntityManager;
import br.edu.unifei.pco203.chess.control.dao.*;
import br.edu.unifei.pco203.chess.model.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author adriano
 */
public class AppIns {
    
    public static void main(String[] args) throws GameException {
        
        EntityManager em = DataSource.createEntityManager();

        GameDAO gDAO = new GameDAO(em);
        Game game1 = new Game();
        game1.setEndDate(new GregorianCalendar());
        gDAO.create(game1);
        Game game2 = new Game();
        game2.setStartDate(new GregorianCalendar());
        gDAO.create(game2);
        Game game3 = new Game();
        game3.setStartDate(new GregorianCalendar());
        game3.setEndDate(new GregorianCalendar());
        gDAO.create(game3);
        
        List<Game> playedGames = new ArrayList<>();
        playedGames.add(game1);
        playedGames.add(game2);
        playedGames.add(game3);
        
        Player white = AppIns.createPlayer("AdrianoHRL");
        white.setPlayedGames(playedGames);
        Player black = AppIns.createPlayer("Bot");
        
        Game game = new Game(white, black);
        white.setLastGame(game);
        black.setLastGame(game);
        game.getStarted();
        
        System.out.println("Let's play chess!!!");
        Pawn whitePawn = game.getBoard().getWhiteSet().getPawn('e');
        Pawn blackPawn = game.getBoard().getBlackSet().getPawn('c');
        for (int i = 0; i < 4; i++) {
            game.move(whitePawn, (char) (whitePawn.getRank() + 1), whitePawn.getFile());
            game.move(blackPawn, (char) (blackPawn.getRank() - 1), blackPawn.getFile());
        }
        
        gDAO.createFullfilledGame(game);
        
        AppIns.promotePawn(game.getBoard().getBlackSet(), 'f', new Queen());
        game.checkMate();        
        System.out.println(game);
        
        System.out.println("******************************");
        for (Game playedGame : playedGames) {
            System.out.println(playedGame);
        }
        
        System.out.println("******************************");
        game1.setWinner(white);
        game2.setWinner(black);
        gDAO.update(game1);
        gDAO.update(game2);
        PlayerDAO pDAO = new PlayerDAO(em);
        System.out.println(pDAO.findAllWonGames(white));
        
        em.close();
        System.exit(0);
        
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
    
    private static void promotePawn(SetOfPieces set, char startFile, Piece promotedPiece) throws GameException {
        EntityManager em = DataSource.createEntityManager();
        DAO pieceDAO = new PieceDAO(em);
        pieceDAO.create(promotedPiece);
        set.promotePawn(startFile, promotedPiece);
        SetOfPiecesDAO setDAO = new SetOfPiecesDAO(em);
        setDAO.update(set);
    }
    
}
