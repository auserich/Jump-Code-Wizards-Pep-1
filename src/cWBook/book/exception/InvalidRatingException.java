package cWBook.book.exception;

public class InvalidRatingException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidRatingException(String message) {
		super(message);
	}

}
