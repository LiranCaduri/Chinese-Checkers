/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.awt.Point;
import java.util.HashMap;

/**              ComputerAI
 * ---------------------------------------
 * @author LiranCaduri
 * 
 *  Class for AI Player - human vs AI. 
 * ---------------------------------------
 */
public class ComputerAI extends Player {
    // Constants
    // weightMat - for strategy | so the AI will know how to insert the game pieces to RED teritory
    private static final int[][] WEIGHT_MAT = {{40, 50, 65, 75},
                                              {50, 60, 70, 85},
                                              {65, 70, 80, 90},
                                              {75, 85, 90, 100}
    };

    private static final int MAX_DISTANCE = 13 ; // the number of the maximum distance on board
    private static final int DEVIATION = Board.N - WEIGHT_MAT.length; // the number that need to add when reference cell on the weightMat to board. (for returning a point on the red teritory)
    

    /*--------------------------------------------------------------------------
    General : The class constractor.
    
    Input : startR - the start row index to init pieces  , endR - the end row index to init pieces, 
    startC - the start col index to init pieces, endC - the end col index to init pieces , color - the color of the player pieces .
    
    Output : instance of the class.
    --------------------------------------------------------------------------*/
    public ComputerAI(int startR, int endR, int startC, int endC, String color) {
        // constractor for AI 
        super(startR, endR, startC, endC, color);
    }

    /*--------------------------------------------------------------------------
    FuncName: evaluate
    
    General : the function calculate the board status grade.
    
    Input : dest - destination point .
    
    Process : the function summarize all the minimum distances between every piece of the AI to the dest point.
    
    Output : grade to the board status.
    
    Run Time : O(1) because the number of pieces is a constant (16) 
    --------------------------------------------------------------------------*/
    private double evaluate(Point dest) {
        // init
        Point src = new Point() ; // src piece  
        double distance = 0, value = 0; // distance - number of distance between src -> dest. | value - grade to board status.
        
        // print trace
        System.out.println("in evaluate ***************\n ");
        System.out.println("Dest point in eval = ("+ dest.x +","+ dest.y +")\n\n");
        System.out.println("---------------------------------");
        
        for (Integer key : pieces.keySet()) { // scaning all of the AI pieces
            
            // set source point
            src.setLocation(key / Board.N, key % Board.N);

            System.out.println("Src point in eval loop = ("+ src.x +","+ src.y +")");
            
            // calculate distance
            distance = Math.sqrt(Math.pow((dest.y - src.y), 2) + Math.pow((dest.x - src.x), 2));
            // sum all grades
            value += MAX_DISTANCE - distance;
            
            System.out.println("current value = "+ value);
        }
        
        // for trace
        System.out.println("final value = " + value);
        System.out.println("out eva *************** \n\n\n");
        
        return value ;
    }
    
    /*--------------------------------------------------------------------------
    FuncName: findTargetPlace
    
    General : the function search for a destination point on the board.
    
    Input : reference to board game. 
    
    Process : the function finds an untaken cell with the biggest weight on the red player base.
    
    Output: returning destination point.
    
    Run Time : O(1) because the size of the matrix is constant (4X4)
    --------------------------------------------------------------------------*/
    private Point findTargetPlace(Board board) {
        // init
        int maxWeight = 0;
        Point dest = new Point();   
        
        for (int i = 0; i < WEIGHT_MAT.length; i++) { //moving on rows
            for (int j = 0; j < WEIGHT_MAT[0].length; j++) { //moving on cols
               
                if (maxWeight < WEIGHT_MAT[i][j] && (!board.getPlayer0().pieces.containsKey((j + DEVIATION) * Board.N + (i + DEVIATION))
                        && !board.getPlayer1().pieces.containsKey((j + DEVIATION) * Board.N + (i + DEVIATION)))) { // if BIGGER than MAX AND Available

                    //seting values
                    maxWeight = WEIGHT_MAT[i][j];
                    dest.setLocation(j + DEVIATION, i + DEVIATION);
                    // dest.x = j + deviation;
                    // dest.y = i + deviation;
                }
            }
        }
        if (dest.x == 0 && dest.y == 0) dest.setLocation(9,9); // need double check
        
        return dest;
    }

