package action;

import dao.BookDao;
import model.Book;

import java.util.List;

import util.*;
public class BookAction {
    public List<Book> showAllBooks() throws Exception{
        return BookDao.getAllBook();
    }
    public List<Book> showValidBook() throws Exception{
        return BookDao.getValidBook();
    }
    public Book getBookInfoById(int bid) throws Exception{
        return BookDao.getBookByBid(bid);
    }
    public returnObj submitBorrowApplication(Book book){

    }
    public returnObj permitBorrowApplication(Book book){

    }
    public returnObj rejectBorrowApplication(Book book){

    }
    public returnObj completeApplication(Book book){

    }
}
