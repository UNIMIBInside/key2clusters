package it.disco.unimib.ewshopp.key2cluster.configuration;

import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.service.MemoryDataManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("MEMORY")
public class MemoryApplicationConfig {


    @Bean
    public IDataManager memoryClusterLoader(GeneralConfig properties){
        return new MemoryDataManager(properties);
    }

}
