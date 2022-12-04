package com.showbookingapp.showprovider.datastructure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.showbookingapp.showprovider.comparator.SeatsComparator;

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
		for (String seat : seats) {
			this.seatsForShow.put(seat, SEAT_UNAVAILABLE);
		}
	}
	
	public void removeBooking(String phoneNum) {
		BookingInformation booking = this.bookings.getOrDefault(phoneNum, null);
		
		if(booking != null) {
			for (String bookedSeats : booking.getSeatsBooked()) {
				this.seatsForShow.put(bookedSeats, SEAT_AVAILABLE);
			}
			
			this.bookings.remove(phoneNum);
		}
	}
	
	public void printBookings() {
		for (Map.Entry<String, BookingInformation> booking : this.bookings.entrySet()) {
			System.out.println(booking.getValue().toBookingDetailsString());
		}
	}
	
	public void printAvailableSeats() {
		List<String> seats = new ArrayList<String>();
		
		for (Map.Entry<String, Integer> seatAvailability : this.seatsForShow.entrySet()) {
			if (seatAvailability.getValue() == SEAT_AVAILABLE) {
				seats.add(seatAvailability.getKey());
			}
		}
		
		Collections.sort(seats, new SeatsComparator());
		
		for (int i=0; i<seats.size(); i++) {
			System.out.print(seats.get(i) + " ");
			if ((i + 1 < seats.size()) && (seats.get(i+1).charAt(0) != seats.get(i).charAt(0))) {
				System.out.println("");
			}
			
		}
	}
}
