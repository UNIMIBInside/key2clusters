package it.disco.unimib.ewshopp.key2cluster.configuration;

import it.disco.unimib.ewshopp.key2cluster.components.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.components.RedisDataManager;
import it.disco.unimib.ewshopp.key2cluster.configuration.ConfigProperties;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
@Profile(value = "REDIS")
public class RedisApplicationConfig {

    private final JedisConnectionFactory connectionFactory;

    private final ConfigProperties properties;

    private final RedisRepo repository;

    public RedisApplicationConfig(JedisConnectionFactory connectionFactory, ConfigProperties properties, RedisRepo repository) {
        this.connectionFactory = connectionFactory;
        this.properties = properties;
        this.repository = repository;
    }

    @Bean
    public IDataManager redisLoader(){
        return new RedisDataManager(repository, properties, connectionFactory);
    }

}