package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	
	GamePanel gp;
	BufferedImage darknessFilter;
	
	public Lighting(GamePanel gp, int circleSize) {
		
		// Create a buffered Image
		darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
		
		// Create a screen-size rentangle area
		Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
		
		// Get the center x and y of the light circle
		int centerX = gp.player.screenX + gp.tileSize/2;
		int centerY = gp.player.screenY + gp.tileSize/2;
		
		// Get the top left x and y of the light circle
		double x = centerX - circleSize/2;
		double y = centerY - circleSize/2;
		
		// Create a light circle shape
		Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);
		
		// Create a light circle area
		Area lightArea = new Area(circleShape);
		
		// Subtract the light circle from the screen rentangle
		screenArea.subtract(lightArea);
		
		// Set a color (black) to draw the rentagle
		g2.setColor(new Color(0,0,0,0.90f));
		
		// Draw the screen rectangle without the light circle area
		g2.fill(screenArea);
		
		g2.dispose();
	}
	public void draw(Graphics2D g2) {
		
		g2.drawImage(darknessFilter, 0, 0, null);
	}

}
