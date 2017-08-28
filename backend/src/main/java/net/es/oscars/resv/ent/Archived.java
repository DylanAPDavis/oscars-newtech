package net.es.oscars.resv.ent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class Archived {
    @JsonCreator
    public Archived(@JsonProperty("connectionId") @NonNull String connectionId,
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
    @Column(unique = true)
    private String connectionId;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Components cmp;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    private Schedule schedule;


}
