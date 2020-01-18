package it.disco.unimib.ewshopp.key2cluster.components;

import io.rebloom.client.Client;
import it.disco.unimib.ewshopp.key2cluster.ConfigProperties;
import it.disco.unimib.ewshopp.key2cluster.model.KeyCluster;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Log
public class RedisLoader implements IClusterLoaders {

    private final JedisConnectionFactory connectionFactory;

    private final ConfigProperties properties;

    private final RedisRepo repository;

    public RedisLoader(RedisRepo repository, ConfigProperties properties, JedisConnectionFactory connectionFactory) {
        this.repository = repository;
        this.properties = properties;
        this.connectionFactory = connectionFactory;
    }

    @SneakyThrows
    @Override
    public void loadData() {

        repository.deleteAll();
        log.info(String.valueOf(repository.count()));

        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/" + properties.getFilename())));
        br.readLine(); //remove header
        String line;

        JedisPool pool = new JedisPool(connectionFactory.getPoolConfig(), connectionFactory.getHostName(), connectionFactory.getPort(), 10000, connectionFactory.getPassword());

        Client client = new Client(pool);

        client.delete("specialBloom");
        client.createFilter("specialBloom", 350000, 0.001);

        int totEntries = 0;
        while ((line = br.readLine()) != null) {

            Stream<String> row = Arrays.stream(line.split(",", 2));
            List<String> lst = row.map(s -> s.trim()).collect(Collectors.toList());
            String key = lst.get(0);
            String categories = lst.get(1);

            repository.save(new KeyCluster(key, categories));
            client.add("specialBloom", key);
            totEntries += 1;
            if (totEntries % 1000 == 0) log.info("loaded " + totEntries);
        }

        log.info("Loaded " + repository.count() + " keywords");

    }
}