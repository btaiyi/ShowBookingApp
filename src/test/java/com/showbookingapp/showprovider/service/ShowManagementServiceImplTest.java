package com.showbookingapp.showprovider.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.showbookingapp.showprovider.dao.ShowsInMemoryDaoImpl;
import com.showbookingapp.showprovider.service.ShowManagementServiceImpl;

public class ShowManagementServiceImplTest {
	
	private ShowManagementServiceImpl showManagementServiceTest;
	private ShowsInMemoryDaoImpl showsDaoMock;
	
	private static final int SHOW_NUM = 2;
	private static final int NUM_ROWS = 3;
	private static final int NUM_SEATS_PER_ROW = 4;
	private static final int CANCELLATION_WINDOW = 2;
	
	private static final int INVALID_ROWS_MAX = 27;
	private static final int INVALID_SEATS_PER_ROW_MAX = 11;
	
	@BeforeEach
	void setup() {
		this.showsDaoMock = mock(ShowsInMemoryDaoImpl.class);
		this.showManagementServiceTest = new ShowManagementServiceImpl(this.showsDaoMock);
		
		when(this.showsDaoMock.createShow(anyInt(),anyInt(),anyInt(),anyInt())).thenReturn(true);
	}
	
	@Test
	void givenValidParamsThenShouldCreateShow() {
		assertTrue(this.showManagementServiceTest.createShow(SHOW_NUM, NUM_ROWS, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
	}
	
	@Test
	void givenRowsOutsideOfConstraintThenShouldNotCreateShow() {
		assertFalse(this.showManagementServiceTest.createShow(SHOW_NUM, INVALID_ROWS_MAX, NUM_SEATS_PER_ROW, CANCELLATION_WINDOW));
	}
	
	@Test
	void givenSeatsPerRowOutsideOfConstraintThenShouldNotCreateShow() {
		assertFalse(this.showManagementServiceTest.createShow(SHOW_NUM, NUM_ROWS, INVALID_SEATS_PER_ROW_MAX, CANCELLATION_WINDOW));
	}
}
