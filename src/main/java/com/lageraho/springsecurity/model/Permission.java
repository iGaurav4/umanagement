package com.lageraho.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission {

    public enum PermissionType {
        Read,
        ReadWrite,
        ReadExecute
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private PermissionType type;
    @JsonIgnore
    private GrantedAuthority grantedAuthority;

}
