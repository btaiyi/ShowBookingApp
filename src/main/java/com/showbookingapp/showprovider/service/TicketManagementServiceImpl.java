package com.showbookingapp.showprovider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.showbookingapp.showprovider.dao.ShowsDao;

@Service
public class TicketManagementServiceImpl implements TicketManagementService {

	private ShowsDao showsDao;
	
	private int ticketNumber;
	
	private static final int INVALID_TICKET = -1;
	
	@Autowired
	public TicketManagementServiceImpl(ShowsDao showsDao) {
		this.showsDao = showsDao;
		this.ticketNumber = 1;
	}
	
	@Override
	public int bookTickets(int showNum, String phoneNum, List<String> seatsToBook) {	

		if(this.showsDao.createBooking(showNum, phoneNum, seatsToBook, this.ticketNumber)) {
			return this.ticketNumber++;
		}
		
		return INVALID_TICKET;
	}
}
