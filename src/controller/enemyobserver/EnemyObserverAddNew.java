package controller.enemyobserver;

import controller.InputEvent;
import controller.Main;

import java.util.Random;
import java.util.TimerTask;

public class EnemyObserverAddNew implements EnemyObserver {

    public static int EnemydiedCount = 0;
    public static boolean callPiranhaMonOn = false;

    public static TimerTask ttAddPiranha;

    @Override
    public void eventReceived() {
        EnemydiedCount++;
        System.out.println("enemy died");

        if (EnemydiedCount == 3) {
            callPiranhaMonOn = true;
        }



//        Main.addLobMwithListener(100,200); cannot add this way
//      When the UFOObserver recieved the lobsterMon died, we create some corresponding event and let input event queue handle it

        if (EnemydiedCount < 3) {
            InputEvent lobevent = new InputEvent();
            Random rand = new Random();
            int x = rand.nextInt(1100) + 50;
            int y = -100;
            lobevent.event = new EnemyCreateEvent("lobsterMon", x, y);
            lobevent.type = InputEvent.LOBM_CREATE; // let InputEvent handle it...
//            System.out.println(lobevent.event + "   " + lobevent.type);
            Main.playerInputEventQueue.queue.add(lobevent);
        }

        if (EnemydiedCount == 3) {
            InputEvent piranhaevent = new InputEvent();
            Random rand = new Random();
            int x = rand.nextInt(1100)+50;
            int y = -100;
            piranhaevent.event = new EnemyCreateEvent("piranhaMon", x, y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
            piranhaevent.type = InputEvent.PIRANHA_PHASE; // let InputEvent handle it...
            Main.playerInputEventQueue.queue.add(piranhaevent);

//            InputEvent piranhaevent2 = new InputEvent();
//
//            ttAddPiranha = new TimerTask() {
//                @Override
//                public void run() {
//                    Main.playerInputEventQueue.queue.add(piranhaevent2); // wait after delay time and add new
//                }
//            };
//            Main.delayGenerateTimer2.schedule(ttAddPiranha,rand.nextInt(2000)+2500); //(2.5-4.5second));
        }


        if (EnemydiedCount == 5) {
            InputEvent bossAppearEvent = new InputEvent();
            Random rand = new Random();
            int x = 100;
            int y = 100;
            bossAppearEvent.event = new EnemyCreateEvent("BossAppearWarning", x, y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
            bossAppearEvent.type = InputEvent.BOSS_APPEAR; // let InputEvent handle it...
            Main.playerInputEventQueue.queue.add(bossAppearEvent);
        }

        if (EnemydiedCount == 6) {
            InputEvent dsDevilEnterEvent = new InputEvent();
            Random rand = new Random();
            int x = 100;
            int y = 100;
            dsDevilEnterEvent.event = new EnemyCreateEvent("dsDevil", x, y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
            dsDevilEnterEvent.type = InputEvent.DSDEVIL_PHASE; // let InputEvent handle it...
            Main.playerInputEventQueue.queue.add(dsDevilEnterEvent);
        }

        if (EnemydiedCount == 7) {
            InputEvent CongratulationsEvent = new InputEvent();
            Random rand = new Random();
            int x = 100;
            int y = 100;
            CongratulationsEvent.event = new EnemyCreateEvent("Congratulations", x, y); // DOESN'T MATTER WHICH LOCATION, WILL BE OVERWRITTEN
            CongratulationsEvent.type = InputEvent.CONGRATULATIONS; // let InputEvent handle it...
            Main.playerInputEventQueue.queue.add(CongratulationsEvent);
        }
//        else if (LobdiedCount == 3 && callPiranhaMonOn == true) {
//            if (piranhaMon.currenttentacle1 == null) {
//                Random rand = new Random();
//                int x = rand.nextInt(1100) + 50;
//                int y = -100;
//                var piranM = new piranhaMon(x, y);
//                gameData.enemyObject.add(piranM);
//            }
//            if (piranhaMon.currenttentacle1 != null && piranhaMon.currenttentacle2 == null) {
//                ttAddPiranha2 = new TimerTask() {
//                    @Override
//                    public void run() {
//                        Random rand = new Random();
//                        int x = rand.nextInt(1100) + 50;
//                        int y = -100;
//                        var piranM = new piranhaMon(x, y);
//                        gameData.enemyObject.add(piranM);   // wait after delay time and add new
//                    }
//                };
//                Random rand = new Random();
//                Main.delayGenerateTimer2.schedule(ttAddPiranha2,rand.nextInt(2000)+2500); //(2.5-4.5second));


//            }
//        }

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