    /**--------------------------------------------------------------------------
    FuncName : onClickAI
    
    General : the function find the best possible move on the board for the AI.
    
    Input : reference to board game. 
    
    Process : the function run findTargetPlace algorithm that returns the dest point,
            than finds all the possible moves for every game piece without painting the move on the panel,
            the function does every move that possible for every piece and runs
            evaluate algorithm and get a grade for every move than its undo the move and 
            when its all finish the algorithm does the move with the maximum grade. 
    
    Output : AI makes the best that possible move on the board.
    
    Run Time : O(n) 
    --------------------------------------------------------------------------*/
    public void onClickAI(Board board) {
        
        //init
        Point oldPos = new Point(); // temperary src point
        Point dest = findTargetPlace(board); // getting the destanation point
        Point maxNewPos = new Point() , maxOldPos = new Point(); // the best move src and dest 
        double value = 0, maxEval = 0; // maxEval - the max grade of the best move. | value - grade of the board status. 
       
        // copy of the piece table | that cancel the error of trying read and write on the same time
        HashMap<Integer, Piece> hmPicCopy = (HashMap) pieces.clone();

        for (Integer key : hmPicCopy.keySet()) { // running on pieces table copy
            // setting the source piece
            oldPos.y = key % Board.N;
            oldPos.x = key / Board.N;
            
            // searching for possible moves with the current piece
            board.possibleLocations(oldPos, true); 

            for (Integer k : board.locationsAI.keySet()) {// running on possible moves table
                // set destination
                Point newPos = new Point(k / Board.N, k % Board.N);
                
                // make a move
                board.addPiece(newPos.x, newPos.y, false);
                board.removePiece(oldPos.x, oldPos.y, false);
               
                // giving a grade to the board after a move
                value = evaluate(dest); 
                
                // ----------------------Strategy Fixing--------------------------
                if(oldPos.x > 5 && oldPos.y > 5 && newPos.x > 5 && newPos.y > 5){ // IF in red teritory
                    if ((WEIGHT_MAT[oldPos.y - DEVIATION][oldPos.x - DEVIATION] >= WEIGHT_MAT[newPos.y-DEVIATION][newPos.x - DEVIATION])){ // cancel move to a lower weigth cell
                        System.out.println("-50 ###");
                        value -= 50;
                    }
                    if ((WEIGHT_MAT[oldPos.y - DEVIATION][oldPos.x - DEVIATION] < WEIGHT_MAT[newPos.y - DEVIATION][newPos.x - DEVIATION])){ // bonus to move with a grader weigth
                        System.out.println("+70***");
                        value += 70;   
                    }
                }
                if ((oldPos.x > 5 && oldPos.y > 5) && (newPos.x <= 5 || newPos.y <=5)){// cancel the jump outside the red teritory
                    value -= 30;
                    System.out.println("-30 ###");
                }
                //-----------------------------------------------------------
                // System.out.println("value = " + value);
                
                // put back the piece
                board.addPiece(oldPos.x, oldPos.y, false);
                board.removePiece(newPos.x, newPos.y, false);

                if (value > maxEval) {// setting values for the move with the bigges grade
                    maxEval = value;
                    maxNewPos.x = newPos.x;
                    maxNewPos.y = newPos.y;
                    maxOldPos.x = oldPos.x;
                    maxOldPos.y = oldPos.y;
                }

            } // end of inner for
            // clean possible moves table for the next piece
            board.locationsAI.clear();
        }// end of outter for
        
        // do the move
        board.addPiece(maxNewPos.x, maxNewPos.y, false);
       
        // remove source piece
        board.removePiece(maxOldPos.x, maxOldPos.y, false);
         
        // trace
        System.out.println("maxeval = " + maxEval);
        System.out.println("---------------------------");

        board.panel.repaint(); // repaint the panel
    }
}
