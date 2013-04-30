/* Player.java
 * Name(s): Norberto, Vaemen, Vidhu
 * 
 * URL
 * Purpose: Creates a player class/object for the Connect4.java file. 
 */
import java.util.*;

public class Player {
    // fields
    private int MAX_VALUE = Integer.MAX_VALUE;                // max threshold value for alpha-beta pruning
    private int MIN_VALUE = Integer.MIN_VALUE;                // min threshold value for alpha-beta pruning
    private int MAX_DEPTH = 8;                                // maximum depth for minmax method
    
    
    // returns column of the next move for the player                                      // Vaeman
    public int move(int [][] board, int player) {                  
        int columns = board[0].length;
        int max = MIN_VALUE;
        int best = -1;                                    //inital declaration of best
        int lostCause = -1;                               // initial declaration of lostCause (move if game will end in losss regardless)
        int depth = 0;                                    //initial declaration of depth
        for(int i=0; i<columns; i++) {
            if(!isLeafNode(i, board)) {
                tryMove(i, depth, player, board);
                int val = minMax(i, depth+1, MIN_VALUE, MAX_VALUE, player, board);  
                undoMove(i, depth, player, board);
                if(val > max) {
                    best = i;
                    max = val;                        //**CHANGE TO SNYDER"S PSEUDCODE CHECK AT OFFICE HOURS***
                }
                lostCause = i;
            }
        }
        
        if(best != -1) {          
            return best;                             // returns best possible move to aim for a win
        } else {                 
            return lostCause;                        // game is determined to have been lost so returns an available move
        }
    }
    
    // uses min max tree with alpha beta pruning to determine which move to make, returns eval of move as int
    int minMax(int column, int depth, int alpha, int beta, int player, int [][] board) {               // Vaeman
        int eval = eval(board);
        if(isLeafNode(column, board) || depth == MAX_DEPTH || eval >= MAX_VALUE || eval <= MIN_VALUE) {
            return eval; // stop searching and return eval
        }
        else if( isMaxNode(depth) ) { 
            int val = MIN_VALUE;                                                                                
            for(int i=0; i<board[0].length; i++) { 
                if(!isLeafNode(i, board)) {
                    alpha = Math.max(alpha, val); // update alpha with max so far 
                    if(beta < alpha) break; // terminate loop 
                    tryMove(i, depth, player, board);                       
                    val = Math.max(val, minMax(i, depth+1, alpha, beta, player, board)); 
                    undoMove(i, depth, player, board);
                } 
            }
            return val; 
        } else { // is a min node 
            int val = MAX_VALUE; 
            for(int i=0; i<board[0].length; i++) { 
                if(!isLeafNode(i, board)) {
                    beta = Math.min(beta, val); // update beta with min so far 
                    if(beta < alpha) break; // terminate loop
                    tryMove(i, depth, player, board);                 
                    val = Math.min(val, minMax(i, depth+1, alpha, beta, player, board)); 
                    undoMove(i, depth, player, board);
                } 
            }
            return val; 
        } 
    }
    
