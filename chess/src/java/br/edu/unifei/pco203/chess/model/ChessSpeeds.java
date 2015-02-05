/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.pco203.chess.model;

/**
 *
 * @author adriano
 */
public enum ChessSpeeds {
    
    RAPID, // 10 to 60 minutes per side (10 seconds increment)
    BLITZ, // 10 minutes or less per side (2 seconds increment)
    BULLET, //1 to 3 minutes per side (1 second increment)
    LIGHTNING, // 1 minute game (a half second increment)
    ARMAGEDON // 6 minutes for white and 5 minutes for black (1 second increment)
    
}
