package uk.gov.dwp.uc.pairtest.service;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeEnum;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Map;

public class TicketServiceImpl implements TicketService {


    private long total;
    private int noSeats;


    public TicketServiceImpl(long total, int noSeats) {
        this.total = total;
        this.noSeats = noSeats;
    }
    public TicketServiceImpl(){}

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

        processPurchaseRequest(ticketPurchaseRequest);
        long accountId = ticketPurchaseRequest.getAccountId();
        TicketPaymentServiceImpl paymentService = new TicketPaymentServiceImpl();
        paymentService.makePayment(accountId, (int) this.getTotal());

        SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();
        seatReservationService.reserveSeat(accountId, this.getNoSeats());
    }

    private void processPurchaseRequest(TicketPurchaseRequest ticketPurchaseRequest) {
        TicketRequest[] requests = ticketPurchaseRequest.getTicketTypeRequests();
        Arrays.stream(requests).forEach(ticketRequest -> {
            int cost = getTicketPrice(ticketRequest.getTicketType());
            calculateTotal(cost * ticketRequest.getNoOfTickets());
            boolean requireSeats = !ticketRequest.getTicketType().equals(TicketTypeEnum.INFANT);
            if (requireSeats) {
                calculateSeats(ticketRequest.getNoOfTickets());
            }
        });
    }

    private int getTicketPrice(TicketTypeEnum type) {
        Map<TicketTypeEnum, Integer> map = Map.of(TicketTypeEnum.ADULT,20, TicketTypeEnum.CHILD, 10, TicketTypeEnum.INFANT, 0);
        return map.get(type);
    }

    private void calculateTotal(int total) {
        this.total += total;
    }

    private void calculateSeats(int seats) {
        this.noSeats += seats;
    }

    public long getTotal() {
        return total;
    }

    public int getNoSeats() {
        return noSeats;
    }
}
