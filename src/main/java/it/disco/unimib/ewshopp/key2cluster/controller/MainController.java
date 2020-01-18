package it.disco.unimib.ewshopp.key2cluster.controller;

import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("key2cluster/api")
public class MainController {

    private final IDataManager dataManager;

    public MainController(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @GetMapping("/keyword/{key}")
    public KeywordCategories getCategories(@PathVariable("key") String key) {
        return dataManager.findCategoriesForAKeyword(key);
    }


    @GetMapping("/keyword")
    public List<KeywordCategories> getMultiKeyCategory(@RequestBody List<String> lstString){

        return dataManager.findCategoriesForMultiKeyword(lstString);
    }


}
