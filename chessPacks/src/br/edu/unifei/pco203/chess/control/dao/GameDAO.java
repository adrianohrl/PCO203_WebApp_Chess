/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Game;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
public class GameDAO extends DAO<Game, Integer> {

    public GameDAO(EntityManager em) {
        super(em);
    }
    
    public void createFullfilledGame(Game game) throws DAOException {
        if (game == null) {
            throw new DAOException("Game must not be null!!!");
        }
        em.getTransaction().begin();
        em.persist(game);
        PlayerDAO playerDAO = new PlayerDAO(em);
        playerDAO.creatingFullfilledGame(game.getWhite());
        playerDAO.creatingFullfilledGame(game.getBlack());
        playerDAO.creatingFullfilledGame(game.getWinner());
        BoardDAO boardDAO = new BoardDAO(em);
        boardDAO.creatingFullfilledGame(game.getBoard());
        em.merge(game);
        em.getTransaction().commit();
    }
    
    protected void creatingFullfilledGame(Game game) throws DAOException {
        em.merge(game);
    }
    
    public List<Calendar> findAllUnfinished() {
        return em.createQuery("SELECT g.startDate FROM Game g WHERE g.endDate IS NULL").getResultList();
    }
    
}
