package controller.starobserver;

public interface StarSubject {

    void attachListener(StarObserver o);// how to attach listener to this subject
    void detachListener(StarObserver o);
    void notifyEvent();
}
