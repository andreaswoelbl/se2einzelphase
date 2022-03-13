package at.aau.se2.einzelphase.network;

/**
 * @author Andreas Wölbl
 * @param <T>
 */
public interface Callback<T> {
    void notify(T t);
}
