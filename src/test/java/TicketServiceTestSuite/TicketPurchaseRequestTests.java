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
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketPurchaseRequestTests {
    private TicketRequest ticketRequest;
    private TicketPurchaseRequest ticketPurchaseRequest;

    @BeforeEach
    public void setup() {
        ticketRequest = mock(TicketRequest.class);
    }

    @AfterEach
    public void teardown(){
        ticketRequest = null;
        ticketPurchaseRequest = null;

    }
    static Stream<Arguments> invalidAccountIdData() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(-2)
        );
    }
    @ParameterizedTest(name = "AccountId: {0}")
    @MethodSource(value = "invalidAccountIdData")
    public void givenInvalidAccountIdThenThrowInvalidPurchaseException(int accountId) {
        assertThrows(InvalidPurchaseException.class, () -> {
            ticketPurchaseRequest = new TicketPurchaseRequest(accountId, new TicketRequest[]{ticketRequest});
        });
    }

    @Test
    public void givenNoAdultTicketRequestThenThrowInvalidPurchaseException() {
        assertThrows(InvalidPurchaseException.class, () -> {
            when(ticketRequest.getTicketType()).thenReturn(TicketTypeEnum.CHILD);
            ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
        }, "No Adult ticket");
    }


    @Test
    public void givenValidInputThenCreateTicketPurchaseRequest() {
        when(ticketRequest.getTicketType()).thenReturn(TicketTypeEnum.ADULT);
        ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest});
        assertEquals(1, ticketPurchaseRequest.getAccountId());
        assertEquals(1, ticketPurchaseRequest.getTicketTypeRequests().length);
    }
}
