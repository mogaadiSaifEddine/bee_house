package com.beehopuse.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hive_id", nullable = false)
    private Hive hive;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private User agent;

    @Column(name = "planned_date", nullable = false)
    private LocalDateTime plannedDate;

    @Column(name = "actual_date")
    private LocalDateTime actualDate;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(nullable = false)
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Column(name = "planned_actions", columnDefinition = "TEXT")
    private String plannedActions;

    @Column(name = "completed_actions", columnDefinition = "TEXT")
    private String completedActions;

    @Column(columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "colony_size_rating")
    private Integer colonySizeRating;

    @Column(name = "health_rating")
    private Integer healthRating;

    @Column(name = "productivity_rating")
    private Integer productivityRating;

    @Column(name = "visit_status")
    @Enumerated(EnumType.STRING)
    private VisitStatus status;

    public enum VisitStatus {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hive getHive() {
        return hive;
    }

    public void setHive(Hive hive) {
        this.hive = hive;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public LocalDateTime getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(LocalDateTime plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDateTime getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDateTime actualDate) {
        this.actualDate = actualDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPlannedActions() {
        return plannedActions;
    }

    public void setPlannedActions(String plannedActions) {
        this.plannedActions = plannedActions;
    }

    public String getCompletedActions() {
        return completedActions;
    }

    public void setCompletedActions(String completedActions) {
        this.completedActions = completedActions;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public Integer getColonySizeRating() {
        return colonySizeRating;
    }

    public void setColonySizeRating(Integer colonySizeRating) {
        this.colonySizeRating = colonySizeRating;
    }

    public Integer getHealthRating() {
        return healthRating;
    }

    public void setHealthRating(Integer healthRating) {
        this.healthRating = healthRating;
    }

    public Integer getProductivityRating() {
        return productivityRating;
    }

    public void setProductivityRating(Integer productivityRating) {
        this.productivityRating = productivityRating;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }
} 