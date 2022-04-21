package com.lageraho.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Builder
//@Entity
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Role {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

//    @Column(unique = true)
    @NotNull(message = "Mandatory Field")
    @NotEmpty(message = "Mandatory Field")
    private String name;

    @NotNull(message = "Mandatory Field")
    @NotEmpty(message = "Mandatory Field")
    private String description;

    @Transient
    private Integer no_of_users;

    @JsonIgnore
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private List<User> users;

    @JsonIgnore
//    @NotNull(message = "Mandatory Field")
//    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;



}
