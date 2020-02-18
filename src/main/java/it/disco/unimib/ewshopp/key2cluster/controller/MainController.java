package it.disco.unimib.ewshopp.key2cluster.controller;

import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategories;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.*;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("key2cluster/api")
public class MainController {

    private final IDataManager dataManager;

    public MainController(IDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @GetMapping(value = "/keyword/{key}", produces = "application/json;charset=UTF-8")
    public KeywordCategories getCategories(@PathVariable("key") String key) {
        return dataManager.findCategoriesForAKeyword(key);
    }

    @GetMapping(value = "/keywords", produces = "application/json;charset=UTF-8")
    public List<KeywordCategories> getMultiKeyAsListToCategory(@RequestParam(value = "kws") String keywords){

        List<String> lst = Arrays.asList(keywords.split(","));
        return getMultiKeyCategory(lst);
    }

    @GetMapping(value = "/keyword", produces = "application/json;charset=UTF-8")
    public List<KeywordCategories> getMultiKeyCategory(@RequestBody List<String> lstString){

        return dataManager.findCategoriesForMultiKeyword(lstString);
    }


}
