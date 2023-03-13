package com.crazypumpkin.animation;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class Main extends JFrame {

    public Main() {
        init();
    }

    private void init() {
        var board = new Board();
        add(board);

        setResizable(false);
        pack();

        setTitle("Animation");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}
