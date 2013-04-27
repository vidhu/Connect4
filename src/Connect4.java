import java.awt.*;
import java.util.EventObject;
import javax.swing.*;


public class Connect4 {
    //Boord used in this game
    //The grids in the board
    static JButton[][] squares = new JButton[8][8];
    static JLabel winnerlbl = new JLabel();
    static int[][] board = new int[8][8];            //game board [row][column]
    
    //GridColors
    static ImageIcon blankButton = new ImageIcon("blank.png");
    static ImageIcon blueButton = new ImageIcon("blue.png");
    static ImageIcon redButton = new ImageIcon("red.png");
    static ImageIcon blueButtonLight = new ImageIcon("bluel.png");
    static ImageIcon redButtonLight = new ImageIcon("redl.png");
    
    //marks if the game is in progress or a player has won
    static boolean isWon = false;
    static int[][] winningCells = new int[4][2];
    
    public static void main(String[] args) throws InterruptedException {
        Player p = new Player();                   // bot
        drawGui();
        
        boolean firstMove = false;        
        while(!isWon){
            
            if(firstMove) {                                  //DELTE AFTER TESTING LINE IS ONLY USED TO SIMULATE COMPUTER GOING FIRST
                makeMove(10, 4);
                firstMove = false;
            }                                     
            
            userTurn();                                  // user makes move
            makeMove(10, p.move(board, 10));                 // bot makes move (playerNumber, column)
        }
        
        
    }
    
    //returns true is there is a 4 in a row at the latest point where the color was added
    public static boolean isWin(int row, int column){
        return isWinHelper(row, column, 1, -1) || //Check diagonal up 
            isWinHelper(row, column, 1, 1) || //Check diagonal down
            isWinHelper(row, column, 1, 0) || //Check horizontal
            isWinHelper(row, column, 0, 1);   //Check vertical
        
        
    }
    public static boolean isWinHelper(int row, int column, int stepX, int stepY){        
        int playerSum = 0;
        int player = board[row][column];
        
        addWinningCell(row, column);
        
        for(int i=1;i<4;i++){
            if((row + (stepY*i)) < 0 || (row + (stepY*i)) > 7){
                break;
            }
            if((column + (stepX*i)) < 0 || (column + (stepX*i)) > 7){
                break;
            }
            //debug variable
            int a = row + (stepY*i);
            int b = column + (stepX*i);
            
            if(board[row + (stepY*i)][column + (stepX*i)] == player){
                addWinningCell(row + (stepY*i), column + (stepX*i));
                playerSum += board[row + (stepY*i)][column + (stepX*i)];
            }else{
                break;
            } 
        }
        
        stepX *= -1;
        stepY *= -1;
        for(int i=1;i<4;i++){
            if((row + (stepY*i)) < 0 || (row + (stepY*i)) > 7){
                break;
            }
            if((column + (stepX*i)) < 0 || (column + (stepX*i)) > 7){
                break;
            }
            //debug variable
            int a = row + (stepY*i);
            int b = column + (stepX*i);
            
            if(board[row + (stepY*i)][column + (stepX*i)] == player){
                addWinningCell(row + (stepY*i), column + (stepX*i));
                playerSum += board[row + (stepY*i)][column + (stepX*i)];
            }else{
                break;
            }           
        }
        
        if(playerSum%10 >= 3){
            return true;
        }else if(playerSum/10 >= 3){
            return true;
        }else{
            resetWinningCell();
        }
        
        return false;
    }
    
