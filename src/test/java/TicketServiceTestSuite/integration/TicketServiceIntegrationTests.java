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
                new TicketRequest(TicketTypeEnum.ADULT, 5),
                new TicketRequest(TicketTypeEnum.CHILD, 5),
                new TicketRequest(TicketTypeEnum.INFANT, 5)
        };
        ticketPurchaseRequest = new TicketPurchaseRequest(1, ticketRequests);
        ticketService.purchaseTickets(ticketPurchaseRequest);
        assertEquals(150, ticketService.getTotal());
        assertEquals(10, ticketService.getNoSeats());
    }

    @Test
    public void given21TicketRequestThenThrowException() {
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
}
