package controller.starobserver;

import controller.InputEvent;
import controller.Main;
import controller.starobserver.StarObserver;
import view.MyWindow;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class StarObserverAddNew implements StarObserver {

    public static int StardiedCount = 0;

    public static TimerTask ttAddStar;



    @Override
    public void eventReceived() {
        System.out.println("starfish energy absorbed");
        StardiedCount++;

//        Main.addLobMwithListener(100,200); cannot add this way
//      When the UFOObserver recieved the lobsterMon died, we create some corresponding event and let input event queue handle it


            InputEvent starevent = new InputEvent();
            Random rand = new Random();
            int x = rand.nextInt(1100)+50;
            int y = -100;
//            starevent.event = new StarCreateEvent("starfishEnergy", x, y);
            starevent.event = new StarCreateEvent("StarFish", x, y);
            starevent.type = InputEvent.STAR_CREATE; // let InputEvent handle it...


            ttAddStar = new TimerTask() {
                @Override
                public void run() {
                        Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
                }
            };
            Main.delayGenerateTimer.schedule(ttAddStar,rand.nextInt(2500)+3000); //(2.5-4.5second -> 3.5-7.5second));


//        new java.util.Timer().schedule( // delay Timer!!!
//                new java.util.TimerTask() {
//                    @Override
//                    public void run() {
//                        if (MyWindow.restarting != true) {
//                            Main.playerInputEventQueue.queue.add(starevent); // wait after delay time and add new
//                        }
//                    }
//                },
//                rand.nextInt(2000)+2500); //(2.5-4.5second)


//        else if (LobdiedCount ==3){
//            InputEvent event = new InputEvent();
//            event.event = new EnemyCreateEvent("lobsterMon", 300, 100);
//            event.type = InputEvent.LOBM_CREATE; // let InputEvent handle it...
//            Main.playerInputEventQueue.queue.add(event);
//        }
//
//        else if (LobdiedCount >3) {
//            Main.endScreen_Clear(); // cannot do stop running... will crash...
//        }

    }
}
