package model.sparklingbubble;

import controller.Main;
import model.GameFigure;
import model.turtlehero.turtleHero;
import model.lobstermon.lobsterMon;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static controller.Main.gameData;

public class SparklingBubble extends GameFigure {

    public static final int UNIT_MOVE = 6;
    public static final int INIT_BUBBLE_SIZE = 20;
    public static final int MAX_BUBBLE_SIZE = 40;

    int size = INIT_BUBBLE_SIZE;
    Point2D.Float target; // where mouse was pressed, get target location and draw the path towards it
    Point2D.Float longTarget;


    Color color; // color of the bubble
    double rad;


    public static final int STATE_CHARGING = 0;
    public static final int STATE_SHOOTING = 1;
    public static final int STATE_EXPLODING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_CHASING =5;

    public final int MAX_SB_EXPLODECOUNT =8;


    int state;
    int explodeCount =0;

    public double shortestEnemyDist = 9999; // initialize when generated, so won't be too close because of 0
    public GameFigure closestEnemy;

    SparklingBubbleAnimStrategy animStrategy;



    Color lightColor = new Color (173,233,233); // 155-255
    Color deepColor = new Color (123,233,233); // the smallest one -100

    public SparklingBubble(int tx, int ty) { // x->tx, y->ty, target, that is, mouse press location
        turtleHero turtleHero = (turtleHero) Main.gameData.fixedObject.get(Main.INDEX_TURTLEHERO);

        //initial location of the bubble <- the end of the barrel

        super.location.x = turtleHero.location.x -14; // almost the nose of turtle
        super.location.y = turtleHero.location.y +1; // almost the nose of turtle

        double delta_x = tx - location.x;
        double delta_y = ty - location.y;

        double direction = Math.atan2(delta_y, delta_x);

        target = new Point2D.Float((float)(tx + 500*Math.cos(direction)), (float)(ty+ 500*Math.sin(direction))); // mousepress location as target

        color = new Color(168,255,255); // initial color

        animStrategy = new SparklingBubbleAnimShooting(this);

        friendINDEX = 5;
        chargeCount = 0;

    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(1)); // change the thickness back to 1
//        g2.fillOval((int) super.location.x - size / 2, (int) super.location.y - size / 2, size, size);

//        if (chargeCount <= 10){
//            g2.fillOval((int) super.location.x - 20 / 2, (int) super.location.y - 20 / 2, 20, 20);
//        }
//        else if (chargeCount <= 20){
//            g2.fillOval((int) super.location.x - 40 / 2, (int) super.location.y - 40 / 2, 40, 40);
//        }
//        else if (chargeCount <= 30){
//            g2.fillOval((int) super.location.x - 60 / 2, (int) super.location.y - 60 / 2, 60, 60);
//        }
//        else {
//            g2.fillOval((int) super.location.x - 80 / 2, (int) super.location.y - 80 / 2, 80, 80);
//        }



