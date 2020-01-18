package it.disco.unimib.ewshopp.key2cluster.components;

import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;

public interface IDataManager {

    void loadData();


    long countKeywords();

    KeywordCategories findCategories(String key);

}
