/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.MovementDAO;
import br.edu.unifei.pco203.chess.model.Movement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@RequestScoped
public class MovementCreateBean {

    private final EntityManager em = DataSource.createEntityManager();
    private final MovementDAO movementDAO = new MovementDAO(em);
    private Movement movement = new Movement();

    public String create() {
        movementDAO.create(movement);
        return "/index";
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

}
