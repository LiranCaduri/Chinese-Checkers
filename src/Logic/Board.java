/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Logic;

import UI.InfoJPanel;
import UI.MainJPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;


/**            Board
 *--------------------------------------
 * @author lirancaduri
 * 
 * class Board - Represent the game Board.
 *--------------------------------------
 */
public class Board {
    // Constants
    public static final int N = 10; // number of rows and cols
    public static final int CALCULATE_SIZE_ON_CLICK = 70;// calculate number when getting a point from on click action
    public static final int CELL_SIZE = 146;// size of cell on board
    public static final int NUMֹֹֹֹ_OF_DIRECTION = 4;
    
    // Variables
    Image image;//image of board
    MainJPanel panel; // game panel
    Player [] players = new Player[2];// an array of players
    HashMap<Integer, Piece> locations = new HashMap<>();// possible location
    HashMap<Integer, Piece> locationsAI = new HashMap<>();// possible location for AI
    
    private final int [][] dirmat = {// direction matrix to simple step
        {-1, 0},//LEFT
        {1, 0},// RIGHT
        {0, -1},// DOWN
        {0, 1} // UP
    };
    private final int [][] longDirmat = { // direction matrix for jump
        {-2, 0}, // JUMP LEFT
        {2, 0}, // JUMP RIGHT
        {0, -2},// JUMP DOWN
        {0, 2} // JUMP UP
    };
    
    /*--------------------------------------------------------------------------
    General : The class constractor.
    
    Input : the game panel that the board is on it .
    
    Output : instance of the class.
    --------------------------------------------------------------------------*/
    public Board(MainJPanel mainJPanel) {
        image = Toolkit.getDefaultToolkit().getImage("Resources/newBoard.png"); // image of the board
        this.setPanel(mainJPanel);// set the panel
        
        players[0] = new Player(0, 3, 0, 3, "brown"); // create an brown player
        players[1] = new Player(N - 4, N - 1, N - 4, N - 1, "red"); // create an red player
        
    }
    
    public Player[] getPlayers() { // get the game player
        return players;
    }
    
    public Image getImage() { //get the board image
        return image;
    }
    
    public MainJPanel getPanel() { // get the game panel
        return panel;
    }
    
    public void setImage(Image image) { // set the board image
        this.image = image;
    }
    
    public void setPanel(MainJPanel panel) { // set the game panel
        this.panel = panel;
    }
    
    public Player getPlayer1() { // get the red player
        return players[1];
    }
    
    public Player getPlayer0() { // get the brown player
        return players[0];
    }
    
    public void setPlayers(Player[] players) { // set both game player
        this.players = players;
    }
    
    public void setPlayer0(Player player0) { // set the brown player
        this.players[0] = player0;
    }
    
    public void setPlayer1(Player player1) { // set the red player
        this.players[1] = player1;
    }
    
    public void setLocationsAI(HashMap<Integer, Piece> locationsAI) { // set the possible moves hashmap of the AI
        this.locationsAI = locationsAI;
    }
    
    public HashMap<Integer, Piece> getLocations() { // get the possible moves hashmap of the humen player
        return this.locations;
    }
    
