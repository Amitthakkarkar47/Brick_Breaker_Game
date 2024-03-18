package com.project.game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener, KeyListener{
	private boolean play = false;
	private int totalBrick = 21;
	private Timer timer;
	private int delay = 8;
	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private int playerX = 350;
	private int score = 0;
	private int level =1;
	private int brickLevel =3;
	private MapGenerator map;
	
	
	public GamePlay(){
		super();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
		
		map = new MapGenerator(3, 7);
	}
	
	public void paint(Graphics g){
		
		//black canvas
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 692, 3);
		
		//top
		g.fillRect(0, 3, 3, 592);
		
		//left
		g.fillRect(692, 3, 3, 592);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//bricks
		map.draw((Graphics2D)g);
		
		//ball 
		g.setColor(Color.red);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		//score
		g.setColor(Color.green);
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Score : "+score, 580, 30);
		
		//score
		g.setColor(Color.green);
		g.setFont(new Font("serif", Font.BOLD, 20));
		g.drawString("Level : "+level, 20, 30);
		
		//Game Over
		if(ballPosY >= 570){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font("sarif", Font.BOLD, 35));
			g.drawString("Game Over!!, Score : "+score, 150, 300);
			
			g.setFont(new Font("sarif", Font.BOLD, 25));
			g.drawString("Press Enter to Restart", 200, 350);
		}
		
		//You Won
		if(totalBrick<=0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.green);
			g.setFont(new Font("sarif", Font.BOLD, 35));
			g.drawString("You Won !!, Score : "+score, 150, 300);
			
			g.setFont(new Font("sarif", Font.BOLD, 25));
			g.drawString("Press Enter to Start level : "+level, 200, 350);	
			

		}
		
		
		
	}
	private void moveLeft(){
		play = true;
		playerX -= 20;
	}
	private void moveRight(){
		play =  true;
		playerX += 20;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//paddle moving left
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerX <=0)
				playerX =0;
			else
				moveLeft();
		}
		
		//paddle moving right
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerX >=600)
				playerX = 600;
			else
				moveRight();
		}
		//restart the game when Enter is pressed
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				score = 0;
				totalBrick = 21+7;
				ballPosX = 120;
				ballPosY =  350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 120;
				level++;
				brickLevel++;
				map = new MapGenerator(brickLevel, 7);	
			}
		}
		repaint();	
	}

	@Override
	public void keyReleased(KeyEvent arg0) {	
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(play){
			if(ballPosX <=0){
				ballXdir =- ballXdir;
			}
			if(ballPosX >=670){
				ballXdir =- ballXdir;
			}
			if(ballPosY <=0){
				ballYdir =- ballYdir;
			}
			
			//creating rectangle around bricks
			Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
			Rectangle paddleRect = new Rectangle(playerX, 550, 100,8);

			if(ballRect.intersects(paddleRect)){
				ballYdir =- ballYdir;
			}

			A:	for(int i=0; i<map.map.length; i++){
				for(int j=0; j<map.map[0].length; j++){
					if(map.map[i][j]>0){
						
						int width = map.brickWidth;
						int height = map.brickHeight;
						int brickXpos = 80+j*width;
						int brickYpos = 50 + i*height;

						//collision
						Rectangle brickRect = new Rectangle(brickXpos, brickYpos, width, height);
						if(ballRect.intersects(brickRect)){
							map.setBrick(0, i, j);
							//int totalBrick = 0;
							totalBrick--;
							score+=5;
							
							// left ||  right 
							if(ballPosX-19 <=brickXpos || ballPosX+1 >= brickXpos+width){
								ballXdir =- ballXdir;

							}else{
								ballYdir =- ballYdir;
							}
							//label A
							break A;
						}
					}
				}
			}
			ballPosX += ballXdir;
			ballPosY += ballYdir;
		}
		repaint();
	}
}
