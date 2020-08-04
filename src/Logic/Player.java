/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import UI.MainJPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**            Player
 *--------------------------------------
 * @author lirancaduri
 * 
 * class PlayerPlayer - Represent a Player in the game.
 *--------------------------------------
 */
public class Player {
    
    // Variables
    HashMap<Integer,Piece> pieces = new HashMap<Integer, Piece>(); // all the game pieces of a player
    String color; // color of player  
    List<Integer> teritory = new ArrayList<>(); //points of teritory
    /*--------------------------------------------------------------------------
    General : The class constractor.
    
    Input : startR - the start row index to init pieces  , endR - the end row index to init pieces, 
    startC - the start col index to init pieces, endC - the end col index to init pieces , color - the color of the player pieces .  
    
    Output : instance of the class.
    --------------------------------------------------------------------------*/
    public Player(int startR ,int endR , int startC ,int endC , String color) { // constractor
        // startR - start row for creating the pieces ,  startC - start colomn for creating the pieces
        // endR - End row for creating the pieces , endC - END colomn for creating the pieces
        this.color=color;
        
        for(int i = startR; i <= endR; i++){
            for(int j = startC ; j <= endC ;j++){
                pieces.put(new Integer (j*Board.N+i), new Piece(i,j, color)); // new piece
                teritory.add(j* Board.N +i); // saving index of teritory base 
            }
        }
    }
    
    public HashMap<Integer, Piece> getPieces() { // getting the game pieces of the player
        return pieces;
    }

    public String getColor() { // getting the player pieces color 
        return color;
    }

    public void setPieces(HashMap<Integer, Piece> pieces) { // setting the game pieces of the player
        this.pieces = pieces;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public void setPiece (int oldKey ,int key, Piece value){ // setting the index of a single piece
        if(pieces.containsKey(oldKey))
            pieces.remove(oldKey);
        pieces.put(key, value);       
    }

    public List<Integer> getTeritory() { // getting a list of the base of the player
        return teritory;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: Draw
    
    General : the function draw the game pieces of the player on the board.
    
    Input : graphics - the graghics of the frame that the drawing will be on, jPanel - the panel that the drawing will be on .
    
    Output : a draw of the game pieces on the board .
    
    Run Time : O(1) because the number of pieces is a constant (16) 
    --------------------------------------------------------------------------*/
    public void Draw (Graphics graphics, MainJPanel JPanel){ // draw the player game pieces
         for(Piece piece : pieces.values())
            piece.Draw(graphics,JPanel);
    }
} 

