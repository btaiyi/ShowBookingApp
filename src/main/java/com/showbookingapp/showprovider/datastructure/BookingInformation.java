package com.showbookingapp.showprovider.datastructure;

import java.time.Instant;
import java.util.List;

public class BookingInformation {
	
	private int ticketNum;
	private String phoneNum;
	private Instant bookingConfirmationTime;
	private List<String> seatsBooked;
	
	public BookingInformation(int ticketNum, String phoneNum, Instant bookingConfirmationTime,
			List<String> seatsBooked) {
		this.ticketNum = ticketNum;
		this.phoneNum = phoneNum;
		this.bookingConfirmationTime = bookingConfirmationTime;
		this.seatsBooked = seatsBooked;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Instant getBookingConfirmationTime() {
		return bookingConfirmationTime;
	}
	public void setBookingConfirmationTime(Instant bookingConfirmationTime) {
		this.bookingConfirmationTime = bookingConfirmationTime;
	}
	public List<String> getSeatsBooked() {
		return seatsBooked;
	}
	public void setSeatsBooked(List<String> seatsBooked) {
		this.seatsBooked = seatsBooked;
	}
	
	public String toBookingDetailsString() {
		return "Booking Detail - ticket no.: " + ticketNum + ", phone number: " + phoneNum + ", seats booked: " + seatsBooked + "]";
	}
}
