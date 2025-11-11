package pucp.edu.pe.tikea.tikeabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class TikeaBackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(TikeaBackendApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(TikeaBackendApplication.class, args);
        logger.info("TikeaBackendApplication started");
    }
}
