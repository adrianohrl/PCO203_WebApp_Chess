/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean.create;

import br.edu.unifei.pco203.chess.control.dao.BoardDAO;
import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.model.Board;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@RequestScoped
public class BoardCreateBean {

    private final EntityManager em = DataSource.createEntityManager();
    private final BoardDAO boardDAO = new BoardDAO(em);
    private Board board = new Board();

    public String create() {
        boardDAO.create(board);
        return "/index";
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
