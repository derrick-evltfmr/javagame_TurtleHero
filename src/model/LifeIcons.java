package model;

import controller.Main;
import model.turtlehero.turtleHero;

import java.awt.*;

public class LifeIcons extends GameFigure {

    String text;
    Color color;
    Font font;

    Color originalColor;


    public LifeIcons(int x, int y, Color color, Font font) {
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
        color = new Color (239,164,20); g2.setColor(color);
//        color = originalColor; g2.setColor(color);
        g2.drawString("Life", (int)location.x, (int)location.y);

        g2.setColor(new Color(125,101,45));
        for (int i = 0; i<turtlehero.villageLife; i++) {
            drawVillage(g2, (int) location.x+i*20, (int) location.y + 10, 10, 10);
        }

        g2.setColor(Color.RED);
        for (int i = 0; i<turtlehero.characterLife; i++) {
            drawHeart(g2, (int) location.x+5+60+5+i*20, (int) location.y + 10, 16, 16);
        }

//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135-1, (int)location.y+1);

//        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135, (int)location.y);

//        color = originalColor; g2.setColor(color);
//        g2.fillRect((int)location.x, (int)location.y+13, turtlehero.energyValue*2 ,20);
//        color = new Color (128,254,244); g2.setColor(color);
//        g2.fillRect((int)location.x-1, (int)location.y+15, turtlehero.energyValue*2 ,20);
//        color = new Color (123,242,160); g2.setColor(color);
//        g2.fillRect((int)location.x-2, (int)location.y+17, turtlehero.energyValue*2 ,20);






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

    public void drawVillage(Graphics g, int x, int y, int width, int height) {
        int[] x1 = {x + 0, x +width, x +width*2};
        int[] y1 = {y+width, y , y +width};
        Polygon xy1 = new Polygon(x1, y1, 3);
        g.fillPolygon(xy1);
        g.fillRect(x + width/2,y +height/2,width,height);

    }

    public void drawHeart(Graphics g, int x, int y, int width, int height) {
        int[] triangleX = {
                x - 2*width/18,
                x + width + 2*width/18,
                (x - 2*width/18 + x + width + 2*width/18)/2};
        int[] triangleY = {
                y + height - 2*height/3,
                y + height - 2*height/3,
                y + height };
        g.fillOval(
                x - width/12,
                y,
                width/2 + width/6,
                height/2);
        g.fillOval(
                x + width/2 - width/12,
                y,
                width/2 + width/6,
                height/2);
        g.fillPolygon(triangleX, triangleY, triangleX.length);
    }
}
