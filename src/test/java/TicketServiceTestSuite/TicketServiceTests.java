package TicketServiceTestSuite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeEnum;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceTests {
    private TicketServiceImpl ticketService;
    private TicketRequest ticketRequest;
    private TicketPurchaseRequest ticketPurchaseRequest;

    @BeforeEach
    public void setup() {
        ticketRequest = mock(TicketRequest.class);
        ticketPurchaseRequest = mock(TicketPurchaseRequest.class);
        ticketService = new TicketServiceImpl();
    }

    @AfterEach
    public void teardown() {
        ticketService = null;
        ticketRequest = null;
        ticketPurchaseRequest = null;
    }

    /**
     * - Considers the above objective, business rules, constraints & assumptions.
     * - Calculates the correct amount for the requested tickets and makes a payment request to the TicketPaymentService.
     * - Calculates the correct no of seats to reserve and makes a seat reservation request to the SeatReservationService.
     * - Rejects any invalid ticket purchase requests. It is up to you to identify what should be deemed as an invalid purchase request.
     */

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(TicketTypeEnum.ADULT, 1, 20),
                Arguments.of(TicketTypeEnum.CHILD, 1, 10),
                Arguments.of(TicketTypeEnum.INFANT, 1, 0)
        );
    }
    @ParameterizedTest(name = "Type: {0}, NoTickets: {1}, expectedTotal: {2}")
    @MethodSource(value = "data")
    public void givenTicketsThenReturnCorrectPrice(TicketTypeEnum type, int noTickets, int expectedTotal) {
        when(ticketPurchaseRequest.getTicketTypeRequests()).thenReturn(new TicketRequest[]{ticketRequest});
        when(ticketRequest.getTicketType()).thenReturn(type);
        when(ticketRequest.getNoOfTickets()).thenReturn(noTickets);
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(expectedTotal, ticketService.getTotal());
    }
    @Test
    public void givenTicketsThenReturnCorrectSeats() {
        when(ticketRequest.getTicketType()).thenReturn(TicketTypeEnum.ADULT);
        when(ticketRequest.getNoOfTickets()).thenReturn(1);
        when(ticketPurchaseRequest.getTicketTypeRequests()).thenReturn(new TicketRequest[]{ticketRequest});
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(1, ticketService.getNoSeats());
    }
}
