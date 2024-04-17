package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
	
	KeyHandler keyH;
	
	int accel;
	int speedCnt;
	String lastDir;
	int max_speed = 8;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = gp.tileSize / 6;
		solidArea.y = gp.tileSize / 3;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gp.tileSize * 2 / 3;
		solidArea.height = gp.tileSize * 2 / 3;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		accel = 1;
		
		direction = "down";
		lastDir = "down";
		
		
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
	}
	
	
	public void update() {
		speed = accel * speedCnt;
		if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true) {
			speedCnt++;
			if(keyH.upPressed == true) {
				direction = "up";
			}
			if(keyH.downPressed == true) {
				direction = "down";
			}
			if(keyH.leftPressed == true) {
				direction = "left";
			}
			if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpobject(objIndex);
			
			// CHECK NPC COLLSION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
				lastDir = direction;
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			speedCnt--;
			if(collisionOn == false) {
				
				switch(lastDir) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
		}
		if(speedCnt <= 0) {
			speedCnt = 0;
		}
		if(speedCnt >= max_speed) {
			speedCnt = max_speed;
		}
	}
	public void pickUpobject(int i) {
		
		if(i != 999) {
			
		}
	}
	public void interactNPC(int i) {
		
		if(i != 999) {
			if(gp.keyH.enterPressed) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		gp.keyH.enterPressed = false;
	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, null);
		
	}
}
