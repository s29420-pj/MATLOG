package pl.pjatk.MATLOG;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestInfrastructure {

    @Bean
    @ServiceConnection
    MongoDBContainer mongoContainer() {
        var container = new MongoDBContainer(DockerImageName.parse("mongo:latest"));
        container.start();
        return container;
    }
}
