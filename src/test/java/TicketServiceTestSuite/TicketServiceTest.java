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

public class TicketServiceTest {
    private TicketServiceImpl ticketService;
    private TicketRequest ticketRequest;
    private TicketPurchaseRequest ticketPurchaseRequest;

    @BeforeEach
    public void setup() {
        ticketService = new TicketServiceImpl();
        ticketRequest = new TicketRequest(TicketTypeEnum.ADULT, 1);
        ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
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
                Arguments.of(TicketTypeEnum.ADULT, 1, 1, TicketTypeEnum.ADULT),
                Arguments.of(TicketTypeEnum.ADULT, 2, 2, TicketTypeEnum.ADULT),
                Arguments.of(TicketTypeEnum.CHILD, 1, 1, TicketTypeEnum.CHILD),
                Arguments.of(TicketTypeEnum.CHILD, 2, 2, TicketTypeEnum.CHILD),
                Arguments.of(TicketTypeEnum.INFANT, 1, 1, TicketTypeEnum.INFANT),
                Arguments.of(TicketTypeEnum.INFANT, 2, 2, TicketTypeEnum.INFANT)
        );
    }
    @ParameterizedTest(name = "TicketTypeEnum: {0}, tickets:{1}, expected: {2}, {3}")
    @MethodSource(value = "data")
    public void givenTicketRequests(TicketTypeEnum type, int noOfTickets, int expectedTickets, TicketTypeEnum expectedType) {
        TicketRequest ticketRequest1 = new TicketRequest(type, noOfTickets);
        assertEquals(expectedTickets, ticketRequest1.getNoOfTickets());
        assertEquals(expectedType, ticketRequest1.getTicketType());
    }

    @Test
    public void givenTicketsThenReturnCorrectPrice() {
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(20, ticketService.getTotal());
    }
    @Test
    public void givenTicketsThenReturnCorrectSeats() {
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(1, ticketService.getNoSeats());
    }

//    @Test
//    public void givenInvalidAccountIdThenThrowError() {
//        ticketRequest = new TicketRequest(TicketRequest.TicketTypeEnum.ADULT, 1);
//        ticketPurchaseRequest = new TicketPurchaseRequest(0, new TicketRequest[]{ticketRequest});
//        ticketService.purchaseTickets(ticketPurchaseRequest);
//        assertThrows(InvalidPurchaseException.class, () -> {
//        });
//    }
}
