/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author adriano
 */
@Entity
public class Board implements Serializable {

    @Id
    @GeneratedValue
    private int code;
    @OneToOne
    private SetOfPieces whiteSet;
    @OneToOne
    private SetOfPieces blackSet;

    public Board() {
        
    }

    public void update() {

    }

    public void clean() {

    }
    
    public void setup() {
        whiteSet = SetOfPieces.getWhiteSet(this);
        blackSet = SetOfPieces.getBlackSet(this);
    }

    @Override
    public String toString() {
        return "Board:\n" + whiteSet + blackSet;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SetOfPieces getWhiteSet() {
        return whiteSet;
    }

    public void setWhiteSet(SetOfPieces whiteSet) {
        this.whiteSet = whiteSet;
    }

    public SetOfPieces getBlackSet() {
        return blackSet;
    }

    public void setBlackSet(SetOfPieces blackSet) {
        this.blackSet = blackSet;
    }

}
