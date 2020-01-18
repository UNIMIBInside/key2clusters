package it.disco.unimib.ewshopp.key2cluster.configuration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keyword.cluster")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperties {

    private String filename;

    private boolean recreateDataStructure;

}