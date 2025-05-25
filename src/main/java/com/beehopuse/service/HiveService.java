package com.beehopuse.service;

import com.beehopuse.model.Hive;
import com.beehopuse.model.Site;
import java.util.List;

public interface HiveService {
    Hive createHive(Hive hive);

    Hive updateHive(Hive hive);

    void deleteHive(Long id);

    Hive getHiveById(Long id);

    List<Hive> getHivesBySite(Site site);

    List<Hive> getAllHives();

    List<Hive> getHivesByStatus(String status);

    List<Hive> getHivesByType(String type);

    List<Hive> getHivesByQueenAge(int minAge, int maxAge);

    List<Hive> getHivesByLastInspectionDate(java.time.LocalDate date);

    List<Hive> getHivesByHoneyQuantity(double minQuantity, double maxQuantity);

    List<Hive> getHivesByHealthStatus(String status);

    List<Hive> getHivesByProductivityStatus(String status);

    List<Hive> getHivesByColonySize(String size);

    List<Hive> getHivesByFrameCount(int minFrames, int maxFrames);

    List<Hive> getHivesByExtensionCount(int minExtensions, int maxExtensions);

    List<Hive> getHivesByLocation(double latitude, double longitude, double radiusKm);

    List<Hive> getHivesByAltitude(double minAltitude, double maxAltitude);

    List<Hive> getHivesByOrientation(String orientation);

    List<Hive> getHivesByShadeStatus(boolean hasShade);

    List<Hive> getHivesByWaterSource(boolean hasWaterSource);

    List<Hive> getHivesByFlowerSource(boolean hasFlowerSource);

    List<Hive> getHivesByPredatorProtection(boolean hasProtection);

    List<Hive> getHivesByVentilationStatus(boolean hasVentilation);

    List<Hive> getHivesByInsulationStatus(boolean hasInsulation);

    List<Hive> getHivesByMoistureProtection(boolean hasProtection);

    List<Hive> getHivesByPestControlStatus(boolean hasControl);

    List<Hive> getHivesByDiseaseStatus(boolean hasDisease);

    List<Hive> getHivesByTreatmentStatus(boolean hasTreatment);

    List<Hive> getHivesByFeedingStatus(boolean hasFeeding);

    List<Hive> getHivesBySwarmControlStatus(boolean hasControl);

    List<Hive> getHivesByQueenReplacementStatus(boolean needsReplacement);

    List<Hive> getHivesByFrameReplacementStatus(boolean needsReplacement);

    List<Hive> getHivesByExtensionReplacementStatus(boolean needsReplacement);

    List<Hive> getHivesByMaintenanceStatus(boolean needsMaintenance);

    List<Hive> getHivesByInspectionStatus(boolean needsInspection);

    List<Hive> getHivesByHarvestStatus(boolean readyForHarvest);

    List<Hive> getHivesByWinteringStatus(boolean isWintering);

    List<Hive> getHivesByMigrationStatus(boolean isMigrating);

    List<Hive> getHivesByApiaryStatus(boolean isInApiary);

    List<Hive> getHivesByResearchStatus(boolean isResearchHive);

    List<Hive> getHivesByEducationStatus(boolean isEducationHive);

    List<Hive> getHivesByExhibitionStatus(boolean isExhibitionHive);

    List<Hive> getHivesByConservationStatus(boolean isConservationHive);

    List<Hive> getHivesByBreedingStatus(boolean isBreedingHive);

    List<Hive> getHivesByQueenBreedingStatus(boolean isQueenBreedingHive);

    List<Hive> getHivesByPackageStatus(boolean isPackageHive);

    List<Hive> getHivesByNucStatus(boolean isNucHive);

    List<Hive> getHivesBySplittingStatus(boolean isSplittingHive);

    List<Hive> getHivesByMergingStatus(boolean isMergingHive);

    List<Hive> getHivesByRequeeningStatus(boolean isRequeeningHive);

    List<Hive> getHivesByUnitingStatus(boolean isUnitingHive);

    List<Hive> getHivesByDividingStatus(boolean isDividingHive);

    List<Hive> getHivesByCombiningStatus(boolean isCombiningHive);

    List<Hive> getHivesBySeparatingStatus(boolean isSeparatingHive);

    List<Hive> getHivesByMovingStatus(boolean isMovingHive);

    List<Hive> getHivesByStoringStatus(boolean isStoringHive);

    List<Hive> getHivesByDisposingStatus(boolean isDisposingHive);

