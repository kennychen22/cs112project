
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;


public class Frame extends JFrame {

    //sets up beginning frame

    public void setUpFrame() {

        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(800,800));

        JPanel centerGrid = new JPanel(new GridLayout(2,2,5,5));

        Font myFont = new Font("Serif",Font.BOLD, 36);

        //making label purdy
        JLabel label= new JLabel("Please select a game");
        label.setFont(myFont);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(100,100));
        label.setBackground(Color.lightGray);
        label.setOpaque(true);



        JPanel topPanel = new JPanel();




        ct.add(centerGrid, BorderLayout.CENTER);
        ct.add(label, BorderLayout.NORTH);

       // ct.add(topPanel, BorderLayout.NORTH);

        // add the jbuttons to select the game
        JButton k = new StartButton(this, 1);
        k.setText("Play Kakarasu");
        k.setFont(myFont);
        k.setBackground(Color.ORANGE);
        k.setOpaque(true);
        k.setBorderPainted(false);



        JButton s = new StartButton(this, 2);
        s.setText("Play Skyscraper");
        s.setFont(myFont);
        s.setBackground(Color.decode("#247BA0"));
        s.setOpaque(true);
        s.setBorderPainted(false);

        JButton h = new StartButton(this, 3);
        h.setText("Play Hitori");
        h.setFont(myFont);
        h.setBackground(Color.decode("#247BA0"));
        h.setOpaque(true);
        h.setBorderPainted(false);


        JButton ko = new StartButton(this, 4);
        ko.setText("Play Kurodoko");
        ko.setFont(myFont);
        ko.setBackground(Color.ORANGE);
        ko.setOpaque(true);
        ko.setBorderPainted(false);


       centerGrid.add(k);
       centerGrid.add(s);
       centerGrid.add(h);
       centerGrid.add(ko);
       ct.validate();
       ct.repaint();

    }

}

