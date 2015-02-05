/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.PieceDAO;
import br.edu.unifei.pco203.chess.model.Piece;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 * @param <P>
 */
@ManagedBean
@RequestScoped
public abstract class PieceCreateBean <P extends Piece> {

    private final EntityManager em = DataSource.createEntityManager();
    private final PieceDAO pieceDAO = new PieceDAO(em);
    private P piece;

    public String create() {
        pieceDAO.create(piece);
        return "/index.xhtml";
    }

    public P getPiece() {
        return piece;
    }

    public void setPiece(P piece) {
        this.piece = piece;
    }

}
