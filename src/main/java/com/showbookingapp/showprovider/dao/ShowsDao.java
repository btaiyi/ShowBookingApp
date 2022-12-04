package com.showbookingapp.showprovider.dao;

import java.util.List;

import com.showbookingapp.showprovider.datastructure.Show;

public interface ShowsDao {
	
	public boolean createShow(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow);
	
	public Show getShow(int showNum);
	
	public boolean createBooking(int showNum, String phoneNum, List<String> seats, int ticketNum);
}