    List<Hive> getHivesByRecyclingStatus(boolean isRecyclingHive);

    List<Hive> getHivesByRepairingStatus(boolean isRepairingHive);

    List<Hive> getHivesByCleaningStatus(boolean isCleaningHive);

    List<Hive> getHivesByPaintingStatus(boolean isPaintingHive);

    List<Hive> getHivesByStainingStatus(boolean isStainingHive);

    List<Hive> getHivesByVarnishingStatus(boolean isVarnishingHive);

    List<Hive> getHivesByWeatherizingStatus(boolean isWeatherizingHive);

    List<Hive> getHivesByInsulatingStatus(boolean isInsulatingHive);

    List<Hive> getHivesByVentilatingStatus(boolean isVentilatingHive);

    List<Hive> getHivesByShadingStatus(boolean isShadingHive);

    List<Hive> getHivesByWateringStatus(boolean isWateringHive);

    List<Hive> getHivesByTreatingStatus(boolean isTreatingHive);

    List<Hive> getHivesByControllingStatus(boolean isControllingHive);

    List<Hive> getHivesByMonitoringStatus(boolean isMonitoringHive);

    List<Hive> getHivesByRecordingStatus(boolean isRecordingHive);

    List<Hive> getHivesByReportingStatus(boolean isReportingHive);

    List<Hive> getHivesByAnalyzingStatus(boolean isAnalyzingHive);

    List<Hive> getHivesByPlanningStatus(boolean isPlanningHive);

    List<Hive> getHivesBySchedulingStatus(boolean isSchedulingHive);

    List<Hive> getHivesByCoordinatingStatus(boolean isCoordinatingHive);

    List<Hive> getHivesByManagingStatus(boolean isManagingHive);

    List<Hive> getHivesBySupervisingStatus(boolean isSupervisingHive);

    List<Hive> getHivesByTrainingStatus(boolean isTrainingHive);

    List<Hive> getHivesByEducatingStatus(boolean isEducatingHive);

    List<Hive> getHivesByResearchingStatus(boolean isResearchingHive);

    List<Hive> getHivesByDevelopingStatus(boolean isDevelopingHive);

    List<Hive> getHivesByTestingStatus(boolean isTestingHive);

    List<Hive> getHivesByEvaluatingStatus(boolean isEvaluatingHive);

    List<Hive> getHivesByImprovingStatus(boolean isImprovingHive);

    List<Hive> getHivesByOptimizingStatus(boolean isOptimizingHive);

    List<Hive> getHivesByMaintainingStatus(boolean isMaintainingHive);

    List<Hive> getHivesBySustainingStatus(boolean isSustainingHive);

    List<Hive> getHivesByPreservingStatus(boolean isPreservingHive);

    List<Hive> getHivesByConservingStatus(boolean isConservingHive);

    List<Hive> getHivesByProtectingStatus(boolean isProtectingHive);

    List<Hive> getHivesByRestoringStatus(boolean isRestoringHive);

    List<Hive> getHivesByRehabilitatingStatus(boolean isRehabilitatingHive);

    List<Hive> getHivesByReconstructingStatus(boolean isReconstructingHive);

    List<Hive> getHivesByRebuildingStatus(boolean isRebuildingHive);

    List<Hive> getHivesByRemodelingStatus(boolean isRemodelingHive);

    List<Hive> getHivesByRenovatingStatus(boolean isRenovatingHive);

    List<Hive> getHivesByRefurbishingStatus(boolean isRefurbishingHive);

    List<Hive> getHivesByReconditioningStatus(boolean isReconditioningHive);

    List<Hive> getHivesByRejuvenatingStatus(boolean isRejuvenatingHive);

    List<Hive> getHivesByRevitalizingStatus(boolean isRevitalizingHive);

    List<Hive> getHivesByRevivingStatus(boolean isRevivingHive);

    List<Hive> getHivesByResurrectingStatus(boolean isResurrectingHive);

    List<Hive> getHivesByReincarnatingStatus(boolean isReincarnatingHive);

    List<Hive> getHivesByRegeneratingStatus(boolean isRegeneratingHive);

    List<Hive> getHivesByRecreatingStatus(boolean isRecreatingHive);

    List<Hive> getHivesByReinventingStatus(boolean isReinventingHive);

    List<Hive> getHivesByReimaginingStatus(boolean isReimaginingHive);

    List<Hive> getHivesByRedesigningStatus(boolean isRedesigningHive);

    List<Hive> getHivesByReengineeringStatus(boolean isReengineeringHive);
}