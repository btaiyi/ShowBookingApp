package com.showbookingapp.showprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.showbookingapp.showprovider.datastructure.Show;
import com.showbookingapp.showprovider.service.ShowManagementService;
import com.showbookingapp.showprovider.service.TicketManagementService;

@Component
public class ShowManagementConsoleApp implements CommandLineRunner {

	private Scanner inputReader;
	private ShowManagementService showManagementService;
	private TicketManagementService ticketManagementService;
	
	@Autowired
	public ShowManagementConsoleApp(ShowManagementService showManagementService, TicketManagementService ticketManagementService) {
		this.inputReader = new Scanner(System.in);
		this.showManagementService = showManagementService;
		this.ticketManagementService = ticketManagementService;
	}
	
	@Override
	public void run(String... args) {
		System.out.println("Welcome to show booking application!");
		System.out.println("Type \"commands\" to view list of commands");
		
        while (true) {
        	System.out.println("\nPlease Enter Command: ");
        	this.handleCommand();
        }
	}
	
	private void printCommands() {
		System.out.println("----- ADMIN COMMANDS -----");
		System.out.
			println("setup <Show Number> <Number of rows> <Numbers of seats per row> <Cancellation window in minutes>");
		
		System.out.println("view <Show Number>");
		
		System.out.println("\n----- BUYER COMMANDS -----");
		
		System.out.println("availability <Show Number>");
		
		System.out.println("book <Show Number> <Phone#> <Comma separated list of seats>");
		
		System.out.println("cancel <Ticket#> <Phone#>");
	}
	
	private void handleCommand() {
		try {
			String command = inputReader.next();
			
			switch(command.toLowerCase()) {
				case "setup":
					handleSetupCommand();
					break;
				case "view":
					handleViewCommand();
					break;
				case "availability":
					handleAvailabilityCommand();
					break;
				case "book":
					handleBookCommand();
					break;
				case "cancel":
					handleCancelCommand();
					break;
				case "commands":
					printCommands();
					break;
				case "exit":
					handleExitCommand();
				default:
					System.out.println("Did not understand command. Please try again!");
					break;
			}
		} catch (Exception e) {
			// To be improved don't catch a generic exception
			System.out.println("Did not understand command. Please try again!");
			inputReader.nextLine(); // clear the inputs
		}
		
	}
	
	private void handleSetupCommand() throws Exception {
		
		int showNum = inputReader.nextInt();
		int numRows = inputReader.nextInt();
		int numSeatsPerRow = inputReader.nextInt();
		int cancellationWindow = inputReader.nextInt();
		
		if (this.showManagementService.createShow(showNum, numRows, numSeatsPerRow, cancellationWindow)) {
			System.out.println("Show created successfully!");
		} else {
			System.out.println("ERROR: Show could not be created! Please check you have entered the required details correctly!");
		}
	}
	
	private void handleViewCommand() throws Exception {
		int showNum = inputReader.nextInt();
		
		Show theShow = this.showManagementService.getShow(showNum);
		if (theShow == null) {
			System.out.println("ERROR: Show not found!");
		} else {
			System.out.println("Show Number: " + theShow.getShowNumber());
			System.out.println("----- Booking Details -----");
			theShow.printBookings();
			System.out.println("----- End of booking details -----");
		}
	}
	
	private void handleAvailabilityCommand() throws Exception {
		int showNum = inputReader.nextInt();
		
		Show theShow = this.showManagementService.getShow(showNum);
		if (theShow == null) {
			System.out.println("Show not found!");
		} else {
			System.out.println("Show Number: " + theShow.getShowNumber());
			System.out.println("Available Seats: ");
			theShow.printAvailableSeats();
		}
	}
	private void handleBookCommand() throws Exception {
		int showNum = inputReader.nextInt();
		String phoneNum = inputReader.next();
		
		String allSeats = inputReader.nextLine();
		List<String> seats = new ArrayList<String>();
		String[] tokenizedSeats = allSeats.split(",");
		for (String seat : tokenizedSeats) {
			seats.add(seat.trim());
		}
		
		int ticketNum = this.ticketManagementService.bookTickets(showNum, phoneNum, seats);
		if (ticketNum > 0) {
			System.out.println("Booking successful!");
			System.out.println("Your ticket number is " + ticketNum);
		} else {
			System.out.println("ERROR: Could not process booking! Please check you have entered the required details correctly!");
		}
	}
	
	private void handleCancelCommand() throws Exception {
		int ticketNum = inputReader.nextInt();
		String phoneNum = inputReader.next();
		
		if (this.ticketManagementService.cancelTickets(ticketNum, phoneNum)) {
			System.out.println("Ticket number " + ticketNum + " cancelled successfully! Hope to see you again!");
		} else {
			System.out.println("ERROR: Could not process cancellation! Please check that you have entered the required details correctly!");
		}
	}
	
	private void handleExitCommand() {
		System.exit(0);
	}
	
	
}
