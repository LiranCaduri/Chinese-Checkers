/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;


import UI.MainJPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**            Piece
 *--------------------------------------
 * @author lirancaduri
 * 
 * class Piece - Represent a game piece.
 *--------------------------------------
 */
public class Piece {
    // Constants
    public static final int PIECE_SIZE = 62; // size of a piece on the Board
    // Variables
    Image image; // image of piece
    int col, row; // index of a piece
    String color; // color of a piece
    
   /*--------------------------------------------------------------------------
    General : The class constractor.
    
    Input : row - the row number of the piece on the board , 
    col - the colomn number of the piece on the board , color - color of the piece.
    
    Output : instance of the class.
    --------------------------------------------------------------------------*/
    public Piece(int row ,int col ,String color) { // Constractor of the Class
        if (color.equals("red")) //choose a image by the color
            image = Toolkit.getDefaultToolkit().getImage("Resources/pieceRed.png");
        else
            image = Toolkit.getDefaultToolkit().getImage("Resources/pieceBrown.png");
        
        this.col= col;
        this.row =row;
        this.color=color;
    }

    
    public Image getImage() { // get a Image of the reference piece
        return image;
    }

    public void setImage(Image image) { // set a image of a piece
        this.image = image;
    }

    public int getCol() { // get the colomn number of the a piece | x 
        return col;
    }

    public void setCol(int col) { // set a new colomn for reference piece
        this.col = col;
    }

    public int getRow() { // get the row number of the a piece | y 
        return row;
    }

    public void setRow(int row) { // set a new colomn for reference piece
        this.row = row;
    }
    
    public String getColor() { // get the color of a piece 
        return color;
    }

    public void setColor(String color) { // set a color for a reference piece
        this.color = color;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: Draw
    
    General : the function draw the piece on the board.
    
    Input : graphics - the graghics of the frame that the drawing will be on, jPanel - the panel that the drawing will be on .
    
    Output : a draw of the piece on the board .
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    public void Draw (Graphics graphics,MainJPanel jPanel){ // draw function of the the piece
       graphics.drawImage(image, col*Board.CELL_SIZE/2, row*Board.CELL_SIZE/2, PIECE_SIZE,PIECE_SIZE, jPanel);
    }   
}
