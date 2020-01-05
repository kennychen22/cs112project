import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resetter extends JButton implements ActionListener {
    private PuzzleGame g;

    public Resetter(PuzzleGame g) {
        this.g = g;
        addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        g.emptyGrid();
    }
}
