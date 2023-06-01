package TicketServiceTestSuite.unit;

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
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(TicketTypeEnum.ADULT, 1, 20),
                Arguments.of(TicketTypeEnum.CHILD, 1, 10),
                Arguments.of(TicketTypeEnum.INFANT, 1, 0),
                Arguments.of(TicketTypeEnum.ADULT, 20, 400),
                Arguments.of(TicketTypeEnum.CHILD, 20, 200)

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
    public void givenATicketThenReturnCorrectSeats() {
        when(ticketRequest.getTicketType()).thenReturn(TicketTypeEnum.ADULT);
        when(ticketRequest.getNoOfTickets()).thenReturn(1);
        when(ticketPurchaseRequest.getTicketTypeRequests()).thenReturn(new TicketRequest[]{ticketRequest});
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(1, ticketService.getNoSeats());
    }

    @Test
    public void givenTicketsThenReturnCorrectSeats() {

        TicketRequest eins = mock(TicketRequest.class);
        TicketRequest zwei = mock(TicketRequest.class);
        TicketRequest drei = mock(TicketRequest.class);

        when(eins.getTicketType()).thenReturn(TicketTypeEnum.ADULT);
        when(zwei.getTicketType()).thenReturn(TicketTypeEnum.CHILD);
        when(drei.getTicketType()).thenReturn(TicketTypeEnum.INFANT);

        when(eins.getNoOfTickets()).thenReturn(10);
        when(zwei.getNoOfTickets()).thenReturn(10);
        when(drei.getNoOfTickets()).thenReturn(10);
        when(ticketPurchaseRequest.getTicketTypeRequests()).thenReturn(new TicketRequest[]{
                eins, zwei, drei
        });
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(20, ticketService.getNoSeats());
    }

    @Test
    public void givenTicketLimitExceededThenThrowException() {
        assertThrows(InvalidPurchaseException.class, () -> {
            when(ticketRequest.getNoOfTickets()).thenReturn(20);
            when(ticketRequest.getTicketType()).thenReturn(TicketTypeEnum.ADULT);
            ticketPurchaseRequest = new TicketPurchaseRequest(1, new TicketRequest[]{ticketRequest, ticketRequest});
            
        });
    }

    @Test
    public void givenTicketLimitThenReturnCorrectCostAndSeats() {
        TicketRequest eins = mock(TicketRequest.class);
        TicketRequest zwei = mock(TicketRequest.class);

        given(eins.getNoOfTickets()).willReturn(10);
        given(zwei.getNoOfTickets()).willReturn(10);

        given(eins.getTicketType()).willReturn(TicketTypeEnum.ADULT);
        given(zwei.getTicketType()).willReturn(TicketTypeEnum.CHILD);
        given(ticketPurchaseRequest.getTicketTypeRequests()).willReturn(new TicketRequest[]{
                eins, zwei
        });
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(20, ticketService.getNoSeats());
        assertEquals(300, ticketService.getTotal());
    }

}
