package providers;

import domain.exeptions.UnprovidedException;

/**
 * Base provider interface
 */
public interface IProviderBase {
    /**
     * Check if required services are provided
     *
     * @param providers Provider services
     * @param <T>       Provider type
     * @throws UnprovidedException UnprovidedException
     */
    default <T> void checkProviders(T ... providers) throws UnprovidedException {
        for (T provider : providers) {
            if (provider == null) {
                String s = providers.getClass().getSimpleName();
                s = s.substring(0, s.length() - 2);
                throw new UnprovidedException(s, this.getClass().getSimpleName());
            }
        }
    }
}
