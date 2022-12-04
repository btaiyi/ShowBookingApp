package com.showbookingapp.showprovider.datastructure;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show {
	
	private int showNumber;
	private int numRows;
	private int numSeatsPerRow;
	private Map<String, Integer> seatsForShow; 
	private int cancellationWindow; // in minutes
	private Map<String, BookingInformation> bookings;
	
	private static final int SEAT_UNAVAILABLE = 0;
	private static final int SEAT_AVAILABLE = 1;
	
	public Show(int showNumber, int numRows, int numSeatsPerRow, int cancellationWindow) {
		this.showNumber = showNumber;
		this.numRows = numRows;
		this.numSeatsPerRow = numSeatsPerRow;
		
		this.seatsForShow = new HashMap<>();
		for (int i=0; i<numRows; i++) {
			char rowPrefix = (char) (i + 'A');
			for (int j=0; j<numSeatsPerRow; j++) {
				this.seatsForShow.put(rowPrefix + String.valueOf(j + 1), SEAT_AVAILABLE);
			}
		}
		
		this.cancellationWindow = cancellationWindow;
		this.bookings = new HashMap<>();
	}

	public int getShowNumber() {
		return this.showNumber;
	}
	
	public int getCancellationWindow() {
		return this.cancellationWindow;
	}

	public int getNumRows() {
		return this.numRows;
	}

	public int getNumSeatsPerRow() {
		return this.numSeatsPerRow;
	}
	
	public int getTotalSeatsForShow() {
		return this.seatsForShow.size();
	}
	
	public Map<String,Integer> getSeatsForShow() {
		// We can possibly return a copy of this instead of the actual hashmap
		return this.seatsForShow;
	}
	
	public BookingInformation getBooking(String phoneNum) {
		return this.bookings.getOrDefault(phoneNum, null);
	}
	
	public void addBooking(int ticketNum,String phoneNum, Instant bookingConfirmationTime, List<String> seats) {
		this.bookings.put(phoneNum, new BookingInformation(ticketNum, phoneNum, bookingConfirmationTime, seats));
		System.out.println("Booking created, phoneNum: " + phoneNum + ", BookingInfo: " + this.bookings.get(phoneNum));
		for (String seat : seats) {
			this.seatsForShow.put(seat, SEAT_UNAVAILABLE);
		}
	}
	
	@Override
	public String toString() {
		return "Show [showNumber=" + showNumber + ", seatsForShow=" + seatsForShow + ", bookings=" + bookings + "]";
	}
}
