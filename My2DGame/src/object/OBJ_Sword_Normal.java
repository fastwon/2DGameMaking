package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {
	
	public OBJ_Sword_Normal(GamePanel gp) {
		super(gp);
		
		type = type_sword;
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackValue = 2;
		description = "[" + name + "]\nAn old sword";
		attackArea.width = 36;
		attackArea.height = 36;
		price = 20;
		knockBackPower = 2;
		motion1_duration = 5;
		motion2_duration = 25;
	}
}
