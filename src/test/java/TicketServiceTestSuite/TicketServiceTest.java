package TicketServiceTestSuite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketServiceTest {
    private TicketServiceImpl ticketService;
    private TicketRequest ticketRequest;
    private TicketPurchaseRequest ticketPurchaseRequest;

    @BeforeEach
    public void setup() {
        ticketService = new TicketServiceImpl();
        ticketRequest = new TicketRequest(TicketRequest.Type.ADULT, 1);
        ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
    }

    @AfterEach
    public void teardown() {
        ticketService = null;
    }

    /**
     * - Considers the above objective, business rules, constraints & assumptions.
     * - Calculates the correct amount for the requested tickets and makes a payment request to the TicketPaymentService.
     * - Calculates the correct no of seats to reserve and makes a seat reservation request to the SeatReservationService.
     * - Rejects any invalid ticket purchase requests. It is up to you to identify what should be deemed as an invalid purchase request.
     */
    @Test
    public void givenTicketsThenReturnCorrectPrice() {
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(20, ticketService.getPrice());
    }
    @Test
    public void givenTicketsThenReturnCorrectSeats() {
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(1, ticketService.getNoSeats());
    }

//    @Test
//    public void givenInvalidAccountIdThenThrowError() {
//        ticketRequest = new TicketRequest(TicketRequest.Type.ADULT, 1);
//        ticketPurchaseRequest = new TicketPurchaseRequest(0, new TicketRequest[]{ticketRequest});
//        ticketService.purchaseTickets(ticketPurchaseRequest);
//        assertThrows(InvalidPurchaseException.class, () -> {
//        });
//    }
}
