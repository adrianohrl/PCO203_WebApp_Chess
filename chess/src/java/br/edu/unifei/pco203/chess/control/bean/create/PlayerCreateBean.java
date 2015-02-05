/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.PlayerDAO;
import br.edu.unifei.pco203.chess.model.Player;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@RequestScoped
public class PlayerCreateBean {

    private final EntityManager em = DataSource.createEntityManager();
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private Player player = new Player();

    public String create() {
        playerDAO.create(player);
        return "/index";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
