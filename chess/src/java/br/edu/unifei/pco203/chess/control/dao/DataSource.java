/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author adriano
 */
public class DataSource {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("chessPU");

    private DataSource() {

    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

}
