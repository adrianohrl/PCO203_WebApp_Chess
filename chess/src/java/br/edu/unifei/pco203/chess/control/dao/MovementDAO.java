/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Movement;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
public class MovementDAO extends DAO<Movement, Integer> {

    public MovementDAO(EntityManager em) {
        super(em);
    }
    
    protected void creatingFullfilledGame(Movement movement) throws DAOException {
        if (movement == null) {
            throw new DAOException("Movement must not be null!!!");
        }
        em.persist(movement);
    }

}
