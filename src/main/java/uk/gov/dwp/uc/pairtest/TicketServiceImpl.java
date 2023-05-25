package uk.gov.dwp.uc.pairtest;

import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketPurchaseRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;


public class TicketServiceImpl implements TicketService {

    private long price;
    private int noSeats;
    private SeatReservationService seatReservationService;
    private TicketServiceImpl ticketService;

    /**
     *
     ## Business Rules

     - There are 3 types of tickets i.e. Infant, Child, and Adult.
     - The ticket prices are based on the type of ticket (see table below).
     - The ticket purchaser declares how many and what type of tickets they want to buy.
     - Multiple tickets can be purchased at any given time.
     - Only a maximum of 20 tickets that can be purchased at a time.
     - Infants do not pay for a ticket and are not allocated a seat. They will be sitting on an Adult's lap.
     - Child and Infant tickets cannot be purchased without purchasing an Adult ticket.

     |   Ticket Type    |     Price   |
     | ---------------- | ----------- |
     |    INFANT        |    £0       |
     |    CHILD         |    £10      |
     |    ADULT         |    £20      |

     - There is an existing TicketPaymentService responsible for taking payments.
     - There is an existing SeatReservationService responsible for reserving seats.

     ## Constraints

     - The TicketService interface **CANNOT** be modified.
     - Any Java code in the thirdparty.* packages **CANNOT** be modified.
     - The TicketTypeRequest class SHOULD be immutable.

     ## Assumptions

     You can assume:
     - All accounts with an id greater than zero are valid. They also have sufficient funds to pay for any no of tickets.
     - The TicketPaymentService implementation is an external provider with no defects. You do not need to worry about how the actual payment happens.
     - The payment will always go through once a payment request has been made to the TicketPaymentService.
     - The SeatReservationService implementation is an external provider with no defects. You do not need to worry about how the seat reservation algorithm works.
     - The seat will always be reserved once a reservation request has been made to the SeatReservationService.


     ## Your Task

     Provide a working implementation of a TicketService that:

     - Considers the above objective, business rules, constraints & assumptions.
     - Calculates the correct amount for the requested tickets and makes a payment request to the TicketPaymentService.
     - Calculates the correct no of seats to reserve and makes a seat reservation request to the SeatReservationService.
     - Rejects any invalid ticket purchase requests. It is up to you to identify what should be deemed as an invalid purchase request.

     */
    /**
     * Should only have private methods other than the one below.
     */
    //TODO: Process ticketRequest. TicketRequest -> TicketPurchaseRequest -> TicketServiceImpl
    @Override
    public void purchaseTickets(TicketPurchaseRequest ticketPurchaseRequest) throws InvalidPurchaseException {

    }

    public long getPrice() {
        return price;
    }

    public int getNoSeats() {
        return noSeats;
    }
}
