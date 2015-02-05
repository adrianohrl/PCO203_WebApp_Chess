/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author adriano
 */
@Entity
public class Player implements Serializable {

    @Id
    private String name;
    @ManyToOne
    private Game lastGame;
    @OneToMany
    private List<Game> playedGames = new ArrayList<>();

    public Player() {

    }

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public Player clone() throws CloneNotSupportedException {
        Player player = (Player) super.clone();
        player.setName(name);
        return player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getLastGame() {
        return lastGame;
    }

    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public List<Game> getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(List<Game> playedGames) {
        this.playedGames = playedGames;
    }

}
