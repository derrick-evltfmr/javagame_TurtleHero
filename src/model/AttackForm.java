package model;

import controller.Main;
import controller.PlayerInputEventQueue;
import model.starfish.StarFish;
import model.turtlehero.turtleHero;

import java.awt.*;
import java.util.Random;

public class AttackForm extends GameFigure {

    String text;
    Color color;
    Font font;

    Color originalColor;

    int colorCount = 0;

    int value1=0, value2=0, value3=0;


    public AttackForm(int x, int y, Color color, Font font) {
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
        color = new Color (198,83,83); g2.setColor(color);
//        color = originalColor; g2.setColor(color);
        g2.drawString("Attack Form", (int)location.x, (int)location.y);

//        color = new Color(233,230,224); g2.setColor(color); // white outline
//        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135-1, (int)location.y+1);
//
//        g2.drawString(Integer.toString(turtlehero.energyValue), (int)location.x+135, (int)location.y);
//
////        color = originalColor; g2.setColor(color);
////        g2.fillRect((int)location.x, (int)location.y+13, turtlehero.energyValue*2 ,20);
//        color = new Color (128,254,244); g2.setColor(color);
//        g2.fillRect((int)location.x-1, (int)location.y+15, turtlehero.energyValue*2 ,20);
//        color = new Color (123,242,160); g2.setColor(color);
//        g2.fillRect((int)location.x-2, (int)location.y+17, turtlehero.energyValue*2 ,20);

        if (PlayerInputEventQueue.weaponForm == PlayerInputEventQueue.WEAPON_NORMALBUBBLE) {
            color = new Color(154,224,222); g2.setColor(color);
            g2.fillOval((int)location.x+10+4,(int)location.y+15+10,10,10);
            color = new Color(233,230,224); g2.setColor(color);
            g2.drawOval((int)location.x+10+4,(int)location.y+15+10,10,10);
        }

        else if (PlayerInputEventQueue.weaponForm == PlayerInputEventQueue.WEAPON_WATERBEAM) {
            Color lightColor = new Color (173,213,233); // 155-255
            Color deepColor = new Color (123,213,233); // the smallest one -100
            Color whiteColor = new Color(233, 230, 224);
            g2.setColor(lightColor); // bottom out oval
            g2.fillOval((int)location.x+10,(int)location.y+15+25,20,12);
            g2.setColor(whiteColor); // bottom out oval
            g2.drawOval((int)location.x+10,(int)location.y+15+25,20,12);
            g2.setColor(deepColor); // bottom out oval
            g2.fillOval((int)location.x+10+2,(int)location.y+15+2+25,16,8);
            g2.setColor(lightColor); // bottom out oval
            g2.drawOval((int)location.x+10+2,(int)location.y+15+2+25,16,8);

            g2.setColor(deepColor);
            g2.fillRect((int)location.x+10+2, (int)location.y+15+2, 16 ,25);
            g2.setColor(lightColor);
            g2.drawRect((int)location.x+10+2, (int)location.y+15+2, 16 ,25);

            g2.setColor(lightColor); // top out oval
            g2.fillOval((int)location.x+10,(int)location.y+15,20,12);
            g2.setColor(whiteColor); // top out oval
            g2.drawOval((int)location.x+10,(int)location.y+15,20,12);
            g2.setColor(deepColor); // top out oval
            g2.fillOval((int)location.x+10+2,(int)location.y+15+2,16,8);
            g2.setColor(lightColor); // top out oval
            g2.drawOval((int)location.x+10+2,(int)location.y+15+2,16,8);
        }

        else if (PlayerInputEventQueue.weaponForm == PlayerInputEventQueue.WEAPON_SPARKLINGBUBBLE) {
            Random rand = new Random();
            Color whiteColor = new Color(233, 230, 224);
            Color smallbubbleCOlor = new Color(210,218,218);

            if (value1==0 || value2==0 || value3==0) { // first time
                value1 = rand.nextInt(100) + 155;
                value2 = rand.nextInt(100) + 155;
                value3 = rand.nextInt(100) + 155;

                if (value1 <= value2 && value1 <= value3) {
                    value1 -= 50;
                } else if ((value2 <= value1 && value2 <= value3)) {
                    value2 -= 50;
                } else {
                    value3 -= 50;
                }

            }

            if (colorCount%15==0) {

                value1 = rand.nextInt(100) + 155;
                value2 = rand.nextInt(100) + 155;
                value3 = rand.nextInt(100) + 155;

                if (value1 <= value2 && value1 <= value3) {
                    value1 -= 50;
                } else if ((value2 <= value1 && value2 <= value3)) {
                    value2 -= 50;
                } else {
                    value3 -= 50;
                }

            }

            Color lightColor = new Color (value1, value2, value3);
            Color deepColor = new Color (value1, value2, value3);

            g2.setColor(lightColor); // top out oval
            g2.fillOval((int)location.x+10+4-7,(int)location.y+15+10-7,25,25);
            g2.setColor(whiteColor); // top out oval
            g2.drawOval((int)location.x+10+4-5,(int)location.y+15+10-5,20,20);
            g2.setColor(deepColor); // top in oval
            g2.fillOval((int)location.x+10+4-5,(int)location.y+15+10-5,20,20);

            g2.setColor(smallbubbleCOlor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+1,(int)location.y+15+10-5+2,4,4);
            g2.setColor(smallbubbleCOlor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+5,(int)location.y+15+10-5+6,3,3);
            g2.setColor(smallbubbleCOlor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+2,(int)location.y+15+10-5+7,2,2);
            g2.setColor(whiteColor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+1,(int)location.y+15+10-5+2,4,4);
            g2.setColor(whiteColor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+5,(int)location.y+15+10-5+6,3,3);
            g2.setColor(whiteColor); // small bubble inside
            g2.fillOval((int)location.x+10+4-5+10+2,(int)location.y+15+10-5+7,2,2);


        }

        colorCount++;
        if (colorCount>3332) colorCount = 0;
        
        if (StarFish.starfishPowerEffectLastingCDOn){
            Color color1 = new Color(141,50,243);
            Color color2 = new Color (50,136,243);
            Color color3 = new Color(198,127,235);
            Color whitecolor = new Color(233,230,224);

            int[] x1 = {(int)location.x+40+30+13, (int)location.x+40+30+17, (int)location.x+40+30+20 , (int)location.x+40+30+28
                    , (int)location.x+40+30+23, (int)location.x+40+30+25 , (int)location.x+40+30+17
                    , (int)location.x+40+30+8+2 , (int)location.x+40+30+11, (int)location.x+40+30+6};
            int[] y1 = {(int)location.y+10+14,(int)location.y+10+6+1,(int)location.y+10+14 ,(int)location.y+10+14
                    ,(int)location.y+10+22,(int)location.y+10+30-1 ,(int)location.y+10+24
                    ,(int)location.y+10+30-1,(int)location.y+10+22 ,(int)location.y+10+14};

            g2.setColor(color1);
            g2.fillOval((int)location.x+40+30+13-10,(int)location.y+10+14-8,28,27);
            g2.setColor(color2);
            g2.fillOval((int)location.x+40+30+13-10+1,(int)location.y+10+14-8+1,28-1,27-1);
            g2.setColor(color3);
            g2.drawOval((int)location.x+40+30+13-10,(int)location.y+10+14-8,28,27);

            g2.setColor(color1);
            g2.fillPolygon(x1,y1,10);
            g2.setColor(whitecolor);
            g2.drawPolygon(x1,y1,10);

            g2.setColor(color1);
            g2.fillOval((int)location.x+40+30+13-5+4-2+1,(int)location.y+10+14-8+4-1+2+1+1,12,12);
            g2.setColor(whitecolor);
            g2.drawOval((int)location.x+40+30+13-5+4-2+1,(int)location.y+10+14-8+4-1+2+1+1,12,12);
            g2.setColor(color2);
            g2.fillOval((int)location.x+40+30+13-5+8-1-3+1,(int)location.y+10+14-8+8-1+1+1,8,8);
            g2.setColor(Color.BLACK);
            g2.drawOval((int)location.x+40+30+13-5+8-1-3+1,(int)location.y+10+14-8+8-1+1+1,8,8);

            
        } else if (StarFish.starfishSpeedEffectLastingCDOn){
            Color color1 = new Color(127,158,205);
            Color color3 = new Color (127,205,163);
            Color color2 = new Color(153,205,127);
            Color whitecolor = new Color(233,230,224);

            int[] x1 = {(int)location.x+40+30+13, (int)location.x+40+30+17, (int)location.x+40+30+20 , (int)location.x+40+30+28
                    , (int)location.x+40+30+23, (int)location.x+40+30+25 , (int)location.x+40+30+17
                    , (int)location.x+40+30+8+2 , (int)location.x+40+30+11, (int)location.x+40+30+6};
            int[] y1 = {(int)location.y+10+14,(int)location.y+10+6+1,(int)location.y+10+14 ,(int)location.y+10+14
                    ,(int)location.y+10+22,(int)location.y+10+30-1 ,(int)location.y+10+24
                    ,(int)location.y+10+30-1,(int)location.y+10+22 ,(int)location.y+10+14};

            g2.setColor(color1);
            g2.fillOval((int)location.x+40+30+13-10,(int)location.y+10+14-8,28,27);
            g2.setColor(color2);
            g2.fillOval((int)location.x+40+30+13-10+1,(int)location.y+10+14-8+1,28-1,27-1);
            g2.setColor(color3);
            g2.drawOval((int)location.x+40+30+13-10,(int)location.y+10+14-8,28,27);

            g2.setColor(color1);
            g2.fillPolygon(x1,y1,10);
            g2.setColor(whitecolor);
            g2.drawPolygon(x1,y1,10);

            g2.setColor(color1);
            g2.fillOval((int)location.x+40+30+13-5+4-2+1,(int)location.y+10+14-8+4-1+2+1+1,12,12);
            g2.setColor(whitecolor);
            g2.drawOval((int)location.x+40+30+13-5+4-2+1,(int)location.y+10+14-8+4-1+2+1+1,12,12);
            g2.setColor(color2);
            g2.fillOval((int)location.x+40+30+13-5+8-1-3+1,(int)location.y+10+14-8+8-1+1+1,8,8);
            g2.setColor(Color.BLACK);
            g2.drawOval((int)location.x+40+30+13-5+8-1-3+1,(int)location.y+10+14-8+8-1+1+1,8,8);
        }



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
