/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Piece;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 * @param <P>
 */
public class PieceDAO<P extends Piece> extends DAO<P, Integer> {

    public PieceDAO(EntityManager em) {
        super(em);
    }
    
    protected void creatingFullfilledGame(P piece) throws DAOException {
        if (piece == null) {
            throw new DAOException("Piece must not be null!!!");
        }
        em.persist(piece);
    }
    

}
