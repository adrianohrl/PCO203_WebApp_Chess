/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.PieceDAO;
import br.edu.unifei.pco203.chess.model.*;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 * @param <P>
 */
@ManagedBean
@SessionScoped
public abstract class PieceBean <P extends Piece> implements Serializable {

    private final EntityManager em = DataSource.createEntityManager();
    private final PieceDAO pieceDAO = new PieceDAO(em);
    private final Class<P> clazz = (Class<P>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private P piece;
    
    public PieceBean() {
        
    }
    
    public PieceBean(P piece) {
        this.piece = piece;
    }

    public String create() {
        pieceDAO.create(piece);
        return "/index.xhtml";
    }
    
    public String color() {
        if (piece.isWhiteSet()) {
            return "white";
        } else {
            return "black";
        }
    }
    
    public String type() {
        return clazz.getSimpleName().toLowerCase();
    }
    
    public String scope() {
        return "valid";
    }
    
    public String top() {
        Integer rank = ('8' - piece.getRank()) * 50;
        return rank.toString();
    }
    
    public String left() {
        Integer file = (piece.getFile() - 'a') * 50;
        return file.toString();
    }
    
    public static <P> PieceBean getInstance(P piece) {
        PieceBean pieceBean = null;
        if (piece instanceof Pawn) {
            pieceBean = PawnBean.getInstance((Pawn) piece);
        } else if (piece instanceof Rook) {
            pieceBean = RookBean.getInstance((Rook) piece);
        } else if (piece instanceof Knight) {
            pieceBean = KnightBean.getInstance((Knight) piece);
        } else if (piece instanceof Bishop) {
            pieceBean = BishopBean.getInstance((Bishop) piece);
        } else if (piece instanceof Queen) {
            pieceBean = QueenBean.getInstance((Queen) piece);
        } else if (piece instanceof King) {
            pieceBean = KingBean.getInstance((King) piece);
        }
        return pieceBean;
    }

    public P getPiece() {
        return piece;
    }

    public void setPiece(P piece) {
        this.piece = piece;
    }

}
