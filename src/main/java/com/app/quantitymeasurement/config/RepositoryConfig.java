//package com.app.quantitymeasurement.config;
//
//import com.app.quantitymeasurement.database.ConnectionPool;
//import com.app.quantitymeasurement.repoImpl.QuantityMeasurementCacheRepository;
//import com.app.quantitymeasurement.repoImpl.QuantityMeasurementDatabaseRepository;
//import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//
//@Configuration
//public class RepositoryConfig {
//
//    @Bean
//    @Profile("!test-db")
//    public QuantityMeasurementRepository cacheRepository() {
//        return new QuantityMeasurementCacheRepository();
//    }
//
//    @Bean
//    @Profile("test-db")
//    public QuantityMeasurementRepository databaseRepository(ConnectionPool pool) {
//        QuantityMeasurementDatabaseRepository repo = new QuantityMeasurementDatabaseRepository(pool);
//        repo.initializeSchema();
//        return repo;
//    }
//
//    @Bean
//    @Profile("test-db")
//    public ConnectionPool connectionPool() {
//        return new ConnectionPool();
//    }
//}
