/* Player.java
 * Name(s): Norberto, Vaemen, Vidhu
 * 
 * URL
 * Purpose: Creates a player class/object for the Connect4.java file. 
 */
import java.util.*;

public class Player {
    // fields
    private int key;                                          // the player key that determines if you're red or blue
    private int [][] board;                                   // game board
    private int MAX_VALUE = Integer.MAX_VALUE;    // max threshold value for alpha-beta pruning
    private int MIN_VALUE = Integer.MIN_VALUE;              // min threshold value for alpha-beta pruning
    private int MAX_DEPTH = 4;                                // maximum depth for minmax method
    
    // constructor 
    public Player(String color) {
        if(color.equalsIgnoreCase("red")) {
            this.key = 1;
        } else if(color.equalsIgnoreCase("blue")) {
            this.key = 10;
        } else {
            this.key = 0;
        }
    }
    
    public int getKey() {
        return this.key;
    }
    
    // returns column of the next move for the player                                      // Vaeman
    public int move(int [][] board, int player) {                  
        this.board = board;
        int columns = board[0].length;
        int max = Integer.MAX_VALUE;
        int best = -1;                                    //inital declaration of best
        for(int i=0; i<columns; i++) { 
            int val = minMax(i, 0, MIN_VALUE, MAX_VALUE, player);
            if(val > max) {
                best = i;
                val = max;
            }
        } 
        return best;
    }
    
    // uses min max tree with alpha beta pruning to determine which move to make, returns eval of move as int
    int minMax(int column, int depth, int alpha, int beta, int player) {               // Vaeman
        int eval = eval(board);
        if(isLeafNode(column) || depth == MAX_DEPTH || eval >= MAX_VALUE || eval <= MIN_VALUE) {
            tryMove(column, depth, player);
            undoMove(column, depth, player);
            return eval; // stop searching and return eval
        }
        else if( isMaxNode(depth) ) { 
            int val = MIN_VALUE; 
            for(int i=0; i<board[0].length; i++) { 
                alpha = Math.max(alpha, val); // update alpha with max so far 
                if(beta < alpha) break; // terminate loop 
                tryMove(column, depth, player);
                val = Math.max(val, minMax(i, depth+1, alpha, beta, player)); 
                undoMove(column, depth, player);
            } 
            return val; 
        } else { // is a min node 
            int val = MAX_VALUE; 
            for(int i=0; i<board[0].length; i++) { 
                beta = Math.min(beta, val); // update beta with min so far 
                if(beta < alpha) break; // terminate loop
                tryMove(column, depth, player);
                val = Math.min(val, minMax(i, depth+1, alpha, beta, player)); 
                undoMove(column, depth, player);
            } 
            return val; 
        } 
    }
    
    // tries move by placing player piece in first available spot in column
    private void tryMove(int column, int depth, int player) {                                // Vaeman
        boolean found = false;
        int row = board.length-1; 
        while(!found && row>=0) {
            if(board[row][column] == 0) {                   
                if(depth%2==0) {                              // even depth means original player turn
                    board[row][column] = player;            
                } else {                                     // odd depth means other player's turn
                    if(player==10) { 
                        board[row][column] = 1;
                    } else {
                        board[row][column] = 10;
                    }
                }
                found = true;
            }
            row--;
        }
    }
    
    // undo last move by removing player piece in last unavailable spot in column 
    private void undoMove(int column, int depth, int player) {                               // Vaeman
        boolean found = false;
        int row = 0;
        while(!found && row<board.length) {
            if(board[row][column] != 0) {
                board[row][column] = 0;
                found = true;
            }
            row++;
        }
    }   
    
    // returns true if leaf node and false if not   
    private boolean isLeafNode(int column) {                                                 // Vaeman
        if(board[0][column] != 0) {  
            return true;
        }
        return false;
    }
    
   // return true if max node and false if not
    private boolean isMaxNode(int depth) {
        if(depth%2 == 0) {
            return  true;
        }
        return false;
    }
      
    public int eval(int [][] board) {                                                        // Norby
        // int [row][column]
        // board.length = number of rows
        // board[row].length = number of columns in that row
        // int [row][column]
        // board.length = number of rows
        // board[row].length = number of columns in that row
        if(boardIsEmpty(board)){
            return 0;
        }
        int row = 0;
        int column = 0;
        int score = 0;
        int worth = 0;
        /*  checks for vertical win  */
        for(int i = 0; i < board.length - 4; i++){
            for(int j = 0; j < board[board.length - 1].length; j++) {
                score = (board[i][j] + 
                         board[i+1][j] + 
                         board[i+2][j] +
                         board[i+3][j]);
                worth = calcWorth(score);
            }
        }
        /*  checks for horizontal win  */
        for(int i = 0; i < board.length - 1; i++){
            for(int j = 0; j < board[board.length - 1].length - 4; j++) {
                score = (board[i][j] + 
                         board[i][j+1] + 
                         board[i][j+2] +
                         board[i][j+3]);
                worth = calcWorth(score);
            }
        }
        /*  checks for diagonal-backward win  */
        for(int i = 0; i < board.length - 4; i++){
            for(int j = 3; j < board[board.length - 1].length - 1; j++) {
                score = (board[i][j] + 
                         board[i+1][j-1] + 
                         board[i+2][j-2] +
                         board[i+3][j-3]);
                worth = calcWorth(score);
            }
        }
        /*  checks for diagonal-forward win  */
        for(int i = 0; i < board.length - 4; i++){
            for(int j = 0; j < board[board.length - 1].length - 4; j++) {
                score = (board[i][j] + 
                         board[i+1][j+1] + 
                         board[i+2][j+2] +
                         board[i+3][j+3]);
                worth = calcWorth(score);
            }
        }
        return worth;
    }
    
    private int calcWorth(int score){    // used to 
        int worth = 0;
        if(score == 0)
            worth += 0;
        
        if(score/10 == 1) // score/10 == 1     1 in a row
            worth += 1;
        if(score/10 == 2) // score/10 == 2     2 in a row
            worth += 30;
        if(score/10 == 3) // score/10 == 3     3 in a row
            worth += 100;
        if(score/10 == 4) // score/10 == 4     4 in a row
            worth += Integer.MAX_VALUE;
        
        if(score%10 == 1)
            worth += -1;
        if(score%10 == 2)
            worth += -30;
        if(score%10 == 3)
            worth += -100;
        if(score%10 == 4)
            worth += -987654321;
        
        return worth;
    }
        
    // returns true if board is empty and false if not
    private boolean boardIsEmpty(int[][] board) {                                            // Vaeman
        int columns = board[0].length;                               // the index 0x0 is the top left corner. so 7x0 would be the bottom left corner
        for(int i=0; i<columns; i++) {                               // so i think this method is the correct one
                                                                     // - Norby
            if(board[0][i] != 0) {
                return false;
            }
        }
        return true;
    }
    
    /*
    private boolean boardIsEmpty(int[][] board) {                     // rewrote this method above since you were checking last row in board
        for(int i = 0; i < board[board.length - 1].length; i++) {     // when it should be the first row (columns are filled from bottom to top)
            if(board[board.length-1][i] != 0){                        // - Vaeman
                return false;                                         
            }                                                         
        }                                                             
        return true;
    }  
    */
}

