package it.disco.unimib.ewshopp.key2cluster.repository;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("MEMORY")
@EnableAutoConfiguration(exclude= RedisAutoConfiguration.class)
public class ApplicationConfigMemory {
}
