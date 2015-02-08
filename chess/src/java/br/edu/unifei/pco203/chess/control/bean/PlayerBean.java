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
import java.io.Serializable;
import java.util.ArrayList;
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
public class PlayerBean implements Serializable {

    private final EntityManager em = DataSource.createEntityManager();
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private final GameDAO gameDAO = new GameDAO(em);
    private Player player = new Player();
    private List<String> playersList = playerDAO.findAllNames();
    private String selectedPlayer;
    private List<Integer> selectedPlayedGames;

    public String create() {
        playerDAO.create(player);
        return "/index";
    }
    
    public String update() {
        player = playerDAO.find(selectedPlayer);
        List<Game> playedGames = new ArrayList<>();
        Game lastGame = null;
        for (Integer selectedGame : selectedPlayedGames) {
            Game game = gameDAO.find(selectedGame);
            if (game.hasPlayed(player)) {
                if (lastGame == null || game.compareTo(lastGame) > 0) {
                    lastGame = game;
                }
                playedGames.add(game);
            }
        }
        player.setPlayedGames(playedGames);
        player.setLastGame(lastGame);
        playerDAO.update(player);
        return "/index";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<String> playersList) {
        this.playersList = playersList;
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(String selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

    public List<Integer> getSelectedPlayedGames() {
        return selectedPlayedGames;
    }

    public void setSelectedPlayedGames(List<Integer> selectedPlayedGames) {
        this.selectedPlayedGames = selectedPlayedGames;
    }
}
