package it.disco.unimib.ewshopp.key2cluster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeywordCategories {

    private String keyword;
    private List<String> categories;
}
