package com.showbookingapp.dao;

import com.showbookingapp.datastructure.Show;

public interface ShowsDao {
	
	public boolean createShow(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow);
	
	public Show getShow(int showNum);
}
