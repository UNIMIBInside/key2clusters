package it.disco.unimib.ewshopp.key2cluster;

import it.disco.unimib.ewshopp.key2cluster.service.IDataManager;
import it.disco.unimib.ewshopp.key2cluster.configuration.GeneralConfig;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
@Log
public class Key2clusterApplication {

	@Autowired(required = false)
	private IDataManager clusterLoaders;

	@Autowired
	private Environment environment;

	@Autowired(required = false)
	private GeneralConfig properties;

	public static void main(String[] args) {
		SpringApplication.run(Key2clusterApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws IOException {
		if (Objects.nonNull(properties)) {
			log.info("Dictionary file name: " + properties.getFilename());
			log.info("Selected storage medium: " + environment.getActiveProfiles());
		}

//		clusterLoaders.loadData();

	}
}