        if (chargeCount <= 40) { // charging
            // outside circle
            color = lightColor;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 2 / 2, (int) super.location.y - chargeCount * 2 / 2,
                    chargeCount * 2, chargeCount * 2);
            // white circle edge
            color = new Color(233, 230, 224);
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 2 / 2 + 6, (int) super.location.y - chargeCount * 2 / 2 + 6,
                    chargeCount * 2 - 12, chargeCount * 2 - 12);
            // inside circle
            color = deepColor;
            g2.setColor(color);
            g2.fillOval((int) super.location.x - chargeCount * 2 / 2 + 7, (int) super.location.y - chargeCount * 2 / 2 + 7,
                    chargeCount * 2 - 14, chargeCount * 2 - 14);

            // small bubbles inside
            color = new Color(233, 230, 224);
            g2.setColor(color);
            g2.fillOval((int) super.location.x + chargeCount/10-2, (int) super.location.y- chargeCount/3*2,
                    chargeCount/3, chargeCount/3);
            g2.fillOval((int) super.location.x + chargeCount/3 , (int) super.location.y - chargeCount/3,
                    chargeCount/5, chargeCount/5);
            g2.fillOval((int) super.location.x + chargeCount/2-2, (int) super.location.y - chargeCount/2,
                    chargeCount/8, chargeCount/8);

            color = new Color(210, 218, 218);
            g2.setColor(color);
            g2.drawOval((int) super.location.x + chargeCount/10 -2 , (int) super.location.y- chargeCount/3*2,
                    chargeCount/3, chargeCount/3);
            g2.drawOval((int) super.location.x + chargeCount/3, (int) super.location.y - chargeCount/3,
                    chargeCount/5, chargeCount/5);
            g2.drawOval((int) super.location.x+ chargeCount/2-2, (int) super.location.y - chargeCount/2,
                    chargeCount/8, chargeCount/8);
        }
        else if (state == STATE_EXPLODING) { // maximum
            // ouside circle
            color = lightColor; g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2-explodeCount*4/2, (int) super.location.y - 80 / 2 -explodeCount*4/2,
                    80+explodeCount*4, 80+explodeCount*4);

            // white circle edge
            color = new Color(233,230,224); g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +6-explodeCount*4/2, (int) super.location.y - 80 / 2 +6-explodeCount*4/2,
                    80-12+explodeCount*4, 80-12+explodeCount*4);

            // inside circle
            color = deepColor; g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +7-explodeCount*4/2, (int) super.location.y - 80 / 2 +7-explodeCount*4/2,
                    80-14+explodeCount*4, 80-14+explodeCount*4);

            // small bubbles inside
            color = new Color(233,230,224); g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +42, (int) super.location.y - 80 / 2 +13,
                    13+explodeCount*5/5, 13+explodeCount*5/5);
            g2.fillOval((int) super.location.x - 80 / 2 +54, (int) super.location.y - 80 / 2 +25,
                    8+explodeCount*5/5, 8+explodeCount*5/5);
            g2.fillOval((int) super.location.x - 80 / 2 +58, (int) super.location.y - 80 / 2 +18,
                    5+explodeCount*5/5, 5+explodeCount*5/5);

            color = new Color(210,218,218); g2.setColor(color);
            g2.drawOval((int) super.location.x - 80 / 2 +42, (int) super.location.y - 80 / 2 +13,
                    13+explodeCount*5/5, 13+explodeCount*5/5);
            g2.drawOval((int) super.location.x - 80 / 2 +54, (int) super.location.y - 80 / 2 +25,
                    8+explodeCount*5/5, 8+explodeCount*5/5);
            g2.drawOval((int) super.location.x - 80 / 2 +58, (int) super.location.y - 80 / 2 +18,
                    5+explodeCount*5/5, 5+explodeCount*5/5);


        }

        else { // maximum
            // ouside circle
            color = lightColor; g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2, (int) super.location.y - 80 / 2, 80, 80);
            // white circle edge
            color = new Color(233,230,224); g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +6, (int) super.location.y - 80 / 2 +6,
                    80-12, 80-12);
            // inside circle
            color = deepColor; g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +7, (int) super.location.y - 80 / 2 +7,
                    80-14, 80-14);
            // small bubbles inside
            color = new Color(233,230,224); g2.setColor(color);
            g2.fillOval((int) super.location.x - 80 / 2 +42, (int) super.location.y - 80 / 2 +13,
                    13, 13);
            g2.fillOval((int) super.location.x - 80 / 2 +54, (int) super.location.y - 80 / 2 +25,
                    8, 8);
            g2.fillOval((int) super.location.x - 80 / 2 +58, (int) super.location.y - 80 / 2 +18,
                    5, 5);

            color = new Color(210,218,218); g2.setColor(color);
            g2.drawOval((int) super.location.x - 80 / 2 +42, (int) super.location.y - 80 / 2 +13,
                    13, 13);
            g2.drawOval((int) super.location.x - 80 / 2 +54, (int) super.location.y - 80 / 2 +25,
                    8, 8);
            g2.drawOval((int) super.location.x - 80 / 2 +58, (int) super.location.y - 80 / 2 +18,
                    5, 5);


        }

        chargeCount++;
    }

    @Override
    public void update() {
        updateState();
        animStrategy.animate();

//            if (target.distance(location) <= 8.0) {
//                //explosion effect
//                if (size <= MAX_BUBBLE_SIZE) size += 5;
//
//                if (size == MAX_BUBBLE_SIZE || size == MAX_BUBBLE_SIZE + 1) { // because ++
//                    while (size > 0) size -= 15;
//                }
//
//            } else {
//
//
//                double delta_x = target.x - location.x;
//                double delta_y = target.y - location.y;
//
//                double direction = Math.atan2(delta_y, delta_x);
//
//                super.location.x += UNIT_MOVE * Math.cos(direction); // opposite(delta x)/hypotenuse
//                super.location.y += UNIT_MOVE * Math.sin(direction); // adjacent(delta y)/hypotenuse
//            }

    }

    private void updateState() { // STATE ARE NOT WORKING HERE...
        Random rand = new Random();
        int value1 = rand.nextInt(100)+155;
        int value2 = rand.nextInt(100)+155;
        int value3 = rand.nextInt(100)+155;

        lightColor = new Color (value1, value2, value3);

        if (value1<=value2 && value1<=value3) {
            value1 -= 50;
        } else if ((value2<=value1 && value2<=value3)) {
            value2 -= 50;
        } else {
            value3 -= 50;
        }

        deepColor = new Color (value1, value2, value3);
        if (state == STATE_CHARGING) {
            if (chargeCount >=40) {
                state = STATE_SHOOTING;
            }
            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                color = Color.WHITE;
                animStrategy = new SparklingBubbleAnimExploding(this);

            }
        }
        else if (state == STATE_SHOOTING ) { // travelling mode

            for (var enemy: Main.gameData.enemyObject) {
                if (enemy.enemyINDEX!=3 && enemy.enemyINDEX!=5){
                    if (this.location.distance(enemy.location) < shortestEnemyDist) {
                        shortestEnemyDist = this.location.distance(enemy.location); // save distance
                        closestEnemy = enemy; // save enemy
                    }
                    System.out.println("shortestDist "+ shortestEnemyDist +"  closest Enemy "+ closestEnemy);
                }
            }
            if (shortestEnemyDist <= 600.0) {
                if (location.y>-10) { // can see in the game scene
                    state = STATE_CHASING;
                    animStrategy = new SparklingBubbleAnimChasing(this);
                    color = new Color(value1,value2,value3);
                }
            }

//            if (lobsterMon.currentLobsterMon != null) { // existing lobsterMon and chase mode
//                if (hitCount == 0 && lobsterMon.currentLobsterMon.location.distance(location) <= 400.0) {
//                    if (location.y>-10){
//                        state = STATE_CHASING;
//                        animStrategy = new SparklingBubbleAnimChasing(this);
////                    color = new Color(value1,value2,value3);
////                    System.out.println("testing1");
//                    }
//                }
//            }

            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                color = Color.WHITE;
                animStrategy = new SparklingBubbleAnimExploding(this);

            }
        }
        else if (state == STATE_CHASING) {
            if (hitCount > 0 || target.distance(location) <= 10.0) {
                state = STATE_EXPLODING;
                color = Color.WHITE;
                animStrategy = new SparklingBubbleAnimExploding(this);
//                System.out.println(hitCount);
            }

        }

        else if (state == STATE_EXPLODING) {
            if (explodeCount >= MAX_SB_EXPLODECOUNT) {
                state = STATE_DONE;
            }
        }
        else if (state == STATE_DONE) {
            super.done = true;
        }

    }

    @Override
    public int getCollisionRadius() {
        return 80/2;
    }
}
