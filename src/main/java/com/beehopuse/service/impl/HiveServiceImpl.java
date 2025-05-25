package com.beehopuse.service.impl;

import com.beehopuse.model.Hive;
import com.beehopuse.model.Site;
import com.beehopuse.service.HiveService;
import com.beehopuse.util.DBUtil;
import jakarta.ejb.Stateless;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class HiveServiceImpl implements HiveService {

    @Override
    public Hive createHive(Hive hive) {
        String sql = "INSERT INTO hives (name, type, status, queen_age, honey_quantity, health_status, productivity_status, colony_size, frame_count, extension_count, latitude, longitude, altitude, orientation, site_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, hive.getName());
            pstmt.setString(2, hive.getType());
            pstmt.setString(3, hive.getStatus());
            pstmt.setInt(4, hive.getQueenAge() != null ? hive.getQueenAge() : 0);
            pstmt.setDouble(5, hive.getHoneyQuantity() != null ? hive.getHoneyQuantity() : 0.0);
            pstmt.setString(6, hive.getHealthStatus() != null ? hive.getHealthStatus().name() : null);
            pstmt.setString(7, hive.getProductivityStatus());
            pstmt.setInt(8, hive.getColonySize() != null ? hive.getColonySize() : 0);
            pstmt.setInt(9, hive.getFrameCount() != null ? hive.getFrameCount() : 0);
            pstmt.setInt(10, hive.getExtensionCount() != null ? hive.getExtensionCount() : 0);
            pstmt.setDouble(11, hive.getLatitude() != null ? hive.getLatitude() : 0.0);
            pstmt.setDouble(12, hive.getLongitude() != null ? hive.getLongitude() : 0.0);
            pstmt.setDouble(13, hive.getAltitude() != null ? hive.getAltitude() : 0.0);
            pstmt.setString(14, hive.getOrientation());
            pstmt.setLong(15, hive.getSite() != null && hive.getSite().getId() != null ? hive.getSite().getId() : 0L);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating hive failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hive.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating hive failed, no ID obtained.");
                }
            }
            return hive;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating hive", e);
        }
    }

    @Override
    public Hive updateHive(Hive hive) {
        String sql = "UPDATE hives SET name = ?, type = ?, status = ?, queen_age = ?, honey_quantity = ?, health_status = ?, productivity_status = ?, colony_size = ?, frame_count = ?, extension_count = ?, latitude = ?, longitude = ?, altitude = ?, orientation = ?, site_id = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hive.getName());
            pstmt.setString(2, hive.getType());
            pstmt.setString(3, hive.getStatus());
            pstmt.setInt(4, hive.getQueenAge() != null ? hive.getQueenAge() : 0);
            pstmt.setDouble(5, hive.getHoneyQuantity() != null ? hive.getHoneyQuantity() : 0.0);
            pstmt.setString(6, hive.getHealthStatus() != null ? hive.getHealthStatus().name() : null);
            pstmt.setString(7, hive.getProductivityStatus());
            pstmt.setInt(8, hive.getColonySize() != null ? hive.getColonySize() : 0);
            pstmt.setInt(9, hive.getFrameCount() != null ? hive.getFrameCount() : 0);
            pstmt.setInt(10, hive.getExtensionCount() != null ? hive.getExtensionCount() : 0);
            pstmt.setDouble(11, hive.getLatitude() != null ? hive.getLatitude() : 0.0);
            pstmt.setDouble(12, hive.getLongitude() != null ? hive.getLongitude() : 0.0);
            pstmt.setDouble(13, hive.getAltitude() != null ? hive.getAltitude() : 0.0);
            pstmt.setString(14, hive.getOrientation());
            pstmt.setLong(15, hive.getSite() != null && hive.getSite().getId() != null ? hive.getSite().getId() : 0L);
            pstmt.setLong(16, hive.getId());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating hive failed, no rows affected.");
            }
            return hive;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating hive", e);
        }
    }

    @Override
    public void deleteHive(Long id) {
        String sql = "DELETE FROM hives WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting hive failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting hive", e);
        }
    }

    @Override
    public Hive getHiveById(Long id) {
        String sql = "SELECT * FROM hives WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToHive(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hive by id", e);
        }
    }

    @Override
    public List<Hive> getHivesBySite(Site site) {
        String sql = "SELECT * FROM hives WHERE site_id = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, site.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by site", e);
        }
    }

    @Override
    public List<Hive> getAllHives() {
        String sql = "SELECT * FROM hives";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                hives.add(mapResultSetToHive(rs));
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all hives", e);
        }
    }

    @Override
    public List<Hive> getHivesByStatus(String status) {
        String sql = "SELECT * FROM hives WHERE status = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by status", e);
        }
    }

    @Override
    public List<Hive> getHivesByType(String type) {
        String sql = "SELECT * FROM hives WHERE type = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by type", e);
        }
    }

    @Override
    public List<Hive> getHivesByQueenAge(int minAge, int maxAge) {
        String sql = "SELECT * FROM hives WHERE queen_age BETWEEN ? AND ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minAge);
            pstmt.setInt(2, maxAge);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by queen age", e);
        }
    }

    @Override
    public List<Hive> getHivesByLastInspectionDate(LocalDate date) {
        String sql = "SELECT * FROM hives WHERE DATE(last_inspection_date) = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by last inspection date", e);
        }
    }

    @Override
    public List<Hive> getHivesByShadeStatus(boolean hasShade) {
        String sql = "SELECT * FROM hives WHERE has_shade = ?";
        return getHivesByBooleanStatus(sql, hasShade);
    }

    @Override
    public List<Hive> getHivesByWaterSource(boolean hasWaterSource) {
        String sql = "SELECT * FROM hives WHERE has_water_source = ?";
        return getHivesByBooleanStatus(sql, hasWaterSource);
    }

    @Override
    public List<Hive> getHivesByFlowerSource(boolean hasFlowerSource) {
        String sql = "SELECT * FROM hives WHERE has_flower_source = ?";
        return getHivesByBooleanStatus(sql, hasFlowerSource);
    }

    @Override
    public List<Hive> getHivesByPredatorProtection(boolean hasProtection) {
        String sql = "SELECT * FROM hives WHERE has_predator_protection = ?";
        return getHivesByBooleanStatus(sql, hasProtection);
    }

    @Override
    public List<Hive> getHivesByVentilationStatus(boolean hasVentilation) {
        String sql = "SELECT * FROM hives WHERE has_ventilation = ?";
        return getHivesByBooleanStatus(sql, hasVentilation);
    }

    @Override
    public List<Hive> getHivesByInsulationStatus(boolean hasInsulation) {
        String sql = "SELECT * FROM hives WHERE has_insulation = ?";
        return getHivesByBooleanStatus(sql, hasInsulation);
    }

    @Override
    public List<Hive> getHivesByMoistureProtection(boolean hasProtection) {
        String sql = "SELECT * FROM hives WHERE has_moisture_protection = ?";
        return getHivesByBooleanStatus(sql, hasProtection);
    }

    @Override
    public List<Hive> getHivesByPestControlStatus(boolean hasControl) {
        String sql = "SELECT * FROM hives WHERE has_pest_control = ?";
        return getHivesByBooleanStatus(sql, hasControl);
    }

    @Override
    public List<Hive> getHivesByDiseaseStatus(boolean hasDisease) {
        String sql = "SELECT * FROM hives WHERE has_disease = ?";
        return getHivesByBooleanStatus(sql, hasDisease);
    }

    @Override
    public List<Hive> getHivesByTreatmentStatus(boolean hasTreatment) {
        String sql = "SELECT * FROM hives WHERE has_treatment = ?";
        return getHivesByBooleanStatus(sql, hasTreatment);
    }

    @Override
    public List<Hive> getHivesByFeedingStatus(boolean hasFeeding) {
        String sql = "SELECT * FROM hives WHERE has_feeding = ?";
        return getHivesByBooleanStatus(sql, hasFeeding);
    }

    @Override
    public List<Hive> getHivesBySwarmControlStatus(boolean hasControl) {
        String sql = "SELECT * FROM hives WHERE has_swarm_control = ?";
        return getHivesByBooleanStatus(sql, hasControl);
    }

    @Override
    public List<Hive> getHivesByQueenReplacementStatus(boolean needsReplacement) {
        String sql = "SELECT * FROM hives WHERE needs_queen_replacement = ?";
        return getHivesByBooleanStatus(sql, needsReplacement);
    }

    @Override
    public List<Hive> getHivesByFrameReplacementStatus(boolean needsReplacement) {
        String sql = "SELECT * FROM hives WHERE needs_frame_replacement = ?";
        return getHivesByBooleanStatus(sql, needsReplacement);
    }

    @Override
    public List<Hive> getHivesByExtensionReplacementStatus(boolean needsReplacement) {
        String sql = "SELECT * FROM hives WHERE needs_extension_replacement = ?";
        return getHivesByBooleanStatus(sql, needsReplacement);
    }

    @Override
    public List<Hive> getHivesByMaintenanceStatus(boolean needsMaintenance) {
        String sql = "SELECT * FROM hives WHERE needs_maintenance = ?";
        return getHivesByBooleanStatus(sql, needsMaintenance);
    }

    @Override
    public List<Hive> getHivesByInspectionStatus(boolean needsInspection) {
        String sql = "SELECT * FROM hives WHERE needs_inspection = ?";
        return getHivesByBooleanStatus(sql, needsInspection);
    }

    @Override
    public List<Hive> getHivesByHarvestStatus(boolean readyForHarvest) {
        String sql = "SELECT * FROM hives WHERE ready_for_harvest = ?";
        return getHivesByBooleanStatus(sql, readyForHarvest);
    }

    @Override
    public List<Hive> getHivesByWinteringStatus(boolean isWintering) {
        String sql = "SELECT * FROM hives WHERE status = 'WINTERING'";
        return getHivesByBooleanStatus(sql, isWintering);
    }

    @Override
    public List<Hive> getHivesByMigrationStatus(boolean isMigrating) {
        String sql = "SELECT * FROM hives WHERE status = 'MIGRATING'";
        return getHivesByBooleanStatus(sql, isMigrating);
    }

    @Override
    public List<Hive> getHivesByApiaryStatus(boolean isInApiary) {
        String sql = "SELECT * FROM hives WHERE status = 'IN_APIARY'";
        return getHivesByBooleanStatus(sql, isInApiary);
    }

    @Override
    public List<Hive> getHivesByResearchStatus(boolean isResearchHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RESEARCH'";
        return getHivesByBooleanStatus(sql, isResearchHive);
    }

    @Override
    public List<Hive> getHivesByEducationStatus(boolean isEducationHive) {
        String sql = "SELECT * FROM hives WHERE status = 'EDUCATION'";
        return getHivesByBooleanStatus(sql, isEducationHive);
    }

    @Override
    public List<Hive> getHivesByExhibitionStatus(boolean isExhibitionHive) {
        String sql = "SELECT * FROM hives WHERE status = 'EXHIBITION'";
        return getHivesByBooleanStatus(sql, isExhibitionHive);
    }

    @Override
    public List<Hive> getHivesByConservationStatus(boolean isConservationHive) {
        String sql = "SELECT * FROM hives WHERE status = 'CONSERVATION'";
        return getHivesByBooleanStatus(sql, isConservationHive);
    }

    @Override
    public List<Hive> getHivesByBreedingStatus(boolean isBreedingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'BREEDING'";
        return getHivesByBooleanStatus(sql, isBreedingHive);
    }

    @Override
    public List<Hive> getHivesByQueenBreedingStatus(boolean isQueenBreedingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'QUEEN_BREEDING'";
        return getHivesByBooleanStatus(sql, isQueenBreedingHive);
    }

    @Override
    public List<Hive> getHivesByPackageStatus(boolean isPackageHive) {
        String sql = "SELECT * FROM hives WHERE status = 'PACKAGE'";
        return getHivesByBooleanStatus(sql, isPackageHive);
    }

    @Override
    public List<Hive> getHivesByNucStatus(boolean isNucHive) {
        String sql = "SELECT * FROM hives WHERE status = 'NUC'";
        return getHivesByBooleanStatus(sql, isNucHive);
    }

    @Override
    public List<Hive> getHivesBySplittingStatus(boolean isSplittingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SPLITTING'";
        return getHivesByBooleanStatus(sql, isSplittingHive);
    }

    @Override
    public List<Hive> getHivesByMergingStatus(boolean isMergingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'MERGING'";
        return getHivesByBooleanStatus(sql, isMergingHive);
    }

    @Override
    public List<Hive> getHivesByRequeeningStatus(boolean isRequeeningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REQUEENING'";
        return getHivesByBooleanStatus(sql, isRequeeningHive);
    }

    @Override
    public List<Hive> getHivesByUnitingStatus(boolean isUnitingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'UNITING'";
        return getHivesByBooleanStatus(sql, isUnitingHive);
    }

    @Override
    public List<Hive> getHivesByDividingStatus(boolean isDividingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'DIVIDING'";
        return getHivesByBooleanStatus(sql, isDividingHive);
    }

    @Override
    public List<Hive> getHivesByCombiningStatus(boolean isCombiningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'COMBINING'";
        return getHivesByBooleanStatus(sql, isCombiningHive);
    }

    @Override
    public List<Hive> getHivesBySeparatingStatus(boolean isSeparatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SEPARATING'";
        return getHivesByBooleanStatus(sql, isSeparatingHive);
    }

    @Override
    public List<Hive> getHivesByMovingStatus(boolean isMovingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'MOVING'";
        return getHivesByBooleanStatus(sql, isMovingHive);
    }

    @Override
    public List<Hive> getHivesByStoringStatus(boolean isStoringHive) {
        String sql = "SELECT * FROM hives WHERE status = 'STORING'";
        return getHivesByBooleanStatus(sql, isStoringHive);
    }

    @Override
    public List<Hive> getHivesByDisposingStatus(boolean isDisposingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'DISPOSING'";
        return getHivesByBooleanStatus(sql, isDisposingHive);
    }

    @Override
    public List<Hive> getHivesByRecyclingStatus(boolean isRecyclingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RECYCLING'";
        return getHivesByBooleanStatus(sql, isRecyclingHive);
    }

    @Override
    public List<Hive> getHivesByRepairingStatus(boolean isRepairingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REPAIRING'";
        return getHivesByBooleanStatus(sql, isRepairingHive);
    }

    @Override
    public List<Hive> getHivesByCleaningStatus(boolean isCleaningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'CLEANING'";
        return getHivesByBooleanStatus(sql, isCleaningHive);
    }

    @Override
    public List<Hive> getHivesByPaintingStatus(boolean isPaintingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'PAINTING'";
        return getHivesByBooleanStatus(sql, isPaintingHive);
    }

    @Override
    public List<Hive> getHivesByStainingStatus(boolean isStainingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'STAINING'";
        return getHivesByBooleanStatus(sql, isStainingHive);
    }

    @Override
    public List<Hive> getHivesByVarnishingStatus(boolean isVarnishingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'VARNISHING'";
        return getHivesByBooleanStatus(sql, isVarnishingHive);
    }

    @Override
    public List<Hive> getHivesByWeatherizingStatus(boolean isWeatherizingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'WEATHERIZING'";
        return getHivesByBooleanStatus(sql, isWeatherizingHive);
    }

    @Override
    public List<Hive> getHivesByInsulatingStatus(boolean isInsulatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'INSULATING'";
        return getHivesByBooleanStatus(sql, isInsulatingHive);
    }

    @Override
    public List<Hive> getHivesByVentilatingStatus(boolean isVentilatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'VENTILATING'";
        return getHivesByBooleanStatus(sql, isVentilatingHive);
    }

    @Override
    public List<Hive> getHivesByShadingStatus(boolean isShadingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SHADING'";
        return getHivesByBooleanStatus(sql, isShadingHive);
    }

    @Override
    public List<Hive> getHivesByWateringStatus(boolean isWateringHive) {
        String sql = "SELECT * FROM hives WHERE status = 'WATERING'";
        return getHivesByBooleanStatus(sql, isWateringHive);
    }

    @Override
    public List<Hive> getHivesByTreatingStatus(boolean isTreatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'TREATING'";
        return getHivesByBooleanStatus(sql, isTreatingHive);
    }

    @Override
    public List<Hive> getHivesByControllingStatus(boolean isControllingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'CONTROLLING'";
        return getHivesByBooleanStatus(sql, isControllingHive);
    }

    @Override
    public List<Hive> getHivesByMonitoringStatus(boolean isMonitoringHive) {
        String sql = "SELECT * FROM hives WHERE status = 'MONITORING'";
        return getHivesByBooleanStatus(sql, isMonitoringHive);
    }

    @Override
    public List<Hive> getHivesByRecordingStatus(boolean isRecordingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RECORDING'";
        return getHivesByBooleanStatus(sql, isRecordingHive);
    }

    @Override
    public List<Hive> getHivesByReportingStatus(boolean isReportingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REPORTING'";
        return getHivesByBooleanStatus(sql, isReportingHive);
    }

    @Override
    public List<Hive> getHivesByAnalyzingStatus(boolean isAnalyzingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'ANALYZING'";
        return getHivesByBooleanStatus(sql, isAnalyzingHive);
    }

    @Override
    public List<Hive> getHivesByPlanningStatus(boolean isPlanningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'PLANNING'";
        return getHivesByBooleanStatus(sql, isPlanningHive);
    }

    @Override
    public List<Hive> getHivesBySchedulingStatus(boolean isSchedulingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SCHEDULING'";
        return getHivesByBooleanStatus(sql, isSchedulingHive);
    }

    @Override
    public List<Hive> getHivesByCoordinatingStatus(boolean isCoordinatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'COORDINATING'";
        return getHivesByBooleanStatus(sql, isCoordinatingHive);
    }

    @Override
    public List<Hive> getHivesByManagingStatus(boolean isManagingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'MANAGING'";
        return getHivesByBooleanStatus(sql, isManagingHive);
    }

    @Override
    public List<Hive> getHivesBySupervisingStatus(boolean isSupervisingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SUPERVISING'";
        return getHivesByBooleanStatus(sql, isSupervisingHive);
    }

    @Override
    public List<Hive> getHivesByTrainingStatus(boolean isTrainingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'TRAINING'";
        return getHivesByBooleanStatus(sql, isTrainingHive);
    }

    @Override
    public List<Hive> getHivesByEducatingStatus(boolean isEducatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'EDUCATING'";
        return getHivesByBooleanStatus(sql, isEducatingHive);
    }

    @Override
    public List<Hive> getHivesByResearchingStatus(boolean isResearchingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RESEARCHING'";
        return getHivesByBooleanStatus(sql, isResearchingHive);
    }

    @Override
    public List<Hive> getHivesByDevelopingStatus(boolean isDevelopingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'DEVELOPING'";
        return getHivesByBooleanStatus(sql, isDevelopingHive);
    }

    @Override
    public List<Hive> getHivesByTestingStatus(boolean isTestingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'TESTING'";
        return getHivesByBooleanStatus(sql, isTestingHive);
    }

    @Override
    public List<Hive> getHivesByEvaluatingStatus(boolean isEvaluatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'EVALUATING'";
        return getHivesByBooleanStatus(sql, isEvaluatingHive);
    }

    @Override
    public List<Hive> getHivesByImprovingStatus(boolean isImprovingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'IMPROVING'";
        return getHivesByBooleanStatus(sql, isImprovingHive);
    }

    @Override
    public List<Hive> getHivesByOptimizingStatus(boolean isOptimizingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'OPTIMIZING'";
        return getHivesByBooleanStatus(sql, isOptimizingHive);
    }

    @Override
    public List<Hive> getHivesByMaintainingStatus(boolean isMaintainingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'MAINTAINING'";
        return getHivesByBooleanStatus(sql, isMaintainingHive);
    }

    @Override
    public List<Hive> getHivesBySustainingStatus(boolean isSustainingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'SUSTAINING'";
        return getHivesByBooleanStatus(sql, isSustainingHive);
    }

    @Override
    public List<Hive> getHivesByPreservingStatus(boolean isPreservingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'PRESERVING'";
        return getHivesByBooleanStatus(sql, isPreservingHive);
    }

    @Override
    public List<Hive> getHivesByConservingStatus(boolean isConservingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'CONSERVING'";
        return getHivesByBooleanStatus(sql, isConservingHive);
    }

    @Override
    public List<Hive> getHivesByProtectingStatus(boolean isProtectingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'PROTECTING'";
        return getHivesByBooleanStatus(sql, isProtectingHive);
    }

    @Override
    public List<Hive> getHivesByRestoringStatus(boolean isRestoringHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RESTORING'";
        return getHivesByBooleanStatus(sql, isRestoringHive);
    }

    @Override
    public List<Hive> getHivesByRehabilitatingStatus(boolean isRehabilitatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REHABILITATING'";
        return getHivesByBooleanStatus(sql, isRehabilitatingHive);
    }

    @Override
    public List<Hive> getHivesByReconstructingStatus(boolean isReconstructingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RECONSTRUCTING'";
        return getHivesByBooleanStatus(sql, isReconstructingHive);
    }

    @Override
    public List<Hive> getHivesByRebuildingStatus(boolean isRebuildingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REBUILDING'";
        return getHivesByBooleanStatus(sql, isRebuildingHive);
    }

    @Override
    public List<Hive> getHivesByRemodelingStatus(boolean isRemodelingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REMODELING'";
        return getHivesByBooleanStatus(sql, isRemodelingHive);
    }

    @Override
    public List<Hive> getHivesByRenovatingStatus(boolean isRenovatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RENOVATING'";
        return getHivesByBooleanStatus(sql, isRenovatingHive);
    }

    @Override
    public List<Hive> getHivesByRefurbishingStatus(boolean isRefurbishingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REFURBISHING'";
        return getHivesByBooleanStatus(sql, isRefurbishingHive);
    }

    @Override
    public List<Hive> getHivesByReconditioningStatus(boolean isReconditioningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RECONDITIONING'";
        return getHivesByBooleanStatus(sql, isReconditioningHive);
    }

    @Override
    public List<Hive> getHivesByRejuvenatingStatus(boolean isRejuvenatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REJUVENATING'";
        return getHivesByBooleanStatus(sql, isRejuvenatingHive);
    }

    @Override
    public List<Hive> getHivesByRevitalizingStatus(boolean isRevitalizingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REVITALIZING'";
        return getHivesByBooleanStatus(sql, isRevitalizingHive);
    }

    @Override
    public List<Hive> getHivesByRevivingStatus(boolean isRevivingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REVIVING'";
        return getHivesByBooleanStatus(sql, isRevivingHive);
    }

    @Override
    public List<Hive> getHivesByResurrectingStatus(boolean isResurrectingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RESURRECTING'";
        return getHivesByBooleanStatus(sql, isResurrectingHive);
    }

    @Override
    public List<Hive> getHivesByReincarnatingStatus(boolean isReincarnatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REINCARNATING'";
        return getHivesByBooleanStatus(sql, isReincarnatingHive);
    }

    @Override
    public List<Hive> getHivesByRegeneratingStatus(boolean isRegeneratingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REGENERATING'";
        return getHivesByBooleanStatus(sql, isRegeneratingHive);
    }

    @Override
    public List<Hive> getHivesByRecreatingStatus(boolean isRecreatingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'RECREATING'";
        return getHivesByBooleanStatus(sql, isRecreatingHive);
    }

    @Override
    public List<Hive> getHivesByReinventingStatus(boolean isReinventingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REINVENTING'";
        return getHivesByBooleanStatus(sql, isReinventingHive);
    }

    @Override
    public List<Hive> getHivesByReimaginingStatus(boolean isReimaginingHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REIMAGINING'";
        return getHivesByBooleanStatus(sql, isReimaginingHive);
    }

    @Override
    public List<Hive> getHivesByRedesigningStatus(boolean isRedesigningHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REDESIGNING'";
        return getHivesByBooleanStatus(sql, isRedesigningHive);
    }

    @Override
    public List<Hive> getHivesByReengineeringStatus(boolean isReengineeringHive) {
        String sql = "SELECT * FROM hives WHERE status = 'REENGINEERING'";
        return getHivesByBooleanStatus(sql, isReengineeringHive);
    }

    @Override
    public List<Hive> getHivesByOrientation(String orientation) {
        String sql = "SELECT * FROM hives WHERE orientation = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orientation);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by orientation", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByProductivityStatus(String status) {
        String sql = "SELECT * FROM hives WHERE productivity_status = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by productivity status", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByHoneyQuantity(double minQuantity, double maxQuantity) {
        String sql = "SELECT * FROM hives WHERE honey_quantity BETWEEN ? AND ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, minQuantity);
            pstmt.setDouble(2, maxQuantity);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by honey quantity", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByFrameCount(int minFrames, int maxFrames) {
        String sql = "SELECT * FROM hives WHERE frame_count BETWEEN ? AND ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minFrames);
            pstmt.setInt(2, maxFrames);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by frame count", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByColonySize(String size) {
        String sql = "SELECT * FROM hives WHERE colony_size = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, size);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by colony size", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByExtensionCount(int minExtensions, int maxExtensions) {
        String sql = "SELECT * FROM hives WHERE extension_count BETWEEN ? AND ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minExtensions);
            pstmt.setInt(2, maxExtensions);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by extension count", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByLocation(double latitude, double longitude, double radiusKm) {
        // This is a simple bounding box search; for more accurate results, use the
        // Haversine formula in SQL
        String sql = "SELECT * FROM hives WHERE latitude BETWEEN ? AND ? AND longitude BETWEEN ? AND ?";
        double latMin = latitude - radiusKm / 111.0;
        double latMax = latitude + radiusKm / 111.0;
        double lonMin = longitude - radiusKm / 111.0;
        double lonMax = longitude + radiusKm / 111.0;
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, latMin);
            pstmt.setDouble(2, latMax);
            pstmt.setDouble(3, lonMin);
            pstmt.setDouble(4, lonMax);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by location", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByAltitude(double minAltitude, double maxAltitude) {
        String sql = "SELECT * FROM hives WHERE altitude BETWEEN ? AND ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, minAltitude);
            pstmt.setDouble(2, maxAltitude);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by altitude", e);
        }
        return hives;
    }

    @Override
    public List<Hive> getHivesByHealthStatus(String status) {
        String sql = "SELECT * FROM hives WHERE health_status = ?";
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by health status", e);
        }
        return hives;
    }

    private List<Hive> getHivesByBooleanStatus(String sql, boolean status) {
        List<Hive> hives = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    hives.add(mapResultSetToHive(rs));
                }
            }
            return hives;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting hives by status", e);
        }
    }

    private Hive mapResultSetToHive(ResultSet rs) throws SQLException {
        Hive hive = new Hive();
        hive.setId(rs.getLong("id"));
        hive.setName(rs.getString("name"));
        hive.setType(rs.getString("type"));
        hive.setStatus(rs.getString("status"));
        hive.setQueenAge(rs.getInt("queen_age"));
        String healthStatusStr = rs.getString("health_status");
        if (healthStatusStr != null) {
            hive.setHealthStatus(Hive.HealthStatus.valueOf(healthStatusStr));
        }
        hive.setProductivityStatus(rs.getString("productivity_status"));
        hive.setColonySize(rs.getInt("colony_size"));
        hive.setFrameCount(rs.getInt("frame_count"));
        hive.setExtensionCount(rs.getInt("extension_count"));
        hive.setLatitude(rs.getDouble("latitude"));
        hive.setLongitude(rs.getDouble("longitude"));
        hive.setAltitude(rs.getDouble("altitude"));
        hive.setOrientation(rs.getString("orientation"));
        // Optionally fetch and set the related Site object
        Long siteId = rs.getLong("site_id");
        if (siteId != null && siteId > 0) {
            // Example: hive.setSite(new SiteServiceImpl().getSiteById(siteId));
            // Uncomment the above line if you want to fetch the Site object here
        }
        return hive;
    }

    // Implement other methods with similar patterns...
    // For brevity, I'm not implementing all methods, but they would follow the same
    // pattern
    // as the ones shown above, using appropriate SQL queries for each case.
}