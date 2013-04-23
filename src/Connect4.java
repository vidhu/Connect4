import java.awt.*;
import java.net.URL;
import java.util.EventObject;
import javax.swing.*;
import java.util.Random;

public class Connect4 {
    //Boord used in this game
    static int[][] board = new int[8][8]; //[row][column]
    
    //The grids in the board
    static JButton[][] squares = new JButton[8][8];
    
    //GridColors
    static ImageIcon blankButton = new ImageIcon("src/blank.png");
    static ImageIcon blueButton = new ImageIcon("src/blue.png");
    static ImageIcon redButton = new ImageIcon("src/red.png");
    
    //A player i.e a bot
    Player p = new Player("blue");
    
    public static void main(String[] args) throws InterruptedException {
        drawGui();
        while(true){
            makeMove(10, 3);  //Bot makes move
            userTurn();       //user makes move
        }
    }
    
    //returns true is there is a 4 in a row at the latest point where the color was added
    public static boolean isWin(int row, int column){
        return isWinHelper(row, column, 1, -1) || //Check diagonal up 
                isWinHelper(row, column, 1, 1) || //Check diagonal down
                isWinHelper(row, column, 1, 0) || //Check horizontal
                isWinHelper(row, column, 0, 1);   //Chech vertical

    }
    public static boolean isWinHelper(int row, int column, int stepX, int stepY){        
        int playerSum = 0;
        
        for(int i=1;i<4;i++){
            if((row + (stepY*i)) < 0 || (row + (stepY*i)) > 7){
                break;
            }
            if((column + (stepX*i)) < 0 || (column + (stepX*i)) > 7){
                break;
            }
            int a = row + (stepY*i);
            int b = column + (stepX*i);
            playerSum += board[row + (stepY*i)][column + (stepX*i)];
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
            int a = row + (stepY*i);
            int b = column + (stepX*i);
            playerSum += board[row + (stepY*i)][column + (stepX*i)];
        }
        
        if(playerSum == 3){
            return true;
        }else if(playerSum == 30){
            return true;
        }
        
        return false;
    }
    
    //Draws the windows and the board when called. Call only once at start of program!
    public static void drawGui(){
        ImageIcon blankButton = new ImageIcon("src/blank.png");
        ImageIcon blueButton = new ImageIcon("src/blue.png");
        ImageIcon redButton = new ImageIcon("src/red.png");
        
        //Create Gui Frame
        JFrame frame = new JFrame("Hash Function Comparison");
        frame.setSize(500, 500);
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
                JBox.setSize(squares[i][j], 50, 50);
                squares[i][j].setIconTextGap(0);
            }  
        }
       
        
        JBox body = JBox.vbox(
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
            )
        );
        
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
                JBox.setSize(squares[i][j], 50, 50);
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
            if(name.subSequence(0, 3).equals("box")) {
                //get the coordinates
                int row = Integer.parseInt(name.split("[|]+")[1]);
                int column = Integer.parseInt(name.split("[|]+")[2]);
                
                
                ImageIcon nextSquareIcon = (ImageIcon)squares[row][column].getIcon();
                
                if(nextSquareIcon == blankButton){
                    while(row<7){        
                        nextSquareIcon = (ImageIcon)squares[row+1][column].getIcon();
                        row++;
                        if(nextSquareIcon != blankButton){
                            row--;
                            break;
                        }
                    }
                    squares[row][column].setIcon(redButton);
                    
                    //Change the board
                    makeMove(1, column, row);
                    break;
                }
            }
        }
    }
    
    public static void makeMove(int Player, int column){
        int row =0;
        
        //Sets the next available row
        while(row<7){
            row++;
            if (board[row][column] != 0) {
                row--;
                break;
            }
        }
        
        makeMove(Player, column, row);
    }
    
    public static void makeMove(int player, int column, int row){
        board[row][column] = player;
        
        //Detect a win
        if (isWin(row, column)) {
            System.out.println("Win detected!");
        }  
        
        //Updates the gui to relfect changes in the board
        updateGui();
    }
}
