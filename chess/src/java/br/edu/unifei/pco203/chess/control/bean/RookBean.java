/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.Rook;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class RookBean extends PieceBean<Rook> implements Serializable {

    public RookBean() {
        super();
        super.setPiece(new Rook());
    }
    
    public RookBean(Rook rook) {
        super(rook);
    }
    
    public static RookBean getInstance(Rook rook) {
        return new RookBean(rook);
    }
    
}
