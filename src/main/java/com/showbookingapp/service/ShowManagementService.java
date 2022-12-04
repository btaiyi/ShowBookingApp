package com.showbookingapp.service;

import com.showbookingapp.datastructure.Show;

public interface ShowManagementService {
	
	public boolean createShow(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow);
	
	public Show getShow(int showNum);
}
