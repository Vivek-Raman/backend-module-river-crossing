package dev.vivekraman.rivercrossing.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "dev.vivekraman.*")
public class BackendModuleRiverCrossingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendModuleRiverCrossingApplication.class, args);
	}
}
