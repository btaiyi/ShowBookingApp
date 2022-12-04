package com.showbookingapp.showprovider.comparator;

import java.util.Comparator;

public class SeatsComparator implements Comparator<String> {

	@Override
	public int compare(String seat1, String seat2) {
		// ASSUMPTION: Seat string will always be letter followed by number
		char seat1Row = seat1.charAt(0);
		char seat2Row = seat2.charAt(0);
		
		if (seat1Row < seat2Row) {
			return -1;
		} else if (seat1Row > seat2Row) {
			return 1;
		} else {
			int seat1Num = Integer.parseInt(seat1.substring(1));
			int seat2Num = Integer.parseInt(seat2.substring(1));
			
			return seat1Num - seat2Num;
		}
	}

}
