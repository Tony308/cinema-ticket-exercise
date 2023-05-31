package TicketServiceTestSuite.unit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeEnum;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketRequestException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketRequestTests {

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
                Arguments.of(TicketTypeEnum.ADULT, -1, "Given invalid number of tickets then, throw InvalidTicketRequestException"),
                Arguments.of(TicketTypeEnum.ADULT, -2, "Given invalid number of tickets then, throw InvalidTicketRequestException")
        );
    }
    @ParameterizedTest(name = "TicketTypeEnum: {0}, tickets:{1}")
    @MethodSource(value = "TicketRequestUnitTestErrorData")
    public void givenTicketRequestsHandleErrors(TicketTypeEnum type, int noOfTickets, String message) {
        assertThrows(InvalidTicketRequestException.class, () -> new TicketRequest(type, noOfTickets),
                message);
    }

}
