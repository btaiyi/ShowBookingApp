package com.showbookingapp.showprovider.dao;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.showbookingapp.showprovider.datastructure.BookingInformation;
import com.showbookingapp.showprovider.datastructure.Show;

@Component
public class ShowsInMemoryDaoImpl implements ShowsDao {
	
	private Map<Integer, Show> shows;
	
	public ShowsInMemoryDaoImpl() {
		this.shows = new HashMap<>();
	}
	
	@Override
	public boolean createShow(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow) {
		if (isValidShowParams(showNum, numRows, numSeatsPerRow, cancellationWindow)) {
			this.shows.put(showNum, new Show(showNum, numRows, numSeatsPerRow, cancellationWindow));
			Show theShow = shows.get(showNum);
			System.out.println("Show Num: " + theShow.getShowNumber());
			System.out.println("Num Rows: " + theShow.getNumRows());
			System.out.println("Num Seats per Row: " + theShow.getNumSeatsPerRow());
			System.out.println("Cancellation Window: " + theShow.getCancellationWindow());
			return true;
		}
		return false;
	}
	
	private boolean isValidShowParams(int showNum, int numRows, int numSeatsPerRow, int cancellationWindow) {
		// ASSUMPTION: Cannot overwrite existing shows that have been created
		return showNum > 0 							&&
			   numRows > 0 							&&
			   numSeatsPerRow > 0 					&&
			   cancellationWindow >= 0				&&
			   !this.shows.containsKey(showNum);
	}

	@Override
	public Show getShow(int showNum) {
		return this.shows.getOrDefault(showNum, null);
	}

	private boolean isSeatsAvailable(int showNum, List<String> seats) {
		boolean isSeatsAvailable = true;
		Show theShow = this.getShow(showNum);
		if (theShow != null) {
			Map<String, Integer> seatsForShow = theShow.getSeatsForShow();
			for (String seat : seats) {
				if (seatsForShow.getOrDefault(seat, 0) == 0) {
					isSeatsAvailable = false;
					break;
				}
			}
		}
		return isSeatsAvailable;
	}

	private BookingInformation getBooking(int showNum, String phoneNumber) {
		BookingInformation booking = null;
		Show theShow = this.getShow(showNum);
		if (theShow != null) {
			booking = theShow.getBooking(phoneNumber);
		}
		return booking;
	}

	@Override
	public boolean createBooking(int showNum, String phoneNum, List<String> seats, int ticketNum) {
		boolean bookingSuccess = false;
		if (isValidBooking(showNum,phoneNum,seats)) {
			this.getShow(showNum).addBooking(ticketNum, phoneNum, Instant.now(), seats);
			bookingSuccess = true;
		}
		
		return bookingSuccess;
	}
	
	private boolean isValidBooking(int showNum, String phoneNum, List<String> seatsToBook) {
		return this.getShow(showNum) != null				&&
			   this.isSeatsAvailable(showNum, seatsToBook)	&&
			   this.getBooking(showNum, phoneNum) == null;
	}
	
	
	
	

}
