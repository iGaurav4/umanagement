package com.lageraho.springsecurity.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lageraho.springsecurity.util.UMConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.stream.Collectors;


@Data
//@Entity
@Document
public class User implements UserDetails {


    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;


    @NotNull(message = "Username can't be empty")
    @NotBlank(message = "Username can't be empty")
    @Size(min = 6, max = 32, message = "Username should be minimum of 6 characters")
    @Pattern(regexp = UMConstants.ALPHANUMERIC_PATTERN, message = "Username should be alphanumeric")
//    @Column(unique = true)
    private String username;

    @NotNull(message = "Forgot to enter password")
    @NotBlank(message = "Forgot to enter password")
    private String password;

    @Pattern(regexp = UMConstants.EMAIL_PATTERN, message = "Please recheck email, seems to be invalid")
    @NotNull(message = "Register your email")
    @NotBlank(message = "Register your email")
    private String email;

    @NotNull(message = "Tell us, Who you are")
    @NotBlank(message = "Tell us, Who you are")
    private String name;

    private boolean isLocked;

    //    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getPermissions().stream().map(Permission::getGrantedAuthority).collect(Collectors.toList());
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return !isLocked;
    }
}
