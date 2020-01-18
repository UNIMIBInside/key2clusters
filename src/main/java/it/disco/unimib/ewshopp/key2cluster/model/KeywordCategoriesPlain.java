package it.disco.unimib.ewshopp.key2cluster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("keycluster")
public class KeywordCategoriesPlain {
    @Id String keyword;
    String categories;
}
