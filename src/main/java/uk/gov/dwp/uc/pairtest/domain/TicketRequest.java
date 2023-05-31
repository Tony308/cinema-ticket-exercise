package uk.gov.dwp.uc.pairtest.domain;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Should be an Immutable Object
 */
public class TicketRequest {

    @Min(1)
    private final int noOfTickets;
    @NotNull
    private final TicketTypeEnum type;

    public TicketRequest(TicketTypeEnum type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public TicketTypeEnum getTicketType() {
        return type;
    }

}
