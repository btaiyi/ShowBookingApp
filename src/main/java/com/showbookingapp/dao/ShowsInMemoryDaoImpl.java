package com.showbookingapp.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.showbookingapp.datastructure.Show;

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
	
	

}
