/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.PlayerDAO;
import br.edu.unifei.pco203.chess.model.Player;
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
public class PlayerUpdateBean {
    
    private final EntityManager em = DataSource.createEntityManager();
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private List<String> playersList = playerDAO.findAllNames();
    private Player player;
    private String selectedPlayer;
    private List<Calendar> selectedPlayedGames;
    
    
}
