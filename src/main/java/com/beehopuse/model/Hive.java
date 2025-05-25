package com.beehopuse.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hives")
public class Hive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HiveExtension> extensions = new ArrayList<>();

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "responsible_agent_id")
    private User responsibleAgent;

    @Column(name = "base_weight")
    private Double baseWeight;

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "colony_size")
    private Integer colonySize;

    @Column(name = "health_status")
    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

    @Column(name = "productivity_level")
    private Integer productivityLevel;

    // New fields
    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "queen_age")
    private Integer queenAge;

    @Column(name = "frame_count")
    private Integer frameCount;

    @Column(name = "extension_count")
    private Integer extensionCount;

    @Column(name = "honey_quantity")
    private Double honeyQuantity;

    @Column(name = "productivity_status")
    private String productivityStatus;

    @Column(name = "has_shade")
    private Boolean hasShade;

    @Column(name = "has_water_source")
    private Boolean hasWaterSource;

    @Column(name = "has_flower_source")
    private Boolean hasFlowerSource;

    @Column(name = "has_predator_protection")
    private Boolean hasPredatorProtection;

    @Column(name = "has_ventilation")
    private Boolean hasVentilation;

    @Column(name = "has_insulation")
    private Boolean hasInsulation;

    @Column(name = "has_moisture_protection")
    private Boolean hasMoistureProtection;

    @Column(name = "has_pest_control")
    private Boolean hasPestControl;

    @Column(name = "has_disease")
    private Boolean hasDisease;

    @Column(name = "has_treatment")
    private Boolean hasTreatment;

    @Column(name = "has_feeding")
    private Boolean hasFeeding;

    @Column(name = "has_swarm_control")
    private Boolean hasSwarmControl;

    @Column(name = "needs_queen_replacement")
    private Boolean needsQueenReplacement;

    @Column(name = "needs_frame_replacement")
    private Boolean needsFrameReplacement;

    @Column(name = "needs_extension_replacement")
    private Boolean needsExtensionReplacement;

    @Column(name = "needs_maintenance")
    private Boolean needsMaintenance;

    @Column(name = "needs_inspection")
    private Boolean needsInspection;

    @Column(name = "ready_for_harvest")
    private Boolean readyForHarvest;

    public enum HealthStatus {
        EXCELLENT,
        GOOD,
        FAIR,
        POOR,
        CRITICAL
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<HiveExtension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<HiveExtension> extensions) {
        this.extensions = extensions;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public User getResponsibleAgent() {
        return responsibleAgent;
    }

    public void setResponsibleAgent(User responsibleAgent) {
        this.responsibleAgent = responsibleAgent;
    }

    public Double getBaseWeight() {
        return baseWeight;
    }

    public void setBaseWeight(Double baseWeight) {
        this.baseWeight = baseWeight;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Integer getColonySize() {
        return colonySize;
    }

    public void setColonySize(Integer colonySize) {
        this.colonySize = colonySize;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Integer getProductivityLevel() {
        return productivityLevel;
    }

    public void setProductivityLevel(Integer productivityLevel) {
        this.productivityLevel = productivityLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Integer getQueenAge() {
        return queenAge;
    }

    public void setQueenAge(Integer queenAge) {
        this.queenAge = queenAge;
    }

    public Integer getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(Integer frameCount) {
        this.frameCount = frameCount;
    }

    public Integer getExtensionCount() {
        return extensionCount;
    }

    public void setExtensionCount(Integer extensionCount) {
        this.extensionCount = extensionCount;
    }

    public Double getHoneyQuantity() {
        return honeyQuantity;
    }

    public void setHoneyQuantity(Double honeyQuantity) {
        this.honeyQuantity = honeyQuantity;
    }

    public String getProductivityStatus() {
        return productivityStatus;
    }

    public void setProductivityStatus(String productivityStatus) {
        this.productivityStatus = productivityStatus;
    }

    public Boolean getHasShade() {
        return hasShade;
    }

    public void setHasShade(Boolean hasShade) {
        this.hasShade = hasShade;
    }

    public Boolean getHasWaterSource() {
        return hasWaterSource;
    }

    public void setHasWaterSource(Boolean hasWaterSource) {
        this.hasWaterSource = hasWaterSource;
    }

    public Boolean getHasFlowerSource() {
        return hasFlowerSource;
    }

    public void setHasFlowerSource(Boolean hasFlowerSource) {
        this.hasFlowerSource = hasFlowerSource;
    }

    public Boolean getHasPredatorProtection() {
        return hasPredatorProtection;
    }

    public void setHasPredatorProtection(Boolean hasPredatorProtection) {
        this.hasPredatorProtection = hasPredatorProtection;
    }

    public Boolean getHasVentilation() {
        return hasVentilation;
    }

    public void setHasVentilation(Boolean hasVentilation) {
        this.hasVentilation = hasVentilation;
    }

    public Boolean getHasInsulation() {
        return hasInsulation;
    }

    public void setHasInsulation(Boolean hasInsulation) {
        this.hasInsulation = hasInsulation;
    }

    public Boolean getHasMoistureProtection() {
        return hasMoistureProtection;
    }

    public void setHasMoistureProtection(Boolean hasMoistureProtection) {
        this.hasMoistureProtection = hasMoistureProtection;
    }

    public Boolean getHasPestControl() {
        return hasPestControl;
    }

    public void setHasPestControl(Boolean hasPestControl) {
        this.hasPestControl = hasPestControl;
    }

    public Boolean getHasDisease() {
        return hasDisease;
    }

    public void setHasDisease(Boolean hasDisease) {
        this.hasDisease = hasDisease;
    }

    public Boolean getHasTreatment() {
        return hasTreatment;
    }

    public void setHasTreatment(Boolean hasTreatment) {
        this.hasTreatment = hasTreatment;
    }

    public Boolean getHasFeeding() {
        return hasFeeding;
    }

    public void setHasFeeding(Boolean hasFeeding) {
        this.hasFeeding = hasFeeding;
    }

    public Boolean getHasSwarmControl() {
        return hasSwarmControl;
    }

    public void setHasSwarmControl(Boolean hasSwarmControl) {
        this.hasSwarmControl = hasSwarmControl;
    }

    public Boolean getNeedsQueenReplacement() {
        return needsQueenReplacement;
    }

    public void setNeedsQueenReplacement(Boolean needsQueenReplacement) {
        this.needsQueenReplacement = needsQueenReplacement;
    }

    public Boolean getNeedsFrameReplacement() {
        return needsFrameReplacement;
    }

    public void setNeedsFrameReplacement(Boolean needsFrameReplacement) {
        this.needsFrameReplacement = needsFrameReplacement;
    }

    public Boolean getNeedsExtensionReplacement() {
        return needsExtensionReplacement;
    }

    public void setNeedsExtensionReplacement(Boolean needsExtensionReplacement) {
        this.needsExtensionReplacement = needsExtensionReplacement;
    }

    public Boolean getNeedsMaintenance() {
        return needsMaintenance;
    }

    public void setNeedsMaintenance(Boolean needsMaintenance) {
        this.needsMaintenance = needsMaintenance;
    }

    public Boolean getNeedsInspection() {
        return needsInspection;
    }

    public void setNeedsInspection(Boolean needsInspection) {
        this.needsInspection = needsInspection;
    }

    public Boolean getReadyForHarvest() {
        return readyForHarvest;
    }

    public void setReadyForHarvest(Boolean readyForHarvest) {
        this.readyForHarvest = readyForHarvest;
    }
}