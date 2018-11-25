package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("oAuth2Authentication", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".roles", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".utilisateurs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".commentaires", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".formulaires", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Utilisateur.class.getName() + ".reservations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Formulaire.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Formulaire.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Question.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Question.class.getName() + ".reservations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Question.class.getName() + ".formulaires", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TypeQuestion.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TypeQuestion.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Reservation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Reservation.class.getName() + ".questions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Reservation.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Reservation.class.getName() + ".autresInformations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.AutresInformations.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Role.class.getName() + ".utilisateurs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Commentaire.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Formation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Formation.class.getName() + ".formulaires", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Formation.class.getName() + ".reservations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CentreDeFormation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CentreDeFormation.class.getName() + ".formations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TypeDeNotification.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TypeDeNotification.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CategorieFormation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.CategorieFormation.class.getName() + ".formations", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
