import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*; 

public class WhacAMole {
    int boardwidth = 600; 
    int boardheight = 650; //50px for the text panel on top

    JFrame frame = new JFrame("Mario: Whac A Mole"); 
    JLabel textLabel = new JLabel();  // <-- déclaration
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel(); 

    JButton[] board = new JButton[9]; // 3*3
    ImageIcon moleIcon; 
    ImageIcon plantIcon;

    JButton currMoleTile; 
    JButton currPlantTile; 

    Random random = new Random(); 
    Timer setMoleTimer; 
    Timer setPlantTimer; 
    int score = 0; 


    WhacAMole (){
        frame.setSize(boardwidth, boardheight); 
        frame.setLocationRelativeTo(null); // open the window at the center of the screen 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0"); 
        textLabel.setOpaque(true);  
        
        textPanel.setLayout(new BorderLayout()); 
        textPanel.add(textLabel); 
        frame.add(textPanel, BorderLayout.NORTH); 

        boardPanel.setLayout(new GridLayout(3,3)); // lig, col
        //boardPanel.setBackground(Color.black);
        frame.add(boardPanel); 

        // plantIcon = new ImageIcon(getClass().getResource("./piranha.png")); 
        // getClass() refers to WhacAMole class
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150,150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150,150, java.awt.Image.SCALE_SMOOTH));

        score = 0; 

        for (int i = 0; i < 9; i++){
            JButton tile = new JButton(); 
            board[i] = tile; 
            boardPanel.add(tile); 
            tile.setFocusable(false); // remove rectangle when clicking on image
            //tile.setIcon(moleIcon); 
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource(); 
                    if (tile == currMoleTile){
                        score += 10; 
                        textLabel.setText("Score: " + Integer.toString(score)); 
                    }
                    else if (tile == currPlantTile){
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop(); 
                        setPlantTimer.stop(); 
                        
                        for (int i = 0; i < 9; i++){
                            board[i].setEnabled(false); 
                        }
                    }
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // remove mole from current tile
                if (currMoleTile != null){ // already has an img
                    currMoleTile.setIcon(null);
                    currMoleTile = null; 
                }

                // randomly selctect another tile
                int num = random.nextInt(9); // 0-8
                JButton tile = board[num]; 

                // if tile is occupied by plant, skip tile for this turn 
                if (currPlantTile == tile){ return; }
                
                // set tile to mole
                currMoleTile = tile; 
                currMoleTile.setIcon(moleIcon); 
            }
            
        }); //1000ms = 1s

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (currPlantTile != null){ 
                    currPlantTile.setIcon(null);
                    currPlantTile = null; 
                }

                // selctect a random tile
                int num = random.nextInt(9); // 0- 8
                JButton tile = board[num]; 

                if (currMoleTile == tile){ return; }

                currPlantTile = tile; 
                currPlantTile.setIcon(plantIcon); 
            }
            
        }); 

        setMoleTimer.start(); 
        setPlantTimer.start(); 
        frame.setVisible(true);  // last one so it can display window
    }


}