package by.hembar.idservice.helper;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PropertiesBean extends Properties {
    protected PropertiesBean(@Value("${host:localhost}")
                             String host,
                             @Value("${port:8080}")
                             Integer port,
                             @Value("${host.id-service:}")
                             String idServiceHost,
                             @Value("${port.id-service:}")
                             Integer idServicePort,
                             @Value("${host.chat-service:}")
                             String chatServiceHost,
                             @Value("${port.chat-service:}")
                             Integer chatServicePort,
                             @Value("${host.news-service:}")
                             String newsServiceHost,
                             @Value("${port.news-service:}")
                             Integer newsServicePort,
                             @Value("${host.search-service:}")
                             String searchServiceHost,
                             @Value("${port.search-service:}")
                             Integer searchServicePort,
                             @Value("${host.storage-service:}")
                             String storageServiceHost,
                             @Value("${port.storage-service:}")
                             Integer storageServicePort,
                             @Value("${jwt.secret-key:}")
                             String secretKey,
                             @Value("${session.life.time:}")
                             Long sessionLifeTime,
                             @Value("${jwt.life.time:}")
                             Long jwtLifeTime,
                             @Value("${user.block.time:}")
                             Long userBlockTime,
                             @Value("${user.admin.key:")
                             String adminKey,
                             @Value("${user.admin.id-list:")
                             String adminIdList
    ) {
        super(host, port,
                idServiceHost, idServicePort,
                chatServiceHost, chatServicePort,
                newsServiceHost, newsServicePort,
                searchServiceHost, searchServicePort,
                storageServiceHost, storageServicePort,
                secretKey, sessionLifeTime, jwtLifeTime, userBlockTime,
                adminKey,
                Arrays.stream(adminIdList.split(",")).map(id -> Long.parseLong(id.trim())).toList()
        );
    }


    @PostConstruct
    public void initProperties() {
        Properties.setInstance(this);
    }
}
