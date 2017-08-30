package net.es.oscars.dto.auth;


import lombok.Data;
import lombok.NonNull;

@Data
public class User {

    @NonNull
    private String username;

    @NonNull
    private String password;

    private String certIssuer;

    private String certSubject;

    private String fullName;

    private String email;

    private Permissions permissions;

    private String institution;

    public User() {

    }

}
