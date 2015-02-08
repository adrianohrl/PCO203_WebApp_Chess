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
import br.edu.unifei.pco203.chess.model.GameException;
import br.edu.unifei.pco203.chess.model.Movement;
import br.edu.unifei.pco203.chess.model.Player;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class GameBean implements Serializable {

    private final EntityManager em = DataSource.createEntityManager();
    private final GameDAO gameDAO = new GameDAO(em);
    private final PlayerDAO playerDAO = new PlayerDAO(em);
    private BoardBean boardBean = new BoardBean();
    private Game game = new Game();
    private List<String> playersList = playerDAO.findAllNames();
    private String selectedWhitePlayer;
    private String selectedBlackPlayer;
    private String playerTurn;
    private String movementCode;

    public String clear() {
        game = new Game();
        playersList = playerDAO.findAllNames();
        selectedWhitePlayer = null;
        selectedBlackPlayer = null;
        playerTurn = null;
        return "/index";
    }

    public String start() {
        if (selectedWhitePlayer == null || selectedBlackPlayer == null) {
            return "";
        }
        Player white = playerDAO.createPlayer(selectedWhitePlayer);
        Player black = playerDAO.createPlayer(selectedBlackPlayer);
        game = new Game(white, black);
        game.getStarted();
        boardBean = new BoardBean(this);
        playerTurn = game.getPlayerTurn().getName();
        //gameDAO.createFullfilledGame(game);
        return "/board/play";
    }

    public String checkMate() {
        game.checkMate();
        gameDAO.createFullfilledGame(game);
        return "/index";
    }

    public String update() {
        try {
            Movement movement = Movement.process(movementCode, game);
            game.move(movement);
        } catch (GameException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Wrong Movement Code", e.getMessage()));
            movementCode = "";
            return "";
        }
        playerTurn = game.getPlayerTurn().getName();
        movementCode = "";
        boardBean = new BoardBean(this);
        return "";
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

    public BoardBean getBoardBean() {
        return boardBean;
    }

    public void setBoardBean(BoardBean boardBean) {
        this.boardBean = boardBean;
    }

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public String getMovementCode() {
        return movementCode;
    }

    public void setMovementCode(String movementCode) {
        this.movementCode = movementCode;
    }

}
