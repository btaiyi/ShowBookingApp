package com.showbookingapp.showprovider.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showbookingapp.showprovider.dao.ShowsDao;
import com.showbookingapp.showprovider.datastructure.BookingInformation;
import com.showbookingapp.showprovider.datastructure.Show;

@Service
public class TicketManagementServiceImpl implements TicketManagementService {

	private ShowsDao showsDao;
	
	private int ticketNumber;
	private Map<Integer, Integer> ticketNumToShowNumLookUptable;
	
	private static final int INVALID_TICKET = -1;
	
	@Autowired
	public TicketManagementServiceImpl(ShowsDao showsDao) {
		this.showsDao = showsDao;
		this.ticketNumber = 1;
		this.ticketNumToShowNumLookUptable = new HashMap<>();
	}
	
	@Override
	public int bookTickets(int showNum, String phoneNum, List<String> seatsToBook) {	

		if(this.showsDao.createBooking(showNum, phoneNum, seatsToBook, this.ticketNumber)) {
			this.ticketNumToShowNumLookUptable.put(this.ticketNumber,showNum);
			return this.ticketNumber++;
		}
		
		return INVALID_TICKET;
	}

	@Override
	public boolean cancelTickets(int ticketNum, String phoneNum) {
		boolean cancellationSuccess = false;
		if (isValidCancellation(ticketNum,phoneNum)
		    &&
		    this.showsDao.cancelBooking(this.ticketNumToShowNumLookUptable.get(ticketNum), 
					phoneNum, ticketNum)) {
			this.ticketNumToShowNumLookUptable.remove(ticketNum);
			cancellationSuccess = true;
		}
		return cancellationSuccess;
	}
	
	private boolean isValidCancellation(int ticketNum, String phoneNum) {
		boolean isValid = false;
		if (this.ticketNumToShowNumLookUptable.containsKey(ticketNum)) {
			int showNum = this.ticketNumToShowNumLookUptable.get(ticketNum);
			Show theShow = this.showsDao.getShow(showNum);
			
			if (theShow != null) {
				int cancellationWindow = theShow.getCancellationWindow();
				BookingInformation booking = theShow.getBooking(phoneNum);
				
				if (booking != null) {
					BigDecimal duration = BigDecimal.valueOf(
							Duration.between(booking.getBookingConfirmationTime(), 
									Instant.now()).getSeconds() / 60.0f);
					
					if (duration.intValue() <= cancellationWindow) {
						isValid = true;
					}
				}
				
			}			
			
		}
		
		return isValid;
	}
}
