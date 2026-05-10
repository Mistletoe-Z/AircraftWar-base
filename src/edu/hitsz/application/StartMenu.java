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
                // 1. 创建游戏对象（后续我们还要把 "normal" 这个难度传进去）
                Game game = new Game("NORMAL");

                // 2. 把游戏画面加到幻灯片幕布里，起个代号叫 "game"
                Main.cardPanel.add(game, "game");

                // 3. 遥控放映机，切换到代号为 "game" 的这张幻灯片
                Main.cardLayout.show(Main.cardPanel, "game");

                // 4. 告诉游戏开始运行（极其重要！不然画面是静止的）
                game.action();
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 创建游戏对象（后续我们还要把 "hard" 这个难度传进去）
                Game game = new Game("HARD");

                // 2. 把游戏画面加到幻灯片幕布里，起个代号叫 "game"
                Main.cardPanel.add(game, "game");

                // 3. 遥控放映机，切换到代号为 "game" 的这张幻灯片
                Main.cardLayout.show(Main.cardPanel, "game");

                // 4. 告诉游戏开始运行（极其重要！不然画面是静止的）
                game.action();
            }
        });
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. 创建游戏对象（后续我们还要把 "EASY" 这个难度传进去）
                Game game = new Game("EASY");

                // 2. 把游戏画面加到幻灯片幕布里，起个代号叫 "game"
                Main.cardPanel.add(game, "game");

                // 3. 遥控放映机，切换到代号为 "game" 的这张幻灯片
                Main.cardLayout.show(Main.cardPanel, "game");

                // 4. 告诉游戏开始运行（极其重要！不然画面是静止的）
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
