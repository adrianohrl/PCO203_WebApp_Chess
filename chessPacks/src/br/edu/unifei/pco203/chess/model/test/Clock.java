/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model.test;

import br.edu.unifei.pco203.chess.model.Game;
import br.edu.unifei.pco203.chess.model.Player;
import java.io.Serializable;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author adriano
 */
public class Clock implements Serializable{
    
    private StopWatch whiteWatch = new StopWatch();
    private StopWatch blackWatch = new StopWatch();
    private Player white;
    private Player black;
    private Game game;
    
    public Clock(Player white, Player black, Game game) {
        this.white = white;
        this.black = black;
        this.game = game;
        if (game.isPaused()) {
            setUpPausedGame();
        }
        else if (!game.isFinished()) {
            startUpGame();
        }
    }
    
    private void startUpGame() {
        blackWatch.start();
        blackWatch.suspend();
        whiteWatch.start();
    }
    
    private void setUpPausedGame() {////////////////////////////////
        if (game.isWhiteTurn()) {
            
        } else {
            
        }
    }
    
    public void toggle() {
        if (!game.isWhiteTurn()) {
            whiteWatch.suspend();
            blackWatch.resume();
        } else {
            blackWatch.suspend();
            whiteWatch.resume();
        }
    }
    
    public void pauseGame() {
        whiteWatch.suspend();
        blackWatch.suspend();
    }
    
    public void gameOver() {
        whiteWatch.stop();
        blackWatch.stop();
    }

    public StopWatch getWhiteWatch() {
        return whiteWatch;
    }

    public void setWhiteWatch(StopWatch whiteWatch) {
        this.whiteWatch = whiteWatch;
    }

    public StopWatch getBlackWatch() {
        return blackWatch;
    }

    public void setBlackWatch(StopWatch blackWatch) {
        this.blackWatch = blackWatch;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
}
