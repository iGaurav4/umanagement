package com.lageraho.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
@Document
public class Permission {

    public enum PermissionType {
        Read,
        ReadWrite,
        ReadExecute
    }

    @Id
//    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private String id;
    private String description;

//    @Enumerated(EnumType.STRING)
    private PermissionType type;

    @JsonIgnore
    private GrantedAuthority grantedAuthority;

}
