/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class Slot implements Serializable {
    
    private char rank;
    private char file;
    private String scope;
    private String top;
    private String left;
            
    public Slot() {
        
    }
    
    public Slot(char rank, char file) {
        this.rank = rank;
        this.file = file;
        Integer topValue = ('8'- rank) * 50;
        scope = "valid";
        top = topValue.toString();
        Integer leftValue = (file - 'a') * 50;
        left = leftValue.toString();
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public char getFile() {
        return file;
    }

    public void setFile(char file) {
        this.file = file;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }
    
}
