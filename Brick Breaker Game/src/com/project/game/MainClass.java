package com.project.game;
import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args) {
	
		//frame for the game
		JFrame frame = new JFrame();
		frame.setTitle("Brick Breaker");
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		GamePlay gamePlay = new GamePlay();
		frame.add(gamePlay);
	}
}
