package net.es.oscars.dto.auth;


import lombok.Data;

@Data
public class Permissions {
    private long maxMbpsPerReservation = 0;

    private long maxMinutesPerReservation = 0;

    private boolean callSoapAllowed = false;

    private boolean pathSpecAllowed = false;

    private boolean adminAllowed = false;
    public Permissions() {

    }
}