/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.view;

import br.edu.unifei.pco203.chess.model.GameException;
import br.edu.unifei.pco203.chess.model.Piece;
import br.edu.unifei.pco203.chess.model.SetOfPieces;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author adriano
 */
@ManagedBean
public class AutoCompleteView {

    private String txt;
    private boolean manualSubmit;

    public List<String> completeText(String code) {
        List<String> results = new ArrayList<>();
        if (code.length() > 5) {
            txt = code.substring(0, 4);
            return results;
        } else if (code.length() > 3) {
            return results;
        }
        List<Piece> pieces;
        SetOfPieces set = new SetOfPieces();
        try {
            switch (Character.toLowerCase(code.charAt(0))) {
                case 'b':
                    pieces = set.getBishopPieces();
                    break;
                case 'k':
                    pieces = set.getKingPieces();
                    break;
                case 'n':
                    pieces = set.getKnightPieces();
                    break;
                case 'p':
                    pieces = set.getPawnPieces();
                    break;
                case 'q':
                    pieces = set.getQueenPieces();
                    break;
                case 'r':
                    pieces = set.getRookPieces();
                    break;
                default:
                    txt = "";
                    return results;
            }
        } catch (GameException e) {
            txt = "";
            return results;
        }
        for (Piece piece : pieces) {
            results.add(Character.toLowerCase(code.charAt(0)) + "" + piece.getRank() + "" + piece.getFile());
        }
        return results;
    }

    public void changeButtonVisibility() {

    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isManualSubmit() {
        return manualSubmit;
    }

    public void setManualSubmit(boolean manualSubmit) {
        this.manualSubmit = manualSubmit;
    }
}
