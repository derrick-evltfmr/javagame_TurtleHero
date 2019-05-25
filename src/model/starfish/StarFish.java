package model.starfish;

import controller.starobserver.StarObserver;
import controller.starobserver.StarSubject;
import model.GameFigure;

import java.awt.*;
import java.util.ArrayList;

public class StarFish extends GameFigure implements StarSubject {
    private Shape shape;
    private Color color;
    private String strategy;

    public int state;
    public int STATE_EXISISTING = 0;
    public int STATE_TOUCHING = 1;
    public int STATE_DONE = 2;

    public Shape starbodyPolygon = new Polygon();
    public int starbodyOval1x;
    public int starbodyOval1y;
    public int starbodyOval1w;
    public int starbodyOval1h;
    public int starbodyOval2x;
    public int starbodyOval2y;
    public int starbodyOval2w;
    public int starbodyOval2h;

    public int starcoreOval1x;
    public int starcoreOval1y;
    public int starcoreOval1w;
    public int starcoreOval1h;
    public int starcoreOval2x;
    public int starcoreOval2y;
    public int starcoreOval2w;
    public int starcoreOval2h;
    public int starcoreOval3x;
    public int starcoreOval3y;
    public int starcoreOval3w;
    public int starcoreOval3h;

    public Color color1;
    public Color color2;
    public Color color3;
    public Color whitecolor;

    public static final int TYPE_STARFISHENERGY = 1;
    public static final int TYPE_STARFISHLIFE = 2;
    public static final int TYPE_STARFISHPOWER = 3;
    public static final int TYPE_STARFISHSPEED = 4;

    public static int starfishLifeCoolDown = 0;
    public static boolean starfishLifeCDOn = false;

    public static int starfishBuffCoolDown = 0;
    public static boolean starfishBuffCDOn = false;

    public static int starfishPowerEffectLastingCoolDown = 0;
    public static boolean starfishPowerEffectLastingCDOn = false;
    public static int starfishSpeedEffectLastingCoolDown = 0;
    public static boolean starfishSpeedEffectLastingCDOn = false;

    public static boolean starEnergyExisting = false;
    public static boolean starLifeExisting = false;
    public static boolean starPowerExisting = false;
    public static boolean starSpeedExisting = false;

    public static int starfishAddCheckDelay = 0;
    public static boolean starfishAddCheckDelayOn = false;


    ArrayList<StarObserver> listeners = new ArrayList<>();

    public StarFish(int x, int y) {
        super(x,y);
        friendINDEX = 3; //starfish
        state = STATE_EXISISTING;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    } // NOT USING, TOO SIMPLE

    public void setColor(Color color) {
        this.color = color;
    } // NOT USING, TOO SIMPLE

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    } // NOT USING, TOO SIMPLE

    @Override
    public String toString() {
        return "Bomb["+shape+", "+color+", "+strategy+"]";
    }

    @Override
    public void render(Graphics2D g2) {

        //draw star body
        g2.setColor(color1);
        g2.fillOval(starbodyOval1x, starbodyOval1y,starbodyOval1w,starbodyOval1h);

        g2.setColor(color2);
        g2.fillOval(starbodyOval2x, starbodyOval2y,starbodyOval2w,starbodyOval2h);

        g2.setColor(color3);
        g2.drawOval(starbodyOval1x, starbodyOval1y,starbodyOval1w,starbodyOval1h);


        g2.setColor(color1);
        g2.fillPolygon((Polygon)starbodyPolygon);
        g2.setColor(whitecolor);
        g2.drawPolygon((Polygon)starbodyPolygon);

        // draw star core

        g2.setColor(color1);
        g2.fillOval(starcoreOval1x, starcoreOval1y,starcoreOval1w,starcoreOval1h);

        g2.setColor(whitecolor);
        g2.drawOval(starcoreOval1x, starcoreOval1y,starcoreOval1w,starcoreOval1h);

        g2.setColor(color2);
        g2.fillOval(starcoreOval2x, starcoreOval2y,starcoreOval2w,starcoreOval2h);

        g2.setColor(Color.BLACK);
        g2.drawOval(starcoreOval2x, starcoreOval2y,starcoreOval2w,starcoreOval2h);
    }

    @Override
    public void update() {
        updateState();
    }

    private void updateState() {
        if (state == STATE_EXISISTING) {
            if(hitTheTurtleHero>0) {
                state = STATE_TOUCHING;
            }

            if (this.starType == TYPE_STARFISHPOWER){ // ONE DONE ANOTHER FOLLOW
                System.out.println("LINE143");
//                starfishAddCheckDelayOn = true;    // THIS NOT WORK -0-
//                if (starfishAddCheckDelayOn == false){
                    if (starSpeedExisting == false) {
                        state = STATE_DONE;
                        System.out.println("LINE148");
                    }
//                }
            }

            if (this.starType == TYPE_STARFISHSPEED){ // ONE DONE ANOTHER FOLLOW
                System.out.println("LINE153");
//                starfishAddCheckDelayOn = true;
//                if (starfishAddCheckDelayOn == false){
                    if (starPowerExisting == false) {
                        state = STATE_DONE;
                        System.out.println("LINE159");
                    }
//                }
            }
        }

        else if (state == STATE_TOUCHING) {

            state = STATE_DONE;

        }

        else if (state == STATE_DONE) {
            if (starType == TYPE_STARFISHENERGY) starEnergyExisting = false;
            else if (starType == TYPE_STARFISHLIFE) {
                starLifeExisting = false;
                starfishLifeCDOn = true;
            }
            else if (starType == TYPE_STARFISHPOWER) {
                starPowerExisting = false;
                starfishBuffCDOn = true;
            }
            else if (starType == TYPE_STARFISHSPEED) {
                starSpeedExisting = false;
                starfishBuffCDOn = true;
            }


            super.done = true;
//            Main.createUFO = true;

            notifyEvent(); // starfish disappear
        }

    }

    @Override
    public int getCollisionRadius() {
        return 25;
    }

    @Override
    public void attachListener(StarObserver o) {
        listeners.add(o);
    }

    @Override
    public void detachListener(StarObserver o) {
        listeners.remove(o);
    }

    @Override
    public void notifyEvent() {
        for (var o : listeners) {
            o.eventReceived();
        }
    }
}
