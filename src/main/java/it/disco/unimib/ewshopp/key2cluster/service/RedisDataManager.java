package it.disco.unimib.ewshopp.key2cluster.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.google.common.primitives.Booleans;
import io.rebloom.client.Client;
import it.disco.unimib.ewshopp.key2cluster.configuration.GeneralConfig;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategoriesPlain;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepository;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.util.Pair;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Log
public class RedisDataManager implements IDataManager {

    private final GeneralConfig properties;

    private final RedisRepository repository;

    private final Client filter;

    public RedisDataManager(RedisRepository repository, GeneralConfig properties, JedisConnectionFactory connectionFactory) {
        this.repository = repository;
        this.properties = properties;
        JedisPool pool = new JedisPool(connectionFactory.getPoolConfig(), connectionFactory.getHostName(), connectionFactory.getPort(), 10000, connectionFactory.getPassword());
        filter = new Client(pool);
    }

    @SneakyThrows
    @Override
    @PostConstruct
    public void loadData() {

        if (properties.isRecreateDataStructure()){
        repository.deleteAll();
        log.info(String.valueOf(repository.count()));

        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/" + properties.getFilename())));
        br.readLine(); //remove header
        String line;



        filter.delete("specialBloom");
        filter.createFilter("specialBloom", 350000, 0.001);

        int totEntries = 0;
        while ((line = br.readLine()) != null) {

            Stream<String> row = Arrays.stream(line.split(",", 2));
            List<String> lst = row.map(s -> s.trim()).collect(Collectors.toList());
            String key = lst.get(0);
            String categories = lst.get(1);

            repository.save(new KeywordCategoriesPlain(key, categories));
            filter.add("specialBloom", key);
            totEntries += 1;
            if (totEntries % 1000 == 0) log.info("loaded " + totEntries);
        }

        log.info("Loaded " + repository.count() + " keywords");

    }
    }

    @Override
    public long countKeywords() {
        return repository.count();
    }

    @Override
    public KeywordCategories findCategoriesForAKeyword(String key) {
        String clusterCategories = "";
        if (filter.exists("specialBloom",key)){
            clusterCategories = repository.findById(key)
                    .orElseGet(()->new KeywordCategoriesPlain(key, ""))
                    .getCategories();
        }
        List<String> lstCategories = Arrays.stream(clusterCategories.split(","))
                .map(s->s.trim()).collect(Collectors.toList());
        return new KeywordCategories(key, lstCategories);
    }

    @Override
    public List<KeywordCategories> findCategoriesForMultiKeyword(Iterable<String> itString) {
        boolean[] results = filter.existsMulti("specialBloom", Iterables.toArray(itString, String.class));

        return Streams.zip(
                StreamSupport.stream(itString.spliterator(), false),
                Booleans.asList(results).stream(),
                Pair::of
        ).map(this::generateKeywordCategories)
        .collect(Collectors.toList());

    }



}
