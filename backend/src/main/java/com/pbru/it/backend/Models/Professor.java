package com.pbru.it.backend.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "professors")
public class Professor implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "professor_id")
    private UUID id;

    @Column(name = "professor_email", length = 100, unique = true , nullable = false)
    private String email;

    @Column(name = "professor_password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @OneToOne(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfessorProfile profile;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfessorEducationalQualification> qualifications = new ArrayList<>();

    public Professor(UUID id, String email, String password, ProfessorProfile profile,
            List<ProfessorEducationalQualification> qualifications, Set<Position> positions) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.qualifications = qualifications;
        this.positions = positions;
    }

    public Professor() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfessorProfile getProfile() {
        return profile;
    }

    public void setProfile(ProfessorProfile profile) {
        this.profile = profile;
    }

    public List<ProfessorEducationalQualification> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<ProfessorEducationalQualification> qualifications) {
        this.qualifications = qualifications;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "position_professor",
        joinColumns = @JoinColumn(name = "professor_id"),
        inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private Set<Position> positions = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return positions.stream()
                        .map(position -> new SimpleGrantedAuthority(position.getName()))
                        .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

}
