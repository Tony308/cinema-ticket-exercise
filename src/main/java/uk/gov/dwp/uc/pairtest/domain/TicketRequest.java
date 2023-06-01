package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidTicketRequestException;

/**
 * Should be an Immutable Object
 */

public class TicketRequest {
    private final int noOfTickets;
    private final TicketTypeEnum type;

    public TicketRequest(TicketTypeEnum type, int noOfTickets) {
        dataValidation(noOfTickets);
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    private void dataValidation(int noOfTickets) {
        if (noOfTickets < 1) {
            throw new InvalidTicketRequestException("Number of tickets must at least be 1");
        }
        if (noOfTickets > 20) {
            throw new InvalidTicketRequestException("Max number of tickets is 20");
        }
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public TicketTypeEnum getTicketType() {
        return type;
    }

}
