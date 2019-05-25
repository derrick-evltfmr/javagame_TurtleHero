package view;

import controller.Main;

import javax.swing.*;
import java.awt.*;

public class MyCanvas extends JPanel {

    public int width; // w of canvas
    public int height; // h of canvas

    public boolean upKeyOn, downKeyOn, leftKeyOn, rightKeyOn, spaceKeyOn;

    public static boolean canvaslooping = false;

//    public static Graphics2D Canvasg2;
    public static Color g2BgColor = Color.BLACK;

    public void render() { // using double buffer mechanism for fast animation
                           // render is a computer graphic term
        width = getSize().width;
        height = getSize().height;

        // off-screen double buffer image
        Image doubleBufferImage = createImage(width, height); //creatImage(given sizes)
        if (doubleBufferImage == null) {
            System.out.println("Critical error: doubleBufferImage is null");
            System.exit(1);
        }

        // off-screen rendering (once doublebufferimage is ready)
        Graphics2D g2OffScreen = (Graphics2D) doubleBufferImage.getGraphics();

        if (g2OffScreen == null) {
            System.out.println("Critical error: g2OffScreen is null");
            System.exit(1);
        }

        // initialize the image buffer ( now the doublebufferimage is confirmed ready)

        g2OffScreen.setBackground(g2BgColor); // original black
        g2OffScreen.clearRect(0,0, width, height); // everything is clear

        // render all game data here!!
        //////    g2OffScreen.setColor(Color.RED);
        //////    g2OffScreen.drawOval(100,100,50,50);
        // for everything in gameData, we render it
        if (MyWindow.restarting != true) {
                for (var fig : Main.gameData.fixedObject) {
                    canvaslooping = true;
                    if (MyWindow.restarting == true) break; // avoid ConcurrentModificationException when restarting(modify) and loop
                    fig.render(g2OffScreen);
                    if (MyWindow.restarting == true) break;
                }
                for (var fig : Main.gameData.friendObject) {
                    canvaslooping = true;
                    if (MyWindow.restarting == true) break;
                    fig.render(g2OffScreen);
                    if (MyWindow.restarting == true) break;
                }
                for (var fig : Main.gameData.enemyObject) {
                    canvaslooping = true;
                    if (MyWindow.restarting == true) break;
                    fig.render(g2OffScreen);
                    if (MyWindow.restarting == true) break;
                }
                for (var fig : Main.gameData.textObject) {
                    canvaslooping = true;
                    if (MyWindow.restarting == true) break;
                    fig.render(g2OffScreen);
                    if (MyWindow.restarting == true) break;
            }
            canvaslooping = false;
        }


        // use active rendering to put the buffer image on screen (GPU)
        Graphics gOnScreen;
        gOnScreen = this.getGraphics();
        if (gOnScreen != null) {
            // copy offScreen image to onScreen
            gOnScreen.drawImage(doubleBufferImage, 0, 0, null); // we don't need enemyobserver just put null
        }
        Toolkit.getDefaultToolkit().sync(); // sync the display on some systems
                                            // for system dependent issue
        if (gOnScreen != null) {
                gOnScreen.dispose();
        }



    }
}
