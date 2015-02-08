/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.Queen;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class QueenBean extends PieceBean<Queen> implements Serializable {

    public QueenBean() {
        super();
        super.setPiece(new Queen());
    }
    
    public QueenBean(Queen queen) {
        super(queen);
    }
    
    public static QueenBean getInstance(Queen queen) {
        return new QueenBean(queen);
    }
    
}
