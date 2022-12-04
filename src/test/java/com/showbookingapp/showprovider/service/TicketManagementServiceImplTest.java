package com.showbookingapp.showprovider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.showbookingapp.showprovider.dao.ShowsDao;
import com.showbookingapp.showprovider.datastructure.BookingInformation;
import com.showbookingapp.showprovider.datastructure.Show;

public class TicketManagementServiceImplTest {
	
	private TicketManagementServiceImpl ticketManagementServiceTest;
	private ShowsDao showsDaoMock;
	private Show showMock;
	
	private static final int SHOW_NUM = 1;
	private static final String PHONE_NUM = "123456789";
	
	private static final int TICKET_NUM = 1;
	private static final int TICKET_NUM_2 = 2;
	private static final int INVALID_TICKET_NUM = -1;
	private static final int NUM_ROWS = 2;
	private static final int NUM_SEATS_PER_ROW = 2;
	private static final int CANCELLATION_WINDOW = 2;
	
	@BeforeEach
	void setup() {
		this.showsDaoMock = mock(ShowsDao.class);
		this.showMock = mock(Show.class);
		this.ticketManagementServiceTest = new TicketManagementServiceImpl(this.showsDaoMock);
	}
	
	@Test
	void givenSuccesfulBookingsShouldBookTicketsReturnIncrementalTicketNumber() {
		when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(true);
		
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), TICKET_NUM);
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), TICKET_NUM_2);
	}
	
	@Test
	void givenUnsuccessfulBookingShouldReturnBookTicketsInvalidTicketNumber() {
		when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(false);
		
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), INVALID_TICKET_NUM);
		
	}
	
	@Test
	void givenBookingWithinCancellationWindowThenCancelTicketsShouldReturnTrue() {
		when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(true);
		when(this.showsDaoMock.cancelBooking(anyInt(), anyString(), anyInt())).thenReturn(true);
		when(this.showsDaoMock.getShow(anyInt())).
			thenReturn(this.showMock);
		when(this.showMock.getCancellationWindow()).thenReturn(CANCELLATION_WINDOW);
		when(this.showMock.getBooking(anyString())).
			thenReturn(new BookingInformation(TICKET_NUM, PHONE_NUM, Instant.now().minusSeconds(60), new ArrayList<String>()));
		
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, seats), TICKET_NUM);
		
		
		assertTrue(this.ticketManagementServiceTest.cancelTickets(TICKET_NUM, PHONE_NUM));
	}
	
	@Test
	void givenBookingCancellationWindowExpiredThenCancelTicketsShouldReturnFalse() {
		when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(true);
		when(this.showsDaoMock.cancelBooking(anyInt(), anyString(), anyInt())).thenReturn(true);
		when(this.showsDaoMock.getShow(anyInt())).
			thenReturn(this.showMock);
		when(this.showMock.getCancellationWindow()).thenReturn(CANCELLATION_WINDOW);
		when(this.showMock.getBooking(anyString())).
			thenReturn(new BookingInformation(TICKET_NUM, PHONE_NUM, Instant.now().minusSeconds(180), new ArrayList<String>()));
		
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, seats), TICKET_NUM);
		
		
		assertFalse(this.ticketManagementServiceTest.cancelTickets(TICKET_NUM, PHONE_NUM));
	}
}
