package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidTicketRequestException;

/**
 * Should be an Immutable Object
 */
public class TicketRequest {
    private final int noOfTickets;
    private final TicketTypeEnum type;

    public TicketRequest(TicketTypeEnum type, int noOfTickets) {
        if (noOfTickets < 1) {
            throw new InvalidTicketRequestException();
        }
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
