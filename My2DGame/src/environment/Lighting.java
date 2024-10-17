package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	
	GamePanel gp;
	BufferedImage darknessFilter;
	
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
		
		color[0] = new Color(0,0,0,0f);
		color[1] = new Color(0,0,0,0.10f);
		color[2] = new Color(0,0,0,0.25f);
		color[3] = new Color(0,0,0,0.40f);
		color[4] = new Color(0,0,0,0.55f);
		color[5] = new Color(0,0,0,0.70f);
		color[6] = new Color(0,0,0,0.95f);
		
		fraction[0] = 0f;
		fraction[1] = 0.2f;
		fraction[2] = 0.4f;
		fraction[3] = 0.6f;
		fraction[4] = 0.8f;
		fraction[5] = 0.9f;
		fraction[6] = 1f;

		RadialGradientPaint gPaint = null;
		
		// Create a gradation paint settings for the light circle
		if(gp.player.currentLight == null) {

			gPaint = new RadialGradientPaint(centerX, centerY, 75, fraction, color);
		}
		else {
			
			gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);
		}

		// Set the gradient data on g2
		g2.setPaint(gPaint);
		
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		g2.dispose();
	}
	public void update() {
		
		if(gp.player.lightUpdated) {
			setLightSouce();
			gp.player.lightUpdated = false;
		}
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
	}

}
