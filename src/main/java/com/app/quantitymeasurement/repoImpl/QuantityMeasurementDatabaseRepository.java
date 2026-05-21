//package com.app.quantitymeasurement.repoImpl;
//
//import com.app.quantitymeasurement.database.ConnectionPool;
//import com.app.quantitymeasurement.exception.DatabaseException;
//import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
//import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class QuantityMeasurementDatabaseRepository implements QuantityMeasurementRepository {
//
//    private final ConnectionPool pool;
//
//    public QuantityMeasurementDatabaseRepository(ConnectionPool pool) {
//        this.pool = pool;
//    }
//
//    @Override
//    public <S extends QuantityMeasurementEntity> S save(S entity) {
//        final String sql = "INSERT INTO quantity_measurements (measurement_type, operation, input, result, timestamp) VALUES (?,?,?,?,?)";
//        try (Connection c = pool.acquire();
//             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setString(1, entity.getMeasurementType());
//            ps.setString(2, entity.getOperation());
//            ps.setString(3, entity.getInput());
//            ps.setString(4, entity.getResult());
//            ps.setTimestamp(5, Timestamp.valueOf(entity.getTimestamp()));
//            ps.executeUpdate();
//
//            try (ResultSet rs = ps.getGeneratedKeys()) {
//                if (rs.next()) {
//                    long generatedId = rs.getLong(1);
//                    entity.setId(generatedId);
//                }
//            }
//            return entity;
//        } catch (SQLException ex) {
//            throw new DatabaseException("Save failed", ex);
//        }
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findAll() {
//        final String sql = "SELECT id, measurement_type, operation, input, result, timestamp FROM quantity_measurements";
//        List<QuantityMeasurementEntity> list = new ArrayList<>();
//        try (Connection c = pool.acquire();
//             Statement st = c.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                QuantityMeasurementEntity e = new QuantityMeasurementEntity(
//                        rs.getString("measurement_type"),
//                        rs.getString("operation"),
//                        rs.getString("input"),
//                        rs.getString("result")
//                );
//                e.setId(rs.getLong("id"));
//                Timestamp ts = rs.getTimestamp("timestamp");
//                if (ts != null) e.setTimestamp(ts.toLocalDateTime());
//                list.add(e);
//            }
//            return list;
//        } catch (SQLException ex) {
//            throw new DatabaseException("Find all failed", ex);
//        }
//    }
//
//    @Override
//    public void deleteAll() {
//        final String sql = "DELETE FROM quantity_measurements";
//        try (Connection c = pool.acquire();
//             Statement st = c.createStatement()) {
//            st.executeUpdate(sql);
//        } catch (SQLException ex) {
//            throw new DatabaseException("Delete all failed", ex);
//        }
//    }
//
//    @Override
//    public long count() {
//        final String sql = "SELECT COUNT(*) FROM quantity_measurements";
//        try (Connection c = pool.acquire();
//             Statement st = c.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//            if (rs.next()) return rs.getLong(1);
//            return 0L;
//        } catch (SQLException ex) {
//            throw new DatabaseException("Count failed", ex);
//        }
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
//        final String sql = "SELECT id, measurement_type, operation, input, result, timestamp FROM quantity_measurements WHERE measurement_type = ?";
//        List<QuantityMeasurementEntity> list = new ArrayList<>();
//        try (Connection c = pool.acquire();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//
//            ps.setString(1, measurementType);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    QuantityMeasurementEntity e = new QuantityMeasurementEntity(
//                            rs.getString("measurement_type"),
//                            rs.getString("operation"),
//                            rs.getString("input"),
//                            rs.getString("result")
//                    );
//                    e.setId(rs.getLong("id"));
//                    Timestamp ts = rs.getTimestamp("timestamp");
//                    if (ts != null) e.setTimestamp(ts.toLocalDateTime());
//                    list.add(e);
//                }
//            }
//            return list;
//        } catch (SQLException ex) {
//            throw new DatabaseException("Find by type failed", ex);
//        }
//    }
//
//    @Override
//    public List<QuantityMeasurementEntity> findByOperation(String operation) {
//        final String sql = "SELECT id, measurement_type, operation, input, result, timestamp FROM quantity_measurements WHERE operation = ?";
//        List<QuantityMeasurementEntity> list = new ArrayList<>();
//        try (Connection c = pool.acquire();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//
//            ps.setString(1, operation);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    QuantityMeasurementEntity e = new QuantityMeasurementEntity(
//                            rs.getString("measurement_type"),
//                            rs.getString("operation"),
//                            rs.getString("input"),
//                            rs.getString("result")
//                    );
//                    e.setId(rs.getLong("id"));
//                    Timestamp ts = rs.getTimestamp("timestamp");
//                    if (ts != null) e.setTimestamp(ts.toLocalDateTime());
//                    list.add(e);
//                }
//            }
//            return list;
//        } catch (SQLException ex) {
//            throw new DatabaseException("Find by operation failed", ex);
//        }
//    }
//
//    @Override
//    public long countByOperation(String operation) {
//        final String sql = "SELECT COUNT(*) FROM quantity_measurements WHERE operation = ?";
//        try (Connection c = pool.acquire();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//
//            ps.setString(1, operation);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) return rs.getLong(1);
//                return 0L;
//            }
//        } catch (SQLException ex) {
//            throw new DatabaseException("Count by operation failed", ex);
//        }
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
//        try (Connection c = pool.acquire()) {
//            DatabaseMetaData meta = c.getMetaData();
//            try (ResultSet rs = meta.getTables(null, null, "quantity_measurements", null)) {
//                if (rs.next()) return true;
//            }
//            try (ResultSet rs2 = meta.getTables(null, null, "QUANTITY_MEASUREMENTS", null)) {
//                return rs2.next();
//            }
//        } catch (SQLException ex) {
//            throw new DatabaseException("Schema check failed", ex);
//        }
//    }
//
//    @Override
//    public void forceError() {
//        throw new RuntimeException("Forced error for testing");
//    }
//
//    public void initializeSchema() {
//        final String sql = "CREATE TABLE IF NOT EXISTS quantity_measurements (" +
//                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
//                "measurement_type VARCHAR(255), " +
//                "operation VARCHAR(255), " +
//                "input VARCHAR(1024), " +
//                "result VARCHAR(1024), " +
//                "timestamp TIMESTAMP)";
//        try (Connection c = pool.acquire();
//             Statement st = c.createStatement()) {
//            st.executeUpdate(sql);
//        } catch (SQLException ex) {
//            throw new DatabaseException("Schema init failed", ex);
//        }
//    }
//}
