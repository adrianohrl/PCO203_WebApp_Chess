/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.control.dao.GameDAO;
import br.edu.unifei.pco203.chess.control.dao.PlayerDAO;
import br.edu.unifei.pco203.chess.model.Game;
import br.edu.unifei.pco203.chess.model.Player;
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
public class GameBean {

    private final EntityManager em = DataSource.createEntityManager();
    private final GameDAO gameDAO = new GameDAO(em);
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private Game game = new Game();
    private List<String> playersList = playerDAO.findAllNames();
    private String selectedWhitePlayer;
    private String selectedBlackPlayer;
    
    public String start() {
        if (selectedWhitePlayer == null || selectedBlackPlayer == null) {
            return "";
        }
        Player white = playerDAO.createPlayer(selectedWhitePlayer);
        Player black = playerDAO.createPlayer(selectedBlackPlayer);
        game = new Game(white, black);
        game.getStarted();
        gameDAO.createFullfilledGame(game);
        return "/board/play";
    }
    
    public String checkMate() {
        if (game == null) {
            return "";
        }
        game.checkMate();
        gameDAO.update(game);
        return "/index";
    }
    
    public String update() {
        
        return "/index";
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<String> playersList) {
        this.playersList = playersList;
    }

    public String getSelectedWhitePlayer() {
        return selectedWhitePlayer;
    }

    public void setSelectedWhitePlayer(String selectedWhitePlayer) {
        this.selectedWhitePlayer = selectedWhitePlayer;
    }

    public String getSelectedBlackPlayer() {
        return selectedBlackPlayer;
    }

    public void setSelectedBlackPlayer(String selectedBlackPlayer) {
        this.selectedBlackPlayer = selectedBlackPlayer;
    }
    
}
