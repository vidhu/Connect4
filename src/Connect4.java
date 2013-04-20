import java.awt.*;
import javax.swing.*;

public class Connect4 {

    int[][] board = new int[8][8];

    Player p = new Player();
    
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("src/blank.png");
        
        //Create Gui Frame
        JFrame frame = new JFrame("Hash Function Comparison");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(255, 240, 180));
        
        JButton[] squares = new JButton[64];
        
        for(int i=0; i<squares.length; i++){
            squares[i] = new JButton(icon);
            JBox.setSize(squares[i], 50, 50);
            squares[i].setIconTextGap(0);
        }
       
        
        JBox body = JBox.vbox(
                    JBox.hbox(
                        squares[0], squares[1], squares[2], squares[3],
                        squares[4], squares[5], squares[6], squares[7]
                    ),
                    JBox.hbox(
                        squares[8], squares[9], squares[10], squares[11],
                        squares[12], squares[13], squares[14], squares[15]
                    ),
                    JBox.hbox(
                        squares[16], squares[17], squares[18], squares[19],
                        squares[20], squares[21], squares[22], squares[23]
                    ),
                    JBox.hbox(
                        squares[24], squares[25], squares[26], squares[27],
                        squares[28], squares[29], squares[30], squares[31]
                    ),
                    JBox.hbox(
                        squares[32], squares[33], squares[34], squares[35],
                        squares[36], squares[37], squares[38], squares[39]
                    ),
                    JBox.hbox(
                        squares[40], squares[41], squares[42], squares[43],
                        squares[44], squares[45], squares[46], squares[47]
                    ),
                    JBox.hbox(
                        squares[48], squares[49], squares[50], squares[51],
                        squares[52], squares[53], squares[54], squares[55]
                    ),
                    JBox.hbox(
                        squares[56], squares[57], squares[58], squares[59],
                        squares[60], squares[61], squares[62], squares[63]
                    )
                );
        
        frame.add(body);
        frame.setVisible(true);
    }
}
