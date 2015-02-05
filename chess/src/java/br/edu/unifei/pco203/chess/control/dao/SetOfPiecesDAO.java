/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Movement;
import br.edu.unifei.pco203.chess.model.Piece;
import br.edu.unifei.pco203.chess.model.SetOfPieces;
import java.util.List;
import javax.persistence.EntityManager;
/**
 *
 * @author adriano
 */
public class SetOfPiecesDAO extends DAO<SetOfPieces, Integer> {

    public SetOfPiecesDAO(EntityManager em) {
        super(em);
    }
    
    protected void creatingFullfilledGame(SetOfPieces set) throws DAOException {
        if (set == null) {
            throw new DAOException("Set of Pieces must not be null!!!");
        }
        em.persist(set);
        PieceDAO pieceDAO = new PieceDAO(em);
        List<Piece> pieces = set.getPieces();
        for (Piece piece : pieces) {
            pieceDAO.creatingFullfilledGame(piece);
        }
        MovementDAO movementDAO = new MovementDAO(em);
        List<Movement> movements = set.getMovements();
        for (Movement movement : movements) {
            movementDAO.creatingFullfilledGame(movement);
        }
        em.merge(set);
    }
    
}
