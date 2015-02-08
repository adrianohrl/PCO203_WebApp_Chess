/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.SetOfPiecesDAO;
import br.edu.unifei.pco203.chess.model.SetOfPieces;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class SetOfPiecesBean implements Serializable {

    private final EntityManager em = DataSource.createEntityManager();
    private final SetOfPiecesDAO setDAO = new SetOfPiecesDAO(em);
    private SetOfPieces set = new SetOfPieces();

    public String create() {
        setDAO.create(set);
        return "/index";
    }

    public SetOfPieces getSet() {
        return set;
    }

    public void setSet(SetOfPieces set) {
        this.set = set;
    }

}
