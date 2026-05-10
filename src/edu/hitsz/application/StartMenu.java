package edu.hitsz.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel mainPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public StartMenu() {

        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 🌟 实例化普通模式对应的子类
                Game game = new NormalGame();

                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 🌟 实例化困难模式对应的子类
                Game game = new HardGame();

                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
            }
        });

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 🌟 实例化简单模式对应的子类
                Game game = new EasyGame();

                Main.cardPanel.add(game, "game");
                Main.cardLayout.show(Main.cardPanel, "game");
                game.action();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartMenu");
        frame.setContentPane(new StartMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}