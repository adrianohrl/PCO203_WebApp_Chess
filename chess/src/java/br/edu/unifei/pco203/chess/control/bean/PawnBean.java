/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.Pawn;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class PawnBean extends PieceBean<Pawn> implements Serializable {

    public PawnBean() {
        super();
        super.setPiece(new Pawn());
    }
    
    public PawnBean(Pawn pawn, String scope, boolean disabled) {
        super(pawn, scope, disabled);
    }
    
    public static PawnBean getInstance(Pawn pawn, String scope, boolean disabled) {
        return new PawnBean(pawn, scope, disabled);
    }
    
}
