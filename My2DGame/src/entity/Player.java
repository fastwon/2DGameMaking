package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.JobAttributes.DefaultSelectionType;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

public class Player extends Entity {
	
	KeyHandler keyH;	
	
	public final int screenX;
	public final int screenY;
	public boolean attackCanceled = false;
	public boolean lightUpdated = false;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		// SOLID AREA
		solidArea = new Rectangle();
		solidArea.x = gp.tileSize / 6;
		solidArea.y = gp.tileSize / 3;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gp.tileSize * 2 / 3;
		solidArea.height = gp.tileSize * 2 / 3;
		// ATTACK AREA
//		attackArea.width = 36;
//		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 62;
		worldY = gp.tileSize * 39;
//		worldX = gp.tileSize * 23;
//		worldY = gp.tileSize * 21;
//		worldX = gp.tileSize * 12;
//		worldY = gp.tileSize * 12;
		
		
		defaultSpeed = 6;
		speed = defaultSpeed;
		direction = "down";
		
		
		// PlAYER STATUS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1; // The more strength he has, the more damage he gives.
		dexterity = 1; // The more dexterity he has, the less damage he receives.
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
//		projectile = new OBJ_Rock(gp);
		attack = getAttack();	// The total attack value is decided by strength and weapon.
		defense = getDefense();	// The total defense value is decided by dexterity and shield.
	}
	public void setDefaultPositions() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		direction = "down";
	}
	public void restoreLifeAndMana() {
		
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	
	
	public void setItems() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	public void getSleepingImage(BufferedImage image) {
		
		up1 = image;
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 = image;
		right1 = image;
		right2 = image;
	}
	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
	}
	
	public void update() {
		
		if(attacking) {
			attacking();
		} else if(keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
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
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK INTERACTIVE TILE COLLISION
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile); 
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false && keyH.enterPressed == false) {
				
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
			}

			if (keyH.enterPressed && attackCanceled == false) {
				gp.playSE(1);
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			
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
		} 
		
		if(gp.keyH.shotKeyPressed && !projectile.alive 
				&& shotAvailableCounter == 30 && projectile.haveResource(this)) {
			
			// SET DEFALUT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			
			// SUBTRACT THE COST (MANA, AMMO ETC.)
			projectile.subtractResource(this);
			
			// CHECK VACANCY
			for(int i=0; i<gp.projectile[1].length; i++) {
				if(gp.projectile[gp.currentMap][i] == null) {
					gp.projectile[gp.currentMap][i] = projectile;
					break;
				}
			}
			
			shotAvailableCounter = 0;
			
			gp.playSE(10);
		}
		
		// This needs to be outside of key if statement!
		if(invincible) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.stopMusic();
			gp.playSE(12);
		}
	}
	public void attacking() {
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter >5 && spriteCounter <=25) {
			spriteNum = 2;
			
			// Save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeigth = solidArea.height;
			
			// Adjust player's worldX/Y for the attackArea
			switch (direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			// attackArea becomes solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			// Check monster collision with the updated worldX, worldY and solidArea
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex, attack, currentWeapon.knockBackPower);
			
			int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
			damageInteractiveTile(iTileIndex);
			
			int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
			damageProjectile(projectileIndex);
			
			// After checking collision restore the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeigth;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void pickUpobject(int i) {
		
		if(i != 999) {
			
			// PICKUP ONLY ITEMS
			if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
			
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			// OBSTACLE
			else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
				if(keyH.enterPressed) {
					attackCanceled = true;
					gp.obj[gp.currentMap][i].interact();
				}
			}
			else {
				// INVENTORY ITEMS
				String text;
				
				if(canObtainItem(gp.obj[gp.currentMap][i])) {
					gp.playSE(1);
					text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
				} else {
					text = "You cannot carry any more!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}
	}
	public void interactNPC(int i) {
		
		if(gp.keyH.enterPressed) {
			if(i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
			}
		}
	}
	// player가 monster를 건드렸을경우
	public void contactMonster(int i) {
		
		if(i != 999) {
			
			if(!invincible && !gp.monster[gp.currentMap][i].dying) {
				gp.playSE(6);
				
				int damage = gp.monster[gp.currentMap][i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invincible = true;
			}
		}
	}
	public void damageMonster(int i, int attack, int knockBackPower) {
		
		if(i != 999) {
			
			if(gp.monster[gp.currentMap][i].invincible == false) {
				
				gp.playSE(5);
				
				if(knockBackPower > 0) {
					knockBack(gp.monster[gp.currentMap][i], knockBackPower);
				}
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				if(gp.monster[gp.currentMap][i].life < 0) {
					gp.monster[gp.currentMap][i].life = 0;
				}
				gp.ui.addMessage(damage + " damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].damageReaction();
				
				if(gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
					gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
					exp += gp.monster[gp.currentMap][i].exp;
					checkLevelUp();
				}
			}
		}
	}
	public void knockBack(Entity entity, int knockBackPower) {
		
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}
	public void damageInteractiveTile(int i) {
		
		if(i != 999 && gp.iTile[gp.currentMap][i].destructible 
				&& gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if(gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestoryedForm();
			}
			
		}
	}
	public void damageProjectile(int i) {
		
		if(i != 999) {
			Entity projectile = gp.projectile[gp.currentMap][i];
			projectile.alive = false;
			generateParticle(projectile, projectile);
		}
	}
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp + level*level;
			maxLife += 2;
			life = maxLife;
			maxMana++;
			mana = maxMana;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You are level " + level + " now!\n"
					+ "You feel stronger!";
		}
	}
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			if(selectedItem.type == type_shield) {
				
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_light) {
				
				if(currentLight == selectedItem) {
					currentLight = null;
				}
				else {
					currentLight = selectedItem;
				}
				lightUpdated = true;
			}
			if(selectedItem.type == type_consumable) {
				
				if(selectedItem.use(this))  {
					selectedItem.amount--;
					if(selectedItem.amount == 0) {
						inventory.remove(itemIndex);
					}
				}
			}
		}
	}
	public int searchItemInInventory(String itemName) {
		
		int itemIndex = 999;
		
		for(int i=0; i<inventory.size(); i++) {
			if(inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}
	public boolean canObtainItem(Entity item) {
		
		boolean canObtain = false;
		
		// CHECK STACKABLE
		if(item.stackable) {
			
			int index = searchItemInInventory(item.name);
			
			if(index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			} else { // New item so need to check vacancy
				if(inventory.size() != maxInventorySize) {
					inventory.add(item);
					canObtain = true;
				}
			}
		}
		else { // Not stackable so check vacancy
			if(inventory.size() != maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
		}
		
		return canObtain;
	}
	
	public void draw(Graphics2D g2) {
		
//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up":
			if(attacking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			} else {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			} else {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			} else {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}
				if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			} else {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		if(invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		
		// DEBUG
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible:"+invincibleCounter, 10, 400);
		
	}
}
