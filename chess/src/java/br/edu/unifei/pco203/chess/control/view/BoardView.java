/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.control.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

/**
 *
 * @author adriano
 */
@ManagedBean
@ViewScoped
public class BoardView implements Serializable {
     
    private DashboardModel model;
     
    @PostConstruct
    public void init() {
        model = new DefaultDashboardModel();
        DashboardColumn fileA = new DefaultDashboardColumn();
        DashboardColumn fileB = new DefaultDashboardColumn();
        DashboardColumn fileC = new DefaultDashboardColumn();
        DashboardColumn fileD = new DefaultDashboardColumn();
        DashboardColumn fileE = new DefaultDashboardColumn();
        DashboardColumn fileF = new DefaultDashboardColumn();
        DashboardColumn fileG = new DefaultDashboardColumn();
        DashboardColumn fileH = new DefaultDashboardColumn();
         
        fileA.addWidget("rookA");
        fileA.addWidget("pawnA");
         
        fileB.addWidget("knightB");
        fileB.addWidget("pawnB");
         
        fileC.addWidget("bishopC");
        fileC.addWidget("pawnC");
        
        fileD.addWidget("queenD");
        fileD.addWidget("pawnD");
         
        fileE.addWidget("kingE");
        fileE.addWidget("pawnE");
         
        fileF.addWidget("bishopF");
        fileF.addWidget("pawnF");
         
        fileG.addWidget("knightG");
        fileG.addWidget("pawnG");
 
        fileH.addWidget("rookH");
        fileH.addWidget("pawnH");
        
        model.addColumn(fileA);
        model.addColumn(fileB);
        model.addColumn(fileC);
        model.addColumn(fileD);
        model.addColumn(fileE);
        model.addColumn(fileF);
        model.addColumn(fileG);
        model.addColumn(fileH);
    }
    
    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
         
        addMessage(message);
    }
     
    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
         
        addMessage(message);
    }
     
    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public DashboardModel getModel() {
        return model;
    }
    
}
