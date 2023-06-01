package uk.gov.dwp.uc.pairtest.domain;

import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Should be an Immutable Object
 */
public class TicketPurchaseRequest {

    private final long accountId;
    private final TicketRequest[] ticketRequests;

    public TicketPurchaseRequest(long accountId, TicketRequest[] ticketRequests) {
        int adultTickets = Arrays.stream(ticketRequests).filter((ticketRequest -> ticketRequest.getTicketType().equals(TicketTypeEnum.ADULT))).mapToInt(TicketRequest::getNoOfTickets).sum();
        int infantTickets = Arrays.stream(ticketRequests).filter((ticketRequest -> ticketRequest.getTicketType().equals(TicketTypeEnum.INFANT))).mapToInt(TicketRequest::getNoOfTickets).sum();

        if (accountId < 1) {
            throw new InvalidPurchaseException("Invalid accountId");
        }
        if (ticketRequests.length < 1) {
            throw new InvalidPurchaseException("Invalid number of TicketRequests");
        }
        boolean notValidTickets = adultTickets < 1;
        if (notValidTickets) {
            throw new InvalidPurchaseException("Must have at least 1 TicketRequest of type: " + TicketTypeEnum.ADULT);
        }
        int noTickets = Arrays.stream(ticketRequests).mapToInt(TicketRequest::getNoOfTickets).sum();
        if (20 < noTickets) {
            throw new InvalidPurchaseException("Exceeded the max number of tickets per purchase request.");
        }

        if (adultTickets < infantTickets) {
            throw new InvalidPurchaseException("Not enough adult tickets to infant tickets ratio.");
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
