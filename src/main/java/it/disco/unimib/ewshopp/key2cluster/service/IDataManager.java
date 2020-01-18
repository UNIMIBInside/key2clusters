package it.disco.unimib.ewshopp.key2cluster.service;

import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import org.springframework.data.util.Pair;

import java.util.Collections;
import java.util.List;

public interface IDataManager {

    void loadData();


    long countKeywords();

    KeywordCategories findCategoriesForAKeyword(String key);

    List<KeywordCategories> findCategoriesForMultiKeyword(Iterable<String> itString);

    default KeywordCategories generateKeywordCategories(Pair<String, Boolean> p) {
        String key = p.getFirst();
        Boolean exists = p.getSecond();
        return exists ?
                findCategoriesForAKeyword(key) :
                new KeywordCategories(p.getFirst(), Collections.emptyList());
    }

}
