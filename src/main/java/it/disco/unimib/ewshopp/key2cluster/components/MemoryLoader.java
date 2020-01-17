package it.disco.unimib.ewshopp.key2cluster.components;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import io.rebloom.client.Client;
import it.disco.unimib.ewshopp.key2cluster.ConfigProperties;
import it.disco.unimib.ewshopp.key2cluster.model.KeyCluster;
import it.disco.unimib.ewshopp.key2cluster.repository.RedisRepo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import vlsi.utils.CompactHashMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Log
public class MemoryLoader implements IClusterLoaders {

    private final ConfigProperties properties;

    public MemoryLoader(ConfigProperties properties) {
        this.properties = properties;
    }

    @SneakyThrows
    @Override
    public void loadData() {

        log.info("Creating in-memory dictionary");

        Map<String, String> dictionary = new CompactHashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/" + properties.getFilename())));
        br.readLine(); //remove header
        String line;

        BloomFilter<String> filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                350000,
                0.001);

        int totEntries = 0;
        while ((line = br.readLine()) != null) {

            Stream<String> row = Arrays.stream(line.split(",", 2));
            List<String> lst = row.map(s -> s.trim()).collect(Collectors.toList());
            String key = lst.get(0);
            String categories = lst.get(1);

            dictionary.put(key, categories);
            filter.put(key);
            totEntries += 1;
            if (totEntries % 1000 == 0) log.info("loaded " + totEntries);
        }

        log.info("Loaded " + dictionary.size() + " keywords");

    }
}
