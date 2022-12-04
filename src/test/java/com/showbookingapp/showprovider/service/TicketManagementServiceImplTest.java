package com.showbookingapp.showprovider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.showbookingapp.showprovider.dao.ShowsDao;

public class TicketManagementServiceImplTest {
	
	private TicketManagementServiceImpl ticketManagementServiceTest;
	private ShowsDao showsDaoMock;
	
	private static final int SHOW_NUM = 1;
	private static final String PHONE_NUM = "123456789";
	
	private static final int TICKET_NUM = 1;
	private static final int TICKET_NUM_2 = 2;
	private static final int INVALID_TICKET_NUM = -1;
	
	@BeforeEach
	void setup() {
		this.showsDaoMock = mock(ShowsDao.class);
		this.ticketManagementServiceTest = new TicketManagementServiceImpl(this.showsDaoMock);
	}
	
	@Test
	void givenSuccesfulBookingsShouldReturnIncrementalTicketNumber() {
		when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(true);
		
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), TICKET_NUM);
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), TICKET_NUM_2);
	}
	
	@Test
	void givenUnsuccessfulBookingShouldReturnInvalidTicketNumber() {
when(this.showsDaoMock.createBooking(anyInt(), anyString(), any(), anyInt())).thenReturn(false);
		
		assertEquals(this.ticketManagementServiceTest.bookTickets(SHOW_NUM, PHONE_NUM, new ArrayList<String>()), INVALID_TICKET_NUM);
		
	}
}
