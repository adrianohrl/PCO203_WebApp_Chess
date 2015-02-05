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
 */
public class KnightDAO extends PieceDAO<Piece> {

    public KnightDAO(EntityManager em) {
        super(em);
    }
    
}
