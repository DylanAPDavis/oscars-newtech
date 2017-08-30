package net.es.oscars.authnz.prop;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "authnz")
@NoArgsConstructor
public class AuthnzProperties {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
