/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.update;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.GameDAO;
import br.edu.unifei.pco203.chess.control.dao.PlayerDAO;
import br.edu.unifei.pco203.chess.model.Game;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@ViewScoped
public class GameUpdateBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final GameDAO gameDAO = new GameDAO(em);
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private List<String> playersList = playerDAO.findAllNames();
    private String selectedWhitePlayer;
    private String selectedBlackPlayer;
    private String selectedWinnerPlayer;
    private Game game;
    private Calendar selectedGame;
    private List<Calendar> gamesList = gameDAO.findAllUnfinished();
    
    public String update() {
        
        return "/index";
    }
    
}
