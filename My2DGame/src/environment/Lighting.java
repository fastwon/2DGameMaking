package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	
	GamePanel gp;
	BufferedImage darknessFilter;
	public int dayCounter;
	public float filterAlpha = 0f;
	
	// Day State
	public final int day = 0;
	public final int dusk = 1;
	public final int night = 2;
	public final int dawn = 3;
	public int dayState = day;
	
	public int radius;
	public final int minRadius = 75;
	
	public Lighting(GamePanel gp) {
		this.gp = gp;
		setLightSouce();
		
	}
	public void setLightSouce() {
		
		// Create a buffered Image
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
		
		// Get the center x and y of the light circle
		int centerX = gp.player.screenX + gp.tileSize/2;
		int centerY = gp.player.screenY + gp.tileSize/2;
		
		// Create a gradation effect within the light circle
		Color[]  color = new Color[7];
		float[] fraction = new float[7];
		
		color[0] = new Color(0,0,0.1f,0f);
		color[1] = new Color(0,0,0.1f,0.10f);
		color[2] = new Color(0,0,0.1f,0.25f);
		color[3] = new Color(0,0,0.1f,0.40f);
		color[4] = new Color(0,0,0.1f,0.55f);
		color[5] = new Color(0,0,0.1f,0.70f);
		color[6] = new Color(0,0,0.1f,0.95f);
		
		fraction[0] = 0f;
		fraction[1] = 0.2f;
		fraction[2] = 0.4f;
		fraction[3] = 0.6f;
		fraction[4] = 0.8f;
		fraction[5] = 0.9f;
		fraction[6] = 1f;

		RadialGradientPaint gPaint = null;
		
		// Create a gradation paint settings for the light circle
		if(gp.gameState == gp.sleepState) {
			radius = 1;
		} else if(gp.player.currentLight == null) {
			radius = minRadius;
		}else {
			radius = gp.player.currentLight.lightRadius;
		}
		gPaint = new RadialGradientPaint(centerX, centerY, radius, fraction, color);

		// Set the gradient data on g2
		g2.setPaint(gPaint);
		
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.dispose();
	}
	public void resetDay() {
		dayState = day;
		filterAlpha = 0f;
	}
	
	public void update() {
		
		if(gp.player.lightUpdated) {
			setLightSouce();
			gp.player.lightUpdated = false;
		}
		
		// Check the state of the day
		if(dayState == day) {
			
			dayCounter++;
			
			if(dayCounter > 1200) {
				dayCounter = 0;
				dayState = dusk;
			}
		}
		if(dayState == dusk) {
			
			filterAlpha += 0.001f;
			
			if(filterAlpha > 1f) {
				filterAlpha = 1f;
				dayState = night;
			}
		}
		if(dayState == night) {
			
			dayCounter++;
			
			if(dayCounter > 1200) {
				dayCounter = 0;
				dayState = dawn;
			}
		}
		if(dayState == dawn) {
			
			filterAlpha -= 0.001f;
			
			if(filterAlpha < 0) {
				filterAlpha = 0;
				dayState = day;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		if(gp.currentArea == gp.outside) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		}
		if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
			g2.drawImage(darknessFilter, 0, 0, null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		// DEBUG
		String situation = "";
		switch (dayState) {
		case day: situation = "Day"; break;
		case dusk: situation = "Dusk"; break;
		case night: situation = "Night"; break;
		case dawn: situation = "Dawn"; break;
		}
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(40f));
		g2.drawString(situation, 800, 500);
	}

}
