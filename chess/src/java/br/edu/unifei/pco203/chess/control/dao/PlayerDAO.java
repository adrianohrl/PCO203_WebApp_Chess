/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import br.edu.unifei.pco203.chess.model.Game;
import br.edu.unifei.pco203.chess.model.Player;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
public class PlayerDAO extends DAO<Player, String> {

    public PlayerDAO(EntityManager em) {
        super(em);
    }

    protected void creatingFullfilledGame(Player player) throws DAOException {
        if (player != null && this.find(player.getName()) == null) {
            em.persist(player);
        }
    }
    
    public List<String> findAllNames() {
        return em.createQuery("SELECT p.name FROM Player p").getResultList();
    }
    
    public List<Game> findAllWonGame(Player player) {
        return em.createQuery("SELECT g FROM Game g WHERE g.winner.name = '" + player.getName() + "' ORDER BY g.startDate DESC").getResultList();
    }
    
    public Player createPlayer(String name) {
        Player player = find(name);
        if (player == null) {
            player = new Player(name);
        }
        return player;
    }
    
    /*public List<String> findAllMovements(Player player) {
        return em.createQuery("SELECT s.movement FROM").getResultList();
    }*/
    
}
