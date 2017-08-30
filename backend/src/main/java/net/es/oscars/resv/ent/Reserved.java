package net.es.oscars.resv.ent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor(suppressConstructorProperties=true)
@NoArgsConstructor
public class Reserved {
    @JsonCreator
    public Reserved(@JsonProperty("connectionId") @NonNull String connectionId,
                    @JsonProperty("cmp") @NonNull Components cmp,
                    @JsonProperty("schedule") @NonNull Schedule schedule) {
        this.connectionId = connectionId;
        this.cmp = cmp;
        this.schedule = schedule;
    }

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NonNull
    private String connectionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    private Components cmp;

    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    private Schedule schedule;


}
