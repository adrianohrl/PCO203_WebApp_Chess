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
import br.edu.unifei.pco203.chess.model.King;
import br.edu.unifei.pco203.chess.model.Movement;
import br.edu.unifei.pco203.chess.model.Player;
import br.edu.unifei.pco203.chess.model.SetOfPieces;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import org.primefaces.event.DragDropEvent;

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
        gameDAO.createFullfilledGame(game);
        return "/board/play";
    }

    public void checkMate() {
        game.checkMate();
        gameDAO.update(game);
    }

    public String pause() {
        game.pause();
        gameDAO.update(game);
        return "/index";
    }

    private void addMovement(Movement movement) {
        MovementBean movementBean = new MovementBean();
        movementBean.setMovement(movement);
        movementBean.create();
        gameDAO.update(game);
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (game.isFinished()) {
            context.addMessage(null, new FacesMessage("Finished Game: ", game.getWinner().getName() + " won!!!"));
            return "/index";
        }
        try {
            Movement movement = Movement.process(movementCode, game);
            game.move(movement);
            addMovement(movement);
            SetOfPieces opponentSet = game.getBoard().getOpponentSet(movement.getPiece());
            King opponentKing = opponentSet.getKing();
            if (opponentKing.isCheckMate()) {
                checkMate();
                context.addMessage(null, new FacesMessage("Check Mate: ", game.getWinner().getName() + " wins!!!"));
                return "/index";
            } else if (opponentKing.isInCheck()) {
                context.addMessage(null, new FacesMessage("Check", game.getPlayerTurn().getName() + "'s king in check!!!"));
            }
        } catch (GameException e) {
            context.addMessage(null, new FacesMessage("Wrong Movement Code:", e.getMessage()));
            movementCode = "";
            return "";
        }
        playerTurn = game.getPlayerTurn().getName();
        movementCode = "";
        boardBean = new BoardBean(this);
        return "";
    }

    public String findGame() {
        if (selectedWhitePlayer == null || selectedBlackPlayer == null) {
            return "";
        }
        Player white = playerDAO.createPlayer(selectedWhitePlayer);
        Player black = playerDAO.createPlayer(selectedBlackPlayer);
        Game desiredGame = new Game(white, black);
        List<Game> playedGames = gameDAO.findGames(desiredGame);
        if (playedGames == null || playedGames.isEmpty()) {
            return "/index";
        }
        Game lastGame = playedGames.get(0);
        for (Game playedGame : playedGames) {
            if (playedGame != null && playedGame.compareTo(lastGame) > 0) {
                lastGame = playedGame;
            }
        }
        game = lastGame;
        boardBean = new BoardBean(this);
        playerTurn = game.getPlayerTurn().getName();
        return "/board/play";
    }

    public String onDrop(DragDropEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (event != null) {
            String dragId = event.getDragId();
            String dropId = event.getDropId();
            context.addMessage(null, new FacesMessage("Piece dropped!!!", "DragId: " + dragId + " DropId: " + dropId));
            
            String[] pieceIds = dragId.split(":");
            int pieceIndex = Integer.parseInt(pieceIds[2]);
            PieceBean piece = boardBean.getPieces().get(pieceIndex);
            
            String[] slotIds = dropId.split(":");
            int slotIndex = Integer.parseInt(slotIds[2]);
            Slot slot = boardBean.getSlots().get(slotIndex);
            
            movementCode = piece.getNotationCode() + "" + piece.getRank() + "" + piece.getFile() + "" + slot.getRank() + "" + slot.getFile();
            return update();
        }
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
