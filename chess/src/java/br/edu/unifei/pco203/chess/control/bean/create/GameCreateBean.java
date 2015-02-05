/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.GameDAO;
import br.edu.unifei.pco203.chess.model.Game;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@RequestScoped
public class GameCreateBean {

    private final EntityManager em = DataSource.createEntityManager();
    private final GameDAO gameDAO = new GameDAO(em);
    private Game game = new Game();

    public String create() {
        gameDAO.create(game);
        return "/index";
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
