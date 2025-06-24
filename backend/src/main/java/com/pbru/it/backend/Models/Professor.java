package com.pbru.it.backend.Models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "professors")
public class Professor implements UserDetails {

    public Professor(){}

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "professor_id")
    private UUID id;

    @Email(message = "Invalid email")
    @Column(name = "professor_email", length = 100, nullable = false)
    private String email;

    @Column(name = "professor_password", columnDefinition = "TEXT", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "professor_profile_id", referencedColumnName = "professor_profile_id")
    @JsonIgnoreProperties("professor")
    private ProfessorProfile professorProfile;

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
                        .map(position -> (GrantedAuthority) () -> "ROLE_" + position.getNameEn())
                        .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
