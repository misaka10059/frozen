package ccsah.frozen.iot;

import ccsfr.boot.ClusterApplication;
import ccsfr.boot.annotation.EnableMQ;
import ccsfr.boot.annotation.EnableRMIClient;
import ccsfr.boot.annotation.EnableRMIServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AUTHOR MisakaNetwork
 * DATE 2019/12/2 20:00
 * DESC
 */
@SpringBootApplication
@EnableMQ
@EnableRMIServer
@EnableRMIClient
public class Application extends ClusterApplication {
    @Autowired
    private ApplicationArguments applicationArguments;

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

}
