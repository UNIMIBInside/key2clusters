package it.disco.unimib.ewshopp.key2cluster.controller;

import it.disco.unimib.ewshopp.key2cluster.components.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ClusterServer {

    private final IDataManager dataManager;

    public ClusterServer(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @GetMapping("/keyword/{key}")
    public KeywordCategories getCategories(@PathVariable("key") String key) {
        return dataManager.findCategories(key);
    }
}
