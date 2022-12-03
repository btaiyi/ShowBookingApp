package com.showbookingapp.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Show {
	
	private int showNumber;
	private List<List<Integer>> seatsForShow; 
	private int cancellationWindow; // in minutes
	
	private static final int SEAT_UNAVAIABLE = 0;
	private static final int SEAT_AVAILABLE = 1;
	
	public Show(int showNumber, int numRows, int numSeatsPerRow, int cancellationWindow) {
		this.showNumber = showNumber;
		
		this.seatsForShow = new ArrayList<>();
		for (int i=0; i<numRows; i++) {
			this.seatsForShow.add(new ArrayList<Integer>());
			for (int j=0; j<numSeatsPerRow; j++) {
				this.seatsForShow.get(i).add(SEAT_AVAILABLE);
			}
		}
		
		this.cancellationWindow = cancellationWindow;
	}

	public int getShowNumber() {
		return this.showNumber;
	}
	
	public int getCancellationWindow() {
		return this.cancellationWindow;
	}

	public int getNumRows() {
		return this.seatsForShow.size();
	}

	public int getNumSeatsPerRow() {
		// ASSUMPTION: Assuming that there is minimum of one row for the show
		return this.seatsForShow.get(0).size();
	}	
}
