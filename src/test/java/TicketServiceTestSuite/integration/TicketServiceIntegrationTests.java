package TicketServiceTestSuite.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeEnum;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.exception.InvalidTicketRequestException;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketServiceIntegrationTests {
    private TicketRequest[] ticketRequests;
    private TicketPurchaseRequest ticketPurchaseRequest;
    private TicketServiceImpl ticketService;
    @BeforeEach
    public void setup(){
        ticketService = new TicketServiceImpl();
    }
    @AfterEach
    public void teardown(){
        ticketService = null;
    }

    @Test
    public void basicIntegrationTest() {
        ticketRequests = new TicketRequest[]{
                new TicketRequest(TicketTypeEnum.ADULT, 10),
                new TicketRequest(TicketTypeEnum.CHILD, 10),
                new TicketRequest(TicketTypeEnum.INFANT, 10)
        };

        ticketPurchaseRequest = new TicketPurchaseRequest(1, ticketRequests);
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(300, ticketService.getTotal());
        assertEquals(20, ticketService.getNoSeats());
    }

    @Test
    public void given21TicketPurchaseRequestThenThrowException() {
        assertThrows(InvalidTicketRequestException.class, () -> {
            ticketRequests = new TicketRequest[]{
                    new TicketRequest(TicketTypeEnum.ADULT, 21)
            };
            ticketPurchaseRequest = new TicketPurchaseRequest(1, ticketRequests);
            ticketService.purchaseTickets(ticketPurchaseRequest);
        });
    }

    @Test
    public void given21TicketPurchaseThenThrowException() {
        assertThrows(InvalidPurchaseException.class, () -> {
            ticketRequests = new TicketRequest[]{
                    new TicketRequest(TicketTypeEnum.ADULT, 20),
                    new TicketRequest(TicketTypeEnum.CHILD, 1)
            };
            ticketPurchaseRequest = new TicketPurchaseRequest(1, ticketRequests);
            ticketService.purchaseTickets(ticketPurchaseRequest);
        });
    }
    @Test
    public void givenTooManyInfantTicketsThenThrowException() {
        assertThrows(InvalidPurchaseException.class, () -> {
            ticketRequests = new TicketRequest[]{
                    new TicketRequest(TicketTypeEnum.ADULT, 9),
                    new TicketRequest(TicketTypeEnum.INFANT, 10)
            };
            ticketPurchaseRequest = new TicketPurchaseRequest(1, ticketRequests);
            ticketService.purchaseTickets(ticketPurchaseRequest);
        });
    }
}
