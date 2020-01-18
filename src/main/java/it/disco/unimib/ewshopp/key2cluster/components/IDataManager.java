package it.disco.unimib.ewshopp.key2cluster.components;

import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;

public interface IDataManager {

    void loadData();

    KeywordCategories findCategories(String key);

}
