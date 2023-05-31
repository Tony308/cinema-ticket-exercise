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
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketRequestException;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketServiceTest {
    private TicketServiceImpl ticketService;
    private TicketRequest ticketRequest;
    private TicketPurchaseRequest ticketPurchaseRequest;

    @BeforeEach
    public void setup() {
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

    static Stream<Arguments> TicketRequestUnitTestData() {
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
    @MethodSource(value = "TicketRequestUnitTestData")
    public void givenTicketRequestsUnitTests(TicketTypeEnum type, int noOfTickets, int expectedTickets, TicketTypeEnum expectedType) {
        TicketRequest ticketRequest1 = new TicketRequest(type, noOfTickets);
        assertEquals(expectedTickets, ticketRequest1.getNoOfTickets());
        assertEquals(expectedType, ticketRequest1.getTicketType());
    }

    static Stream<Arguments> TicketRequestUnitTestErrorData() {
        return Stream.of(
                Arguments.of(TicketTypeEnum.ADULT, 0, "Given invalid number of tickets then, throw InvalidTicketRequestException"),
                Arguments.of(TicketTypeEnum.ADULT, -1, "Given invalid number of tickets then, throw InvalidTicketRequestException")
        );
    }
    @ParameterizedTest(name = "TicketTypeEnum: {0}, tickets:{1}")
    @MethodSource(value = "TicketRequestUnitTestErrorData")
    public void givenTicketRequestsHandleErrors(TicketTypeEnum type, int noOfTickets, String message) {
        assertThrows(InvalidTicketRequestException.class, () -> new TicketRequest(type, noOfTickets),
                message);
    }


    @Test
    public void givenTicketsThenReturnCorrectPrice() {
        ticketRequest = new TicketRequest(TicketTypeEnum.ADULT, 1);
        ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(20, ticketService.getTotal());
    }
    @Test
    public void givenTicketsThenReturnCorrectSeats() {
        ticketRequest = new TicketRequest(TicketTypeEnum.ADULT, 1);
        ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
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
