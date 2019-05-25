package controller.enemyobserver;

public interface EnemySubject {

    void attachListener(EnemyObserver o);// how to attach listener to this subject
    void detachListener(EnemyObserver o);
    void notifyEvent();
}
