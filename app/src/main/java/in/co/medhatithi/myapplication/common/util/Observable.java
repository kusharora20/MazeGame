package in.co.medhatithi.myapplication.common.util;

public interface Observable<ListenerType> {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
