/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.bean;

import br.edu.unifei.pco203.chess.control.dao.BoardDAO;
import br.edu.unifei.pco203.chess.control.dao.DataSource;
import br.edu.unifei.pco203.chess.model.Board;
import br.edu.unifei.pco203.chess.model.Game;
import br.edu.unifei.pco203.chess.model.GameException;
import br.edu.unifei.pco203.chess.model.Piece;
import br.edu.unifei.pco203.chess.model.SetOfPieces;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

/**
 *
 * @author adriano
 */
@ManagedBean
@SessionScoped
public class BoardBean implements Serializable {

    private final EntityManager em = DataSource.createEntityManager();
    private final BoardDAO boardDAO = new BoardDAO(em);
    private List<PieceBean> whitePieces = new ArrayList<>();
    private List<PieceBean> blackPieces = new ArrayList<>();
    private List<Piece> pieces = new ArrayList();
    private Board board = new Board();
    private GameBean gameBean;
    private String txt;
    private boolean autoSubmit;

    public BoardBean() {

    }

    public BoardBean(GameBean gameBean) {
        this.gameBean = gameBean;
        board = gameBean.getGame().getBoard();
        SetOfPieces whiteSet = board.getWhiteSet();
        for (Piece piece : whiteSet.getPieces()) {
            PieceBean pieceBean = PieceBean.getInstance(piece);
            whitePieces.add(pieceBean);
            pieces.add(piece);
        }
        SetOfPieces blackSet = board.getBlackSet();
        for (Piece piece : blackSet.getPieces()) {
            PieceBean pieceBean = PieceBean.getInstance(piece);
            blackPieces.add(pieceBean);
            pieces.add(piece);
        }
    }

    public List<String> completeText(String code) {
        gameBean.setMovementCode(code);
        List<String> results = new ArrayList<>();
        if (code.length() > 5) {
            gameBean.setMovementCode(code.substring(0, 4));
            return results;
        } else if (code.length() == 5 && autoSubmit) {
            gameBean.update();
        } else if (code.length() >= 3) {
            return results;
        }
        List<Piece> foundPieces;
        SetOfPieces set = gameBean.getGame().getSetTurn();
        try {
            switch (Character.toLowerCase(code.charAt(0))) {
                case 'b':
                    foundPieces = set.getBishopPieces();
                    break;
                case 'k':
                    foundPieces = set.getKingPieces();
                    break;
                case 'n':
                    foundPieces = set.getKnightPieces();
                    break;
                case 'p':
                    foundPieces = set.getPawnPieces();
                    break;
                case 'q':
                    foundPieces = set.getQueenPieces();
                    break;
                case 'r':
                    foundPieces = set.getRookPieces();
                    break;
                default:
                    gameBean.setMovementCode("");
                    return results;
            }
        } catch (GameException e) {
            gameBean.setMovementCode("");
            return results;
        }
        for (Piece piece : foundPieces) {
            String suggestedCode = null;
            if (code.length() == 1) {
                suggestedCode = code.charAt(0) + "" + piece.getRank() + "" + piece.getFile();
            } else {
                char c = code.charAt(1);
                if (c >= '1' && c <= '8' && c == piece.getRank()) {
                    suggestedCode = code.charAt(0) + "" + c + "" + piece.getFile();
                } else if (c >= 'a' && c <= 'h' && c == piece.getFile()) {
                    suggestedCode = code.charAt(0) + "" + piece.getRank() + "" + c;
                }
            }
            if (suggestedCode != null) {
                results.add(suggestedCode.toLowerCase());
            }
        }
        return results;
    }

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

    public List<PieceBean> getWhitePieces() {
        return whitePieces;
    }

    public void setWhitePieces(List<PieceBean> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public List<PieceBean> getBlackPieces() {
        return blackPieces;
    }

    public void setBlackPieces(List<PieceBean> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public GameBean getGameBean() {
        return gameBean;
    }

    public void setGameBean(GameBean gameBean) {
        this.gameBean = gameBean;
    }

    public boolean isAutoSubmit() {
        return autoSubmit;
    }

    public void setAutoSubmit(boolean autoSubmit) {
        this.autoSubmit = autoSubmit;
    }

}
