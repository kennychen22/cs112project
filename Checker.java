import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checker extends JButton implements ActionListener {
    private PuzzleGame g;


    public Checker(PuzzleGame g) {
        this.g = g;
        addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
      g.solvedMessage();
    }
}
