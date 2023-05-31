package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;

/**
 * Should be an Immutable Object
 */
public class TicketPurchaseRequest {

    private final long accountId;
    private final TicketRequest[] ticketRequests;

    public TicketPurchaseRequest(long accountId, TicketRequest[] ticketRequests) {

        if (accountId < 1) {
            throw new InvalidPurchaseException("Invalid accountId");
        }
        if (ticketRequests.length < 1) {
            throw new InvalidPurchaseException("Invalid number of TicketRequests");
        }
        boolean notValidTickets = Arrays.stream(ticketRequests).noneMatch(item -> item.getTicketType().equals(TicketTypeEnum.ADULT));
        if (notValidTickets) {
            throw new InvalidPurchaseException("InvalidTicketPurchaseRequest, must have at least 1 TicketRequest of type: " + TicketTypeEnum.ADULT);
        }
        this.accountId = accountId;
        this.ticketRequests = ticketRequests;
    }

    public long getAccountId() {
        return accountId;
    }

    public TicketRequest[] getTicketTypeRequests() {
        return ticketRequests;
    }
}
