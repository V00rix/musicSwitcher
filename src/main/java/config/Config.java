package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import providers.player.api.IPlayerProvider;
import providers.player.implementation.PlayerProvider;
import providers.status.api.IStatusProvider;
import providers.status.implementation.StatusProvider;

/**
 * Spring beans configuration
 */
@Configuration
public class Config {
    private IStatusProvider statusProvider;
    private IPlayerProvider playerProvider;

    /**
     * Register status provider
     * @return Status provider instance
     */
    @Bean
    public IStatusProvider statusProvider() {
        return this.statusProvider = this.statusProvider == null ? new StatusProvider() : this.statusProvider;
    }

    /**
     * Register player provider
     * @return Player provider instance
     * @throws Exception Exception
     */
    @Bean
    public IPlayerProvider playerProvider() throws Exception {
        return this.playerProvider = this.playerProvider == null ? new PlayerProvider(this.statusProvider) : this.playerProvider;
    }

    // todo: Declare beans here
}