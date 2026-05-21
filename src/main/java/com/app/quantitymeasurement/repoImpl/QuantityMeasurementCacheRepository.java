//package com.app.quantitymeasurement.repoImpl;
//
//import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
//import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.stream.Collectors;
//
//public class QuantityMeasurementCacheRepository implements QuantityMeasurementRepository {
//
//    private static final QuantityMeasurementCacheRepository INSTANCE = new QuantityMeasurementCacheRepository();
//
//    public static QuantityMeasurementCacheRepository getInstance() {
//        return INSTANCE;
//    }
//
//    private final List<QuantityMeasurementEntity> store = Collections.synchronizedList(new ArrayList<>());
//    private final AtomicLong idGen = new AtomicLong(1);
//
//    public QuantityMeasurementCacheRepository() { }
//
//    @Override
//    public <S extends QuantityMeasurementEntity> S save(S entity) {
//        if (entity.getId() == null) {
//            entity.setId(idGen.getAndIncrement());
//        }
//        store.removeIf(e -> Objects.equals(e.getId(), entity.getId()));
//        store.add(entity);
//        return entity;
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findAll() {
//        return new ArrayList<>(store);
//    }
//
//    @Override
//    public void deleteAll() {
//        store.clear();
//    }
//
//    @Override
//    public long count() {
//        return store.size();
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
//        if (measurementType == null) return Collections.emptyList();
//        return store.stream()
//                .filter(e -> measurementType.equalsIgnoreCase(e.getMeasurementType()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findByOperation(String operation) {
//        if (operation == null) return Collections.emptyList();
//        return store.stream()
//                .filter(e -> operation.equalsIgnoreCase(e.getOperation()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public long countByOperation(String operation) {
//        if (operation == null) return 0L;
//        return store.stream()
//                .filter(e -> operation.equalsIgnoreCase(e.getOperation()))
//                .count();
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> getAllMeasurements() {
//        return findAll();
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
//        return findByOperation(operation);
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
//        return findByMeasurementType(type);
//    }
//
//    @Override
//    public long getTotalCount() {
//        return count();
//    }
//
//    @Override
//    public boolean schemaExists() {
//        return true;
//    }
//
//    @Override
//    public void forceError() {
//        throw new RuntimeException("Forced error for testing");
//    }
//}
