package model;

import controller.Main;
import controller.PlayerInputEventQueue;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.awt.geom.Point2D;

public class EnergyBar extends GameFigure {

    String text;
    Color color;
    Font font;

    Color originalColor;


    public EnergyBar(int x, int y, Color color, Font font) {
        super(x,y);
        this.color = color;
        this.originalColor = color;
        this.font = font;


    }



    @Override
    public void render(Graphics2D g2) {
        var turtlehero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);
//        this.font = new Font(this.font.getName(),Font.BOLD,this.font.getSize()+1);
        g2.setFont(font);
        color = new Color (123,242,160); g2.setColor(color);
//        color = originalColor; g2.setColor(color);
        g2.drawString("Energy Bar", (int)location.x, (int)location.y);

//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135-1, (int)location.y+1);

        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135, (int)location.y);

//        color = originalColor; g2.setColor(color);
//        g2.fillRect((int)location.x, (int)location.y+13, turtlehero.energyValue*2 ,20);
        color = new Color (128,254,244); g2.setColor(color);
        g2.fillRect((int)location.x-1, (int)location.y+15, turtlehero.energyValue*2 ,20);
        color = new Color (123,242,160); g2.setColor(color);
        g2.fillRect((int)location.x-2, (int)location.y+17, turtlehero.energyValue*2 ,20);


    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {

    }

    @Override
    public int getCollisionRadius() {
        return 0;
    }
}
