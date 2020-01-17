package it.disco.unimib.ewshopp.key2cluster;

import it.disco.unimib.ewshopp.key2cluster.components.IClusterLoaders;
import it.disco.unimib.ewshopp.key2cluster.components.RedisLoader;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
    public IClusterLoaders redisLoader(){
        return new RedisLoader(repository, properties, connectionFactory);
    }

}