package net.es.oscars.dto.acct;


import lombok.Data;
import lombok.NonNull;

import java.util.Collection;

@Data
public class Customer {

    @NonNull
    private String name;

    private Collection<String> projects;

    public Customer() {

    }

}
