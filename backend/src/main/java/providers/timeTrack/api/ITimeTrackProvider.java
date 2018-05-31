package providers.timeTrack.api;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Time track provider
 */
public interface ITimeTrackProvider {
    /**
     * Wrap a callable into a tracker
     *
     * @param f   Callable  function
     * @param <T> Return type of a callable
     * @return Callable's result
     * @throws Exception Inner exception
     */
    default <T> T track(Callable<T> f) throws Exception {
        return track(f, "Unanimous operation");
    }

    /**
     * Wrap a callable into a tracker
     *
     * @param f        Callable  function
     * @param taskName Task name
     * @param <T>      Return type of a callable
     * @return Callable's result
     * @throws Exception Inner exception
     */
    default <T> T track(Callable<T> f, String taskName) throws Exception {
        return track(f, taskName + " has started at %s",
                taskName + " has finished at %s.\n" + taskName + " has taken %s");
    }

    /**
     * Wrap a callable into a tracker
     *
     * @param f            Callable  function
     * @param startMessage Message on execution start
     * @param endMessage   Message on execution end
     * @param <T>          Return type of a callable
     * @return Callable's result
     * @throws Exception Inner exception
     */
    <T> T track(Callable<T> f, String startMessage, String endMessage) throws Exception;

    /**
     * Tracks operations progress performed on a list
     *
     * @param items    Items
     * @param function Function
     * @param taskName Task name
     * @param <T>      Input array type
     * @param <R>      Return array type
     * @return New array
     * @throws Exception Exception
     */
    default <T, R> ArrayList<R> trackProgress(ArrayList<T> items, Function<T, R> function, String taskName) throws Exception {
        return this.trackProgress(items, new TriFunction<T, Double, String, R>() {
            @Override
            public R apply(T t, Double aDouble, String s) {
                return function.apply(t);
            }
        }, taskName);
    }

    /**
     * Tracks operations progress performed on a list
     *
     * @param items    Items
     * @param function Function
     * @param taskName Task name
     * @param <T> Input array type
     * @param <R> Return array type
     * @return New array
     * @throws Exception Exception
     */
    <T, R> ArrayList<R> trackProgress(ArrayList<T> items, TriFunction<T, Double, String, R> function, String taskName) throws Exception;

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {

        R apply(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> andThen(
                Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }
}
