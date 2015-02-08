/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.Knight;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class KnightBean extends PieceBean<Knight> implements Serializable {

    public KnightBean() {
        super();
        super.setPiece(new Knight());
    }
    
    public KnightBean(Knight knight) {
        super(knight);
    }
    
    public static KnightBean getInstance(Knight knight) {
        return new KnightBean(knight);
    }
    
}
