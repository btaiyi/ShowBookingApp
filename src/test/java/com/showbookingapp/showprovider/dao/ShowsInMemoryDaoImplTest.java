package com.showbookingapp.showprovider.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.showbookingapp.showprovider.dao.ShowsInMemoryDaoImpl;

class ShowsInMemoryDaoImplTest {
	
	ShowsInMemoryDaoImpl showsDaoTest;
	
	private static final int SHOW_NUM = 1;
	private static final int NUM_ROWS = 2;
	private static final int NUM_SEATS_PER_ROW = 3;
	private static final int CANCELLATION_WINDOW = 1;
	
	private static final int NUM_ROWS_ALT_VALUE = 3;
	private static final int NUM_SEATS_PER_ROW_ALT_VALUE = 4;
	private static final int CANCELLATION_WINDOW_ALT_VALUE = 2;
	private static final int INVALID_SHOW_NUM = -1;
	private static final int INVALID_ROWS = -1;
	private static final int INVALID_SEATS_PER_ROW = -1;
	private static final int INVALID_CANCELLATION_WINDOW = -1;
	
	private static final String PHONE_NUM = "123456789";
	private static final int TICKET_NUM = 1;
	private static final String PHONE_NUM_2 = "123456781";
	private static final int TICKET_NUM_2 = 2;
	private static final int INVALID_TICKET_NUM = -1;
	
	@BeforeEach
	void setup() {
		this.showsDaoTest = new ShowsInMemoryDaoImpl();
	}
	
	@Test
	void givenValidShowParamsThenShouldCreateShow() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getTotalSeatsForShow(), NUM_ROWS * NUM_SEATS_PER_ROW);
	}
	
	@Test
	void givenDuplicatedShowNumThenShouldNotOverwriteShow() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getTotalSeatsForShow(), NUM_ROWS * NUM_SEATS_PER_ROW);
		
		// Attempt to create show with same show number but fail
		assertFalse(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS_ALT_VALUE, 
				NUM_SEATS_PER_ROW_ALT_VALUE, CANCELLATION_WINDOW_ALT_VALUE));
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getTotalSeatsForShow(), NUM_ROWS * NUM_SEATS_PER_ROW);
	}
	
	@Test
	void givenNegativeShowNumberThenShouldNotCreateShow() {
		assertFalse(this.showsDaoTest.createShow(INVALID_SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertNull(this.showsDaoTest.getShow(INVALID_SHOW_NUM));
	}
	
	@Test
	void givenNegativeRowsThenShouldNotCreateShow() {
		assertFalse(this.showsDaoTest.createShow(SHOW_NUM, INVALID_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}
	
	@Test
	void givenNegativeSeatsPerRowThenShouldNotCreateShow() {
		assertFalse(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, INVALID_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}
	
	@Test
	void givenNegativeCancellationWindowThenShouldNotCreateShow() {
		assertFalse(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, INVALID_CANCELLATION_WINDOW));
		
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}
	
	@Test
	void givenValidSeatsThenCreateBookingShouldReturnTrue() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
	}
	
	@Test
	void givenInvalidSeatsThenCreateBookingShouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A11");
		
		assertFalse(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
	}
	
	@Test
	void givenUnavailableSeatsThenCreateBookingShouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		
		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
		
		assertFalse(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM_2, seats, TICKET_NUM_2));
	}
	
	@Test
	void givenBookingFromSamePhoneNumThenCreateBookingShouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");

		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
		
		List<String> seats2 = new ArrayList<String>();
		seats.add("A2");
		assertFalse(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats2, TICKET_NUM_2));
	}
	
	@Test
	void givenInvalidShowThenCreateBookingShouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");

		assertFalse(this.showsDaoTest.createBooking(INVALID_SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
	}
	
	@Test
	void givenValidShowNumAndTicketNumAndPhoneNumThenCancelBookingShouldReturnTrue() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
		
		assertTrue(this.showsDaoTest.cancelBooking(SHOW_NUM, PHONE_NUM, TICKET_NUM));
	}
	
	@Test
	void givenShowNotFoundThenCancelBookingShouldReturnFalse() {		
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		assertFalse(this.showsDaoTest.cancelBooking(INVALID_SHOW_NUM, PHONE_NUM, TICKET_NUM));
	}
	
	@Test
	void givenTicketNumNotFoundThenCancelBookingSHouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
		
		assertFalse(this.showsDaoTest.cancelBooking(SHOW_NUM, PHONE_NUM, INVALID_TICKET_NUM));
	}
	
	@Test
	void givenPhoneNumNotFoundThenCancelBookingSHouldReturnFalse() {
		assertTrue(this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
		
		List<String> seats = new ArrayList<String>();
		seats.add("A1");
		seats.add("A2");
		assertTrue(this.showsDaoTest.createBooking(SHOW_NUM, PHONE_NUM, seats, TICKET_NUM));
		
		assertFalse(this.showsDaoTest.cancelBooking(SHOW_NUM, PHONE_NUM_2, TICKET_NUM));
	}

}
