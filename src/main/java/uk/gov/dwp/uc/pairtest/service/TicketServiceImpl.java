package uk.gov.dwp.uc.pairtest.service;

import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeEnum;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.Map;

public class TicketServiceImpl implements TicketService {


    private long total;
    private int noSeats;
    private SeatReservationService seatReservationService;
    private TicketServiceImpl ticketService;

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

//        todo:
//         1. process ticket
//              -
//         * 2. calculate total
//         * 3. process payment
//         * 4. calculate seats
//         * 5. process seatService
        TicketRequest[] requests = ticketPurchaseRequest.getTicketTypeRequests();
        Arrays.stream(requests).forEach(ticketRequest -> {
            int cost = getTicketPrice(ticketRequest.getTicketType());
            calculateTotal(cost * ticketRequest.getNoOfTickets());

        });

        //todo: calculate seats after payment process
        calculateSeats();
    }

    private int getTicketPrice(TicketTypeEnum type) {
        Map<TicketTypeEnum, Integer> map = Map.of(TicketTypeEnum.ADULT,20, TicketTypeEnum.CHILD, 10, TicketTypeEnum.INFANT, 0);
        return map.get(type);
    }

    private void calculateTotal(int total) {
        this.total = total;
    }

    private void calculateSeats() {
        this.noSeats = -1;
    }

    public long getTotal() {
        return total;
    }

    public int getNoSeats() {
        return noSeats;
    }
}
