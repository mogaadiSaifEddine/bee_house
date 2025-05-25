package com.beehopuse.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Farm> ownedFarms = new HashSet<>();

    @OneToMany(mappedBy = "responsibleAgent")
    private Set<Hive> responsibleHives = new HashSet<>();

    @OneToMany(mappedBy = "agent")
    private Set<Visit> visits = new HashSet<>();

    public enum UserRole {
        ADMIN,
        FARMER,
        AGENT,
        SUPERVISOR
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Set<Farm> getOwnedFarms() {
        return ownedFarms;
    }

    public void setOwnedFarms(Set<Farm> ownedFarms) {
        this.ownedFarms = ownedFarms;
    }

    public Set<Hive> getResponsibleHives() {
        return responsibleHives;
    }

    public void setResponsibleHives(Set<Hive> responsibleHives) {
        this.responsibleHives = responsibleHives;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
} 