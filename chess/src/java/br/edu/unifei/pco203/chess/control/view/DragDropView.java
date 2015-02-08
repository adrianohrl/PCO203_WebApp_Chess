/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
 
/**
 *
 * @author adriano
 */
@ManagedBean(name="boardView")
@ViewScoped
public class DragDropView implements Serializable {
    
    private List<String> ranks = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    
    public DragDropView() {
        ranks = new ArrayList<>();
        files = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Integer px = (i + 1) * 50;
            ranks.add(px.toString());
            files.add(px.toString());
        }
    }
    
    @PostConstruct
    public void init() {
        ranks = new ArrayList<>();
        files = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Integer px = (i + 1) * 50;
            ranks.add(px.toString());
            files.add(px.toString());
        }
    }

    public List<String> getRanks() {
        return ranks;
    }

    public void setRanks(List<String> ranks) {
        this.ranks = ranks;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
    
}
