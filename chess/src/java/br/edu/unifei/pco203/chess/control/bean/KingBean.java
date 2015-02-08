/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.King;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class KingBean extends PieceBean<King> implements Serializable {

    public KingBean() {
        super();
        super.setPiece(new King());
    }
    
    public KingBean(King king) {
        super(king);
    }
    
    public static KingBean getInstance(King king) {
        return new KingBean(king);
    }
    
}
