package config;

import domain.exeptions.UnprovidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import providers.library.api.ILibraryProvider;
import providers.library.implementation.LibraryProvider;
import providers.playList.api.IPlayListProvider;
import providers.playList.implementation.PlayListProvider;
import providers.player.api.IPlayerProvider;
import providers.player.implementation.PlayerProvider;
import providers.status.api.IStatusProvider;
import providers.status.implementation.StatusProvider;
import providers.timeTrack.api.ITimeTrackProvider;
import providers.timeTrack.implementation.TimeTrackProvider;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring beans configuration
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {
    //region Fields

    private IStatusProvider statusProvider;
    private IPlayerProvider playerProvider;
    private IPlayListProvider playListProvider;
    private ILibraryProvider libraryProvider;
    private ITimeTrackProvider timeTrackProvider;

    //endregion


    @Autowired
    private Environment environment;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor());
    }

    //region Beans

    /**
     * Register status provider
     *
     * @return Status provider instance
     */
    @Bean
    public IStatusProvider statusProvider() {
        return this.statusProvider = this.statusProvider == null ? new StatusProvider() : this.statusProvider;
    }


    /**
     * Register time track provider
     *
     * @return Time track provider instance
     * @throws UnprovidedException UnprovidedException
     */
    @Bean
    public ITimeTrackProvider timeTrackProvider() throws UnprovidedException {
        return this.timeTrackProvider = this.timeTrackProvider == null ? new TimeTrackProvider(this.statusProvider) : this.timeTrackProvider;
    }

    /**
     * Register player provider
     *
     * @return Player provider instance
     * @throws Exception Exception
     */
    @Bean
    public IPlayerProvider playerProvider() throws Exception {
        return this.playerProvider = this.playerProvider == null ? new PlayerProvider(this.statusProvider) : this.playerProvider;
    }

    /**
     * Register library provider
     *
     * @return Library provider instance
     * @throws UnprovidedException UnprovidedException
     */
    @Bean
    public ILibraryProvider libraryProvider() throws UnprovidedException {
        return this.libraryProvider = this.libraryProvider == null ? new LibraryProvider(this.statusProvider, this.timeTrackProvider) : this.libraryProvider;
    }

    /**
     * Register play list provider
     *
     * @return Play list provider instance
     * @throws UnprovidedException UnprovidedException
     */
    @Bean
    public IPlayListProvider playListProvider() throws UnprovidedException {
        return this.playListProvider = this.playListProvider == null ? new PlayListProvider(this.statusProvider, this.playerProvider, this.libraryProvider) : this.playListProvider;
    }

    //endregion
}