    // tries move by placing player piece in first available spot in column
    private void tryMove(int column, int depth, int player, int [][] board) {                                // Vaeman
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
    private void undoMove(int column, int depth, int player, int [][] board) {                               // Vaeman
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
    private boolean isLeafNode(int column, int [][] board) {                                                 // Vaeman
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
    
    // returns the worth of a board
    public int eval(int [][] board) {                                                        // Norby
        // int [row][column]
        // board.length = number of rows
        // board[row].length = number of columns in that row
        //if(boardIsEmpty(board)){
        //    return 0;
        //}
        int score = 0;
        int worth = 0;
        int calcWorth = 0;
        /*  checks for vertical win  */
        for(int i = 0; i <= board.length - 4; i++){
            for(int j = 0; j <= board[board.length - 1].length - 1; j++) {
                score = (board[i][j] + 
                         board[i+1][j] + 
                         board[i+2][j] +
                         board[i+3][j]);
                calcWorth = calcWorth(score);
                if(calcWorth == MAX_VALUE) {
                    return MAX_VALUE;
                } else if(calcWorth == MIN_VALUE) {
                    return MIN_VALUE;
                } else {
                    worth += calcWorth;
                }
            }
        }
        /*  checks for horizontal win  */
        for(int i = 0; i <= board.length - 1; i++){
            for(int j = 0; j <= board[board.length - 1].length - 4; j++) {
                score = (board[i][j] + 
                         board[i][j+1] + 
                         board[i][j+2] +
                         board[i][j+3]);
                calcWorth = calcWorth(score);
                if(calcWorth == MAX_VALUE) {
                    return MAX_VALUE;
                } else if(calcWorth == MIN_VALUE) {
                    return MIN_VALUE;
                } else {
                    worth += calcWorth;
                }
            }
        }
        /*  checks for diagonal-backward win  */
        for(int i = 0; i <= board.length - 4; i++){
            for(int j = 3; j <= board[board.length - 1].length - 1; j++) {
                score = (board[i][j] + 
                         board[i+1][j-1] + 
                         board[i+2][j-2] +
                         board[i+3][j-3]);
                calcWorth = calcWorth(score);
                if(calcWorth == MAX_VALUE) {
                    return MAX_VALUE;
                } else if(calcWorth == MIN_VALUE) {
                    return MIN_VALUE;
                } else {
                    worth += calcWorth;
                }
            }
        }
        /*  checks for diagonal-forward win  */
        for(int i = 0; i <= board.length - 4; i++){
            for(int j = 0; j <= board[board.length - 1].length - 4; j++) {
                score = (board[i][j] + 
                         board[i+1][j+1] + 
                         board[i+2][j+2] +
                         board[i+3][j+3]);
                calcWorth = calcWorth(score);
                if(calcWorth == MAX_VALUE) {
                    return MAX_VALUE;
                } else if(calcWorth == MIN_VALUE) {
                    return MIN_VALUE;
                } else {
                    worth += calcWorth;
                }
            }
        }
        return worth;
    }
    
    // used to calculate the worth of a calculated score 
    private int calcWorth(int score){     
        int worth = 0;
        if(score == 0)
            worth += 0;
        
        if(score/10 == 1)                   //            1 in a row for max player
            worth += 5;
        if(score/10 == 2) {                 //            2 in a row for max player 
            if(score%10 == 0)
                worth += 50;
            if(score%10 == 1)
                worth += 20;
            if(score%10 == 2)
                worth += 0;
            
        }
        if(score/10 == 3) {                 //           3 in a row for max player
            if(score%10 == 0)
                worth += 100;
            if(score%10 == 1)
                worth += 0;
        }
        if(score/10 == 4) {                  //             4 in a row for max player
            return MAX_VALUE;
        }
        if(score%10 == 1) // 1 in a row for min player
            worth += -1;
        if(score%10 == 2) // 2 in a row for min player
            worth += -30;
        if(score%10 == 3) // 3 in a row for min player
            worth += -200;
        if(score%10 == 4){ // 4 in a row for min player
            return MIN_VALUE;
        }
        return worth;
    }
    
    // returns true if board is empty and false if not
    private boolean boardIsEmpty(int[][] board) {                                // Norby                              
        int columns = board[0].length;                             
        for(int i=0; i<columns; i++) {                             
            
            if(board[board[board.length-1].length-1][i] != 0) {                                   
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        //unit testing Player class
        Player P1 = new Player();
        int [][] board = new int [8][8];
        
        board[0][3] = 1;
        board[0][4] = 10;
        board[1][3] = 10;
        board[1][4] = 1;
        board[2][3] = 10;
        board[2][4] = 10;
        board[3][3] = 1;
        board[3][4] = 10;
        board[4][3] = 1;
        board[4][4] = 1;
        board[5][3] = 1;
        board[5][4] = 1;
        board[6][3] = 10;
        board[7][3] = 10;
        
        
        // int eval = P1.eval(board);
        System.out.println("leaf node: " + P1.isLeafNode(3, board)); 
        System.out.println(P1.move(board, 10));
    }
}

