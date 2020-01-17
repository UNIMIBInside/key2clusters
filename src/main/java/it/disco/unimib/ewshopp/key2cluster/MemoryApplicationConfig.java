package it.disco.unimib.ewshopp.key2cluster;

import it.disco.unimib.ewshopp.key2cluster.components.IClusterLoaders;
import it.disco.unimib.ewshopp.key2cluster.components.MemoryLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("MEMORY")
public class MemoryApplicationConfig {


    @Bean
    public IClusterLoaders memoryClusterLoader(ConfigProperties properties){
        return new MemoryLoader(properties);
    }

}
