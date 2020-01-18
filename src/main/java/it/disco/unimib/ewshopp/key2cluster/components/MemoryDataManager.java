package it.disco.unimib.ewshopp.key2cluster.components;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import it.disco.unimib.ewshopp.key2cluster.configuration.ConfigProperties;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import vlsi.utils.CompactHashMap;

import javax.annotation.PostConstruct;
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
public class MemoryDataManager implements IDataManager {

    private final ConfigProperties properties;
    private final BloomFilter<CharSequence> filter;
    private final Map<String, String> dictionary;

    public MemoryDataManager(ConfigProperties properties) {
        this.properties = properties;
        filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                350000,
                0.001);
       dictionary = new CompactHashMap<>();
    }

    @SneakyThrows
    @Override
    @PostConstruct
    public void loadData() {

        log.info("Creating in-memory dictionary");


        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/" + properties.getFilename())));
        br.readLine(); //remove header
        String line;



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

    @Override
    public long countKeywords() {
        return dictionary.size();
    }

    @Override
    public KeywordCategories findCategories(String key) {
        String clusterCategories = "";
        if (filter.mightContain(key)){
            clusterCategories = dictionary.getOrDefault(key, "");
        }
        List<String> lstCategories = Arrays.stream(clusterCategories.split(","))
                .map(s->s.trim()).collect(Collectors.toList());
        return new KeywordCategories(key, lstCategories);
    }
}
