/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Board;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
public class BoardDAO extends DAO<Board, Integer> {

    public BoardDAO(EntityManager em) {
        super(em);
    }
    
    protected void creatingFullfilledGame(Board board) throws DAOException {
        if (board == null) {
            throw new DAOException("Board must not be null!!!");
        }
        em.persist(board);
        SetOfPiecesDAO setDAO = new SetOfPiecesDAO(em);
        setDAO.creatingFullfilledGame(board.getWhiteSet());
        setDAO.creatingFullfilledGame(board.getBlackSet());
        em.merge(board);
    }
    
}
