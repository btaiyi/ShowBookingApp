package com.showbookingapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	private static final int INVALID_ROWS_NEGATIVE = -1;
	private static final int INVALID_ROWS_MAX = 27;
	private static final int INVALID_SEATS_PER_ROW_NEGATIVE = -1;
	private static final int INVALID_SEATS_PER_ROW_MAX = 11;
	private static final int INVALID_CANCELLATION_WINDOW = -1;
	
	@BeforeEach
	void setup() {
		this.showsDaoTest = new ShowsInMemoryDaoImpl();
	}
	
	@Test
	void givenValidShowParamsThenShouldCreateShow() {
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW);
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);
			
		// Attempt to create show with same show number but fail
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS_ALT_VALUE, 
				NUM_SEATS_PER_ROW_ALT_VALUE, CANCELLATION_WINDOW_ALT_VALUE);
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);
	}
	
	@Test
	void givenDuplicatedShowNumThenShouldNotOverwriteShow() {
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW);
		
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getShowNumber(), SHOW_NUM);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumRows(), NUM_ROWS);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getNumSeatsPerRow(), NUM_SEATS_PER_ROW);
		assertEquals(this.showsDaoTest.getShow(SHOW_NUM).getCancellationWindow(), CANCELLATION_WINDOW);	
	}
	
	@Test
	void givenNegativeShowNumberThenShouldNotCreateShow() {
		this.showsDaoTest.createShow(INVALID_SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW);
		
		assertNull(this.showsDaoTest.getShow(INVALID_SHOW_NUM));
	}
	
	@Test
	void givenInvalidRowsThenShouldNotCreateShow() {
		this.showsDaoTest.createShow(SHOW_NUM, INVALID_ROWS_NEGATIVE, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW);
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
		
		this.showsDaoTest.createShow(SHOW_NUM, INVALID_ROWS_MAX, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW);
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}
	
	@Test
	void givenInvalidSeatsPerRowThenShouldNotCreateShow() {
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, INVALID_SEATS_PER_ROW_NEGATIVE, CANCELLATION_WINDOW);
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
		
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, INVALID_SEATS_PER_ROW_MAX, CANCELLATION_WINDOW);
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}
	
	@Test
	void givenInvalidCancellationWindowThenShouldNotCreateShow() {
		this.showsDaoTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, INVALID_CANCELLATION_WINDOW);
		assertNull(this.showsDaoTest.getShow(SHOW_NUM));
	}


}