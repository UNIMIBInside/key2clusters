package it.disco.unimib.ewshopp.key2cluster.configuration;

import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.service.RedisDataManager;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepository;
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

    private final GeneralConfig properties;

    private final RedisRepository repository;

    public RedisApplicationConfig(JedisConnectionFactory connectionFactory, GeneralConfig properties, RedisRepository repository) {
        this.connectionFactory = connectionFactory;
        this.properties = properties;
        this.repository = repository;
    }

    @Bean
    public IDataManager redisLoader(){
        return new RedisDataManager(repository, properties, connectionFactory);
    }

}