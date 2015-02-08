/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.model.Bishop;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class BishopBean extends PieceBean<Bishop> implements Serializable {

    public BishopBean() {
        super();
        super.setPiece(new Bishop());
    }
    
    public BishopBean(Bishop bishop) {
        super(bishop);
    }
    
    public static BishopBean getInstance(Bishop bishop) {
        return new BishopBean(bishop);
    }
    
}
