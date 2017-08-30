package net.es.oscars.dto.spec;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservedBandwidth {


    @NonNull
    private String urn;

    @NonNull
    private String containerConnectionId;       // Unique ID of the containing Connection

    //private Integer bandwidth;

    private Integer inBandwidth;

    private Integer egBandwidth;


    private Instant beginning;

    private Instant ending;


}
