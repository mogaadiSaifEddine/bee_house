package com.beehopuse.model;

import javax.persistence.*;

@Entity
@Table(name = "frames")
public class Frame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "extension_id", nullable = false)
    private HiveExtension extension;

    @Column(name = "slot_number", nullable = false)
    private Integer slotNumber;

    @Column(name = "current_weight")
    private Double currentWeight;

    @Column(name = "frame_type")
    @Enumerated(EnumType.STRING)
    private FrameType frameType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FrameStatus status;

    public enum FrameType {
        HONEY,
        BROOD,
        POLLEN,
        EMPTY
    }

    public enum FrameStatus {
        NEW,
        IN_USE,
        FULL,
        HARVESTED,
        DAMAGED
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HiveExtension getExtension() {
        return extension;
    }

    public void setExtension(HiveExtension extension) {
        this.extension = extension;
    }

    public Integer getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(Integer slotNumber) {
        this.slotNumber = slotNumber;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public FrameType getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameType frameType) {
        this.frameType = frameType;
    }

    public FrameStatus getStatus() {
        return status;
    }

    public void setStatus(FrameStatus status) {
        this.status = status;
    }
} 