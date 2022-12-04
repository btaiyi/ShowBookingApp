package com.showbookingapp.showprovider.service;

import java.util.List;

public interface TicketManagementService {
	
	public int bookTickets(int showNum, String phoneNum, List<String> seatsToBook);
	
	public boolean cancelTickets(int ticketNum, String phoneNum);
}
