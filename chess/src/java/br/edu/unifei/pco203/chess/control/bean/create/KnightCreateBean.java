/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.model.Knight;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@RequestScoped
public class KnightCreateBean extends PieceCreateBean<Knight> {

    public KnightCreateBean() {
        super();
        super.setPiece(new Knight());
    }
    
}
