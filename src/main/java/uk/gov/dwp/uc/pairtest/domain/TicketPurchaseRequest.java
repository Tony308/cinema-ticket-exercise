package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

/**
 * Should be an Immutable Object
 */
public class TicketPurchaseRequest {

    private final long accountId;
    private final TicketRequest[] ticketRequests;

    public TicketPurchaseRequest(long accountId, TicketRequest[] ticketRequests) {
        if (accountId < 1) {
            throw new InvalidPurchaseException();
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
