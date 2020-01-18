package it.disco.unimib.ewshopp.key2cluster.repository;


import it.disco.unimib.ewshopp.key2cluster.model.KeywordCategoriesPlain;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<KeywordCategoriesPlain, String> {
}
