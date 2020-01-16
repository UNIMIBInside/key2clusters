package it.disco.unimib.ewshopp.key2cluster;

import io.rebloom.client.Client;
import io.rebloom.client.ClusterClient;
import it.disco.unimib.ewshopp.key2cluster.model.KeyCluster;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepo;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.PoolConfig;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.util.ResourceUtils;

import redis.clients.jedis.JedisPool;
import vlsi.utils.CompactHashMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Log
public class Key2clusterApplication {

	@Autowired(required = false)
	private RedisRepo repository;

	@Autowired(required = false)
	private JedisConnectionFactory connectionFactory;
	

	@Autowired
	private Environment environment;

	@Autowired(required = false)
	private ConfigProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(Key2clusterApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws IOException {
		if (Objects.nonNull(properties)) {
			log.info("Dictionary file name: " + properties.getFilename());
			log.info("Selected storage medium: " + environment.getActiveProfiles());
		}

		if (Arrays.asList(environment.getActiveProfiles()).contains("REDIS")) {


	}
}