    //Draws the windows and the board when called. Call only once at start of program!
    public static void drawGui(){
        ImageIcon blankButton = new ImageIcon("src/blank.png");
        ImageIcon blueButton = new ImageIcon("src/blue.png");
        ImageIcon redButton = new ImageIcon("src/red.png");
        
        //Create Gui Frame
        JFrame frame = new JFrame("Connect 4");
        frame.setSize(537, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(255, 240, 180));
        
        
        for(int i=0; i<squares.length; i++){
            for(int j=0; j<squares.length; j++){
                if(board[i][j] == 1){
                    squares[i][j] = new JButton(redButton);
                }else if(board[i][j] == 10){
                    squares[i][j] = new JButton(blueButton);
                }else{
                    squares[i][j] = new JButton(blankButton);
                }
                JBox.setSize(squares[i][j], 65, 65);
                squares[i][j].setIconTextGap(0);
            }  
        }
        
        
        JBox body = JBox.vbox(
                              JBox.hbox(
                                        JBox.hglue(),
                                        JBox.hbox(new JLabel("Connect 4")),
                                        JBox.hglue()),
                              JBox.vglue(),
                              JBox.hbox(
                                        squares[0][0], squares[0][1], squares[0][2], squares[0][3],
                                        squares[0][4], squares[0][5], squares[0][6], squares[0][7]
                                       ),
                              JBox.hbox(
                                        squares[1][0], squares[1][1], squares[1][2], squares[1][3],
                                        squares[1][4], squares[1][5], squares[1][6], squares[1][7]
                                       ),
                              JBox.hbox(
                                        squares[2][0], squares[2][1], squares[2][2], squares[2][3],
                                        squares[2][4], squares[2][5], squares[2][6], squares[2][7]
                                       ),
                              JBox.hbox(
                                        squares[3][0], squares[3][1], squares[3][2], squares[3][3],
                                        squares[3][4], squares[3][5], squares[3][6], squares[3][7]
                                       ),
                              JBox.hbox(
                                        squares[4][0], squares[4][1], squares[4][2], squares[4][3],
                                        squares[4][4], squares[4][5], squares[4][6], squares[4][7]
                                       ),
                              JBox.hbox(
                                        squares[5][0], squares[5][1], squares[5][2], squares[5][3],
                                        squares[5][4], squares[5][5], squares[5][6], squares[5][7]
                                       ),
                              JBox.hbox(
                                        squares[6][0], squares[6][1], squares[6][2], squares[6][3],
                                        squares[6][4], squares[6][5], squares[6][6], squares[6][7]
                                       ),
                              JBox.hbox(
                                        squares[7][0], squares[7][1], squares[7][2], squares[7][3],
                                        squares[7][4], squares[7][5], squares[7][6], squares[7][7]
                                       ),
                              JBox.hbox(
                                        winnerlbl
                                       ),
                              JBox.vglue()
                             );
        body.setFont(new Font("Connect 4", Font.BOLD, 48));
        
        frame.add(body);
        frame.setVisible(true);
        
    }
    
    //call this whenever you update the board to redraw the grids
    public static void updateGui(){
        for(int i=0; i<squares.length; i++){
            for(int j=0; j<squares.length; j++){
                if(board[i][j] == 1){
                    squares[i][j].setIcon(redButton);
                }else if(board[i][j] == 10){
                    squares[i][j].setIcon(blueButton);
                }else{
                    squares[i][j].setIcon(blankButton);
                }
                JBox.setSize(squares[i][j], 65, 65);
                squares[i][j].setIconTextGap(0);
            }  
        }
    }
    
    //wait for user input
    public static void userTurn(){
        //Event listeners
        JEventQueue events = new JEventQueue();
        for(int i=0; i<squares.length; i++){
            for(int j=0; j<squares.length; j++){
                events.listenTo(squares[i][j], "box|"+i+"|"+j);
            }  
        }
        
        while(true){
            EventObject event = events.waitEvent();
            String name = events.getName(event);
            if (name.subSequence(0, 3).equals("box")) {
                //get the coordinates
                int column = Integer.parseInt(name.split("[|]+")[2]);
                
                //Change the board
                makeMove(1, column);
                break;
            }
        }
    }
    
    public static void makeMove(int Player, int column){                    // Vaeman
        boolean found = false;
        int row = board.length-1; 
        while(!found && row>=0) {
            if(board[row][column] == 0) {  
                makeMove(Player, column, row);
                found = true;
            }
            row--;
        }
    }
    
    public static void makeMove(int player, int column, int row){
        board[row][column] = player;        
        //Detect a win
        if (isWin(row, column)) {
            //Sets the win message
            if(player == 1){
                highlightWinningCells(1);
                winnerlbl.setText("You have won!"); 
            }else if(player == 10){
                highlightWinningCells(10);
                winnerlbl.setText("Bot has won!");
            }
            
            //Disables all buttons
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    squares[i][j].setEnabled(true);
                }
            }
            
            //Sets isWon flag to true to halt game
            isWon = true;
            
            //Debuging thing
            System.out.println("Win detected!");
            return;
        }  
        
        //Updates the gui to relfect changes in the board
        updateGui();
    }
    
    public static void addWinningCell(int row, int column){
        for(int i=0;i<4;i++){
            if(winningCells[i][0] == 0){
                winningCells[i][0] = row;
                winningCells[i][1] = column;
                break;
            }
        }
    }
    
    public static void resetWinningCell(){
        for(int i=0; i<4; i++){
            for(int j=0; j<2; j++){
                winningCells[i][j] = 0;
            }
        }
    }
    
    public static void highlightWinningCells(int player){
        if(player == 1){
            for(int i=0; i<4; i++){
                squares[winningCells[i][0]][winningCells[i][1]].setIcon(redButtonLight);
            }
        }else if(player == 10){
            for(int i=0; i<4; i++){
                squares[winningCells[i][0]][winningCells[i][1]].setIcon(blueButtonLight);
            }
        }
    }
}