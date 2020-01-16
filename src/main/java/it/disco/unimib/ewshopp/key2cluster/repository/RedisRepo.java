package it.disco.unimib.ewshopp.key2cluster.repository;


import it.disco.unimib.ewshopp.key2cluster.model.KeyCluster;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepo extends CrudRepository<KeyCluster, String> {
}
