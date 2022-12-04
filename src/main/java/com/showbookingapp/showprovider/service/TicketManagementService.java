package com.showbookingapp.showprovider.service;

import java.util.List;

public interface TicketManagementService {
	
	public int bookTickets(int showNum, String phoneNumber, List<String> seatsToBook);
}
