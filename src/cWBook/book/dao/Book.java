package cWBook.book.dao;

public class Book {
	private int id;
	private String name;
	private int rating;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		// formatting rating in set command to bring min up, max down 1-5
		if(rating < 1) {
			int minRating = 1;
			this.rating = minRating;
		} else if (rating > 5) {
			int maxRating = 5;
			this.rating = maxRating;
		} else {
		this.rating = rating;
		}
	}
	
	@Override
	public String toString() {
		return "Book name " + this.name;
	}
	
	public Book(int id, String name, int rating) {
		this.id = id;
		this.name = name;
		this.rating = rating;
	}

}