    public HashMap<Integer, Piece> getLocationsAI() { // get the possible moves hashmap of the AI
        return this.locationsAI;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: addPiece
    
    General : the function adds a piece on the board (part of making a move).
    
    Input : x - the column index of the dest piece, y - the row index of the dest piece,
     redTurn - true or false is it the red player turn ? .
    
    Process : the function check if the cell is free and adds the new piece to the player. .
    
    Output : true or false if the adding of the piece works.
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    public boolean addPiece(int x, int y, boolean redTurn) {//move
        boolean success = false; // if succeed to add the dest piece  
        
        if (!(players[0].pieces.containsKey(x * N + y) || players[1].pieces.containsKey(x * N + y))) { //if there is no piece in the cell 
            success = true; //success adding the piece
            
            if (redTurn) { // to which player we need to add the piece  
                players[1].pieces.put(x * N + y, new Piece(y, x, "red"));
            }
            else if (!redTurn){
                players[0].pieces.put(x * N + y, new Piece(y, x, "Brown"));
            }  
        }      
        return success;  
    }
    
    /*--------------------------------------------------------------------------
    FuncName: removePiece
    
    General : the function removes a piece from the board (part of making a move).
    
    Input : x - the column index of the src piece, y - the row index of the src piece,
     redTurn - true or false is it the red player turn ? .
    
    Process : the function check which player is making a move and check if is contain the piece than remove the src piece.
    
    Output : removing the src piece .
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    public void removePiece(int x, int y, boolean redTurn) { //move
        //remove after second click
        
        if (redTurn) { // to which player we need to remove the piece
            if (players[1].pieces.containsKey(x * N + y)) { //double check if contain the piece
                players[1].pieces.remove(x * N + y); // if is contain remove it
            } else {
                panel.setSecondclick(!(panel.isSecondclick())); //turn the secondClick back | for getting a new click
            }
        } else {
            if (players[0].pieces.containsKey(x * N + y)) {//double check if contain the piece
                players[0].pieces.remove(x * N + y);// if is contain remove it
            } else {
                panel.setSecondclick(!(panel.isSecondclick()));//turn the secondClick back | for getting a new click
            }
        }
    }
    
    /*--------------------------------------------------------------------------
    FuncName: possibleLocations
    
    General : the function finds all the simple possible moves that the src piece can do (part of making a move).
    
    Input : index - the location of the src piece, AI - true or false is it the AI turn ? .
    
    Process : the function runs in a loop on al 4 directions up ,down ,right ,left 
    ,and check if the cell is free and not outside the board .
    
    Output : all the simple moves fo the src piece .
    
    Run Time : O(n) because longPossibleLocations run time is O(n).
    --------------------------------------------------------------------------*/
    public void possibleLocations(Point index, boolean AI) { //calculating possable location of moves and calling to longPossibleLocations that calculate jumps
        //init
        //dest.x * N + dest.y
        Point dest = new Point();
        
        for (int i = 0; i < NUMֹֹֹֹ_OF_DIRECTION; i++) {// 4 directions
            
            // set direction by Dirmat
            dest.setLocation(index.x + dirmat[i][1], index.y + dirmat[i][0]);
            
            if (!players[1].pieces.containsKey(dest.x * N + dest.y) && !players[0].pieces.containsKey(dest.x * N + dest.y) && IsLegal(dest.y, dest.x)) { 
                // if the cell not taken by the two players AND it's not outside the board 
                
                if (AI) {
                    // insert into correct table AI or Human
                    locationsAI.put(dest.x * N + dest.y, new Piece(dest.y, dest.x, "green"));
                } else {
                    locations.put(dest.x * N + dest.y, new Piece(dest.y, dest.x, "green"));
                }
            }
        }
        longPossibleLocations(index.x,index.y,AI); // recursive | checking jumps
    }
     
    /*--------------------------------------------------------------------------
    FuncName: longPossibleLocations
    
    General : the function finds all the multiple jump possible moves that the src piece can do (part of making a move).
    
    Input : col - the column index of the src piece, row - the row index of the src piece, AI - true or false is it the AI turn ? .
    
    Process : the function runs in a loop on al 4 directions up ,down ,right ,left 
    ,and check if the cell is free and not outside the board and if the possible location to move is not already added,
    than we check there is a piece in front of the selected piece and the cell is free, and adding the move into currect table.
    
    Output : all the multiple jumps possible moves for the src piece .
    
    Run Time : O(n)
    --------------------------------------------------------------------------*/
    public void longPossibleLocations(int col, int row, boolean AI) {
        //calculate jumps
        //init
        int curr_row, curr_col;
        for (int i = 0; i < NUMֹֹֹֹ_OF_DIRECTION; i++) { // 4 directions

            // jump direction by longDirmat
            curr_row = row + longDirmat[i][0];
            curr_col = col + longDirmat[i][1];
            
            if (!players[1].pieces.containsKey(curr_col * N + curr_row) && !players[0].pieces.containsKey(curr_col * N + curr_row)
                    && IsLegal(curr_row, curr_col)) { // free AND it's not outside the board
                
                if ((AI && !locationsAI.containsKey(curr_col * N + curr_row)) || (!AI && !locations.containsKey(curr_col * N + curr_row))){ 
                    // NOT in the hashtable of possiable location  OR  NOT in the hashtable of AI possiable location hashtable
                   
                    if (col == curr_col) { // if there is a piece in front of the selected piece
                        int minRow = Math.min(row, curr_row) + 1;// a row index of a piece that supposed to be in front of the selected piece
                        
                        if (anyContainsKey(minRow, col)) { //IF one of the player on cell
                            if (AI) { // for knowing to which table insert the value
                                locationsAI.put(curr_col * N + curr_row, new Piece(curr_row, curr_col, "green"));
                            } else {
                                locations.put(curr_col * N + curr_row, new Piece(curr_row, curr_col, "green"));
                            }
                            longPossibleLocations(curr_col, curr_row, AI);// again from the jump location
                        } 
                    }
                    else { // if there is a piece in front of the selected piece
                        int minCol = Math.min(col, curr_col) + 1;//a col index of a piece that supposed to be in front of the selected piece
                        
                        if (anyContainsKey(row, minCol)) {//IF one of the player on cell
                            if (AI) { // for knowing to which table insert the value
                                locationsAI.put(curr_col * N + curr_row, new Piece(curr_row, curr_col, "green"));
                            } else {
                                locations.put(curr_col * N + curr_row, new Piece(curr_row, curr_col, "green"));
                            }
                            longPossibleLocations(curr_col, curr_row, AI);// again from the jump possiable location
                        }
                    }
                }
            }
        }
    }
    
    /*--------------------------------------------------------------------------
    FuncName: anyContainsKey
    
    General : the function check if the cell is free.
    
    Input : col - the column index of the src piece, row - the row index of the src piece.
    
    Output : true or false if the cell is free .
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/ 
    public boolean anyContainsKey(int row, int col) {
        //if one of the two players has piece in a cell
        return players[0].pieces.containsKey(col * N + row) || players[1].pieces.containsKey(col * N + row);
    }
    
    /*--------------------------------------------------------------------------
    FuncName: IsLegal
    
    General : the function check if not outside the board.
    
    Input : curr_col - the column index of the src piece, curr_row - the row index of the src piece.
    
    Output : true or false if out side the board.
    
    Run Time : O(1)
    --------------------------------------------------------------------------*/
    private boolean IsLegal(int curr_row, int curr_col) {
        //if can move to location
        //rule
        // chacking if not outside the board
        return curr_row >= 0 && curr_row < N && curr_col >= 0 && curr_col < N;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: isWinner
    
    General : the function check if there is a winner for the game.
    
    Input : winner - the player that did the last move, p2 - the other with the base terirory list, ijp - reference to the info panel.
    
    Output : true or false if there is awinner for the game.
    
    Run Time : O(1) because the number of pieces is a constant (16).
    --------------------------------------------------------------------------*/
    public boolean isWinner(Player winner, Player p2, InfoJPanel ijp) {
        // checking who is the winner
        String color = winner.getColor(); // the color of the player that did the last move and might be the winner
        boolean win = true; // a flag if the player realy win
        
        for (Map.Entry<Integer, Piece> entry : winner.pieces.entrySet()) { // scaning the HashTable of the player
            Integer key = entry.getKey(); // pulling the key
            if (p2.getTeritory().contains(key)) { // checking if the key is one of the opponent teritory indexes
                win = true; // if it's true stay true
            } else {
                win = false; // if it's false set false
                break;
            }
        }
        winAdjustment(color, win, ijp);// call to winAjustment func
        return win;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: winAdjustment
    
    General : the function update the info panel if there is a winner for the game.
    
    Input : color - color of the winner, win - if there is a winner, ijp - reference to the info panel.
    
    Output : updated info panel.
    
    Run Time : O(1).
    --------------------------------------------------------------------------*/
    public void winAdjustment(String color, boolean win, InfoJPanel ijp) {
       Color brown = new Color(147, 73, 0);// brown color RGB
        if (win == true) {// if there is a winner
            switch (color) { // checking the color of the player
                case "red":
                    ijp.getTurnLable().setText("RED WINS"); // set lable
                    ijp.getTurnLable().setForeground(Color.red); //set color of the lable
                    ijp.getTimer().stop();// stop timer
                    break;
                case "brown":
                    ijp.getTurnLable().setText("BROWN WINS"); // set lable
                    ijp.getTurnLable().setForeground(brown); //set color of the lable
                    ijp.getTimer().stop(); // stop timer
                    break;
            }
        }
    }
    
    /*--------------------------------------------------------------------------
    FuncName: colorMove
    
    General : drawing all the possible moves.
    
    Input : col - the column index of the move, row - the row index of the move,
    graphics - the graghics of the frame that the drawing will be on.
    
    Output :  a draw of the move on the board.
    
    Run Time : O(1).
    --------------------------------------------------------------------------*/
    public void colorMove(int col, int row, Graphics graphics) {
        // Coloring possible moves function
        // constant Variable For Calculate the location of the image
        final int matchGreenImageOnBoard = 11 , resizeForGreenImage = 9; // ajustment for greenCell.png 
        
        Image greenCellImage = Toolkit.getDefaultToolkit().getImage("Resources/greenCell.png"); // get reference to the image 
        
        graphics.drawImage(greenCellImage, col * CALCULATE_SIZE_ON_CLICK + matchGreenImageOnBoard, row * CALCULATE_SIZE_ON_CLICK + matchGreenImageOnBoard,
                                                                      Piece.PIECE_SIZE + resizeForGreenImage, Piece.PIECE_SIZE + resizeForGreenImage, panel); // draw the image 
    }
    
    /*--------------------------------------------------------------------------
    FuncName: Draw
    
    General : drawing the board image and the pieces of the 2 players.
    
    Input : graphics - the graghics of the frame that the drawing will be on.
    
    Output :  a draw of the board image and the pieces of the 2 players on the board.
    
    Run Time : O(1).
    --------------------------------------------------------------------------*/
    public void Draw(Graphics graphics) {
        //startup draw players
        graphics.drawImage(this.image, 0, 0, panel.getWidth(), panel.getHeight(), panel); // draw board image
        players[0].Draw(graphics, panel);// draw player pieces
        players[1].Draw(graphics, panel);// draw player pieces
    }
}
