package com.showbookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.showbookingapp.dao.ShowsDao;
import com.showbookingapp.datastructure.Show;

public class ShowManagementServiceImpl implements ShowManagementService {

	private ShowsDao showsDao;
	
	// max of 10 seats per row and max 26 rows
	private static final int MAX_ROWS = 26;
	private static final int MAX_SEATS_PER_ROW = 10;
		
	@Autowired
	public ShowManagementServiceImpl(ShowsDao showsDao) {
		this.showsDao = showsDao;
	}
	
	@Override
	public boolean createShow(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow) {
		if (isParamsWithinConstraints(numRows, numSeatsPerRow)) {
			return this.showsDao.createShow(showNum, numRows, numSeatsPerRow, cancellationWindow);
		}
		return false;
	}

	@Override
	public Show getShow(int showNum) {
		return this.showsDao.getShow(showNum);
	}
	
	private boolean isParamsWithinConstraints(int numRows, int numSeatsPerRow) {
		return numRows <= MAX_ROWS &&
			   numSeatsPerRow <= MAX_SEATS_PER_ROW;
	}

}
