

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class StartButton extends JButton implements ActionListener {

    private PuzzleGame g;
    private Frame f;
    private int code;



    public StartButton(Frame f, int code) {
        this.f = f;
        this.code = code;
         addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    //play kakarasu
    if (code == 1) {
        f.getContentPane().removeAll();
        g = new Kakarasu(f);
        g.emptyGrid();
        g.getGrid();

    }

    if(code == 2) {
        f.getContentPane().removeAll();
        g = new Skyscraper(f);
        g.emptyGrid();
        g.getGrid();
    }

        if(code == 3) {
            f.getContentPane().removeAll();
            g = new Hitori(f);
            g.emptyGrid();
            g.getGrid();
        }

        if(code == 4) {
            f.getContentPane().removeAll();
            g = new Kurodoko(f);
            g.emptyGrid();
            g.getGrid();
        }

    //go back
    if (code == 0) {
        f.getContentPane().removeAll();
        f.setUpFrame();
    }
    // refresh the GUI
    f.validate();
    f.repaint();
    }




}
