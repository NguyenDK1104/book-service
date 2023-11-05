package com.nguyendk.bookservice.query.controller;

import com.nguyendk.bookservice.query.model.BookResponseModel;
import com.nguyendk.bookservice.query.queries.GetAllBookQuery;
import com.nguyendk.bookservice.query.queries.GetBooksQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/getById/{bookId}")
    public BookResponseModel getBookDetail(@PathVariable String bookId) {
        GetBooksQuery getBooksQuery = new GetBooksQuery();
        getBooksQuery.setBookId(bookId);
        BookResponseModel bookResponseModel =
                queryGateway.query(getBooksQuery,
                        ResponseTypes.instanceOf(BookResponseModel.class)).join();
        return bookResponseModel;
    }

    @GetMapping("/getAllBook")
    public List<BookResponseModel> getAllBook() {
        GetAllBookQuery getAllBookQuery = new GetAllBookQuery();
        List<BookResponseModel> list =
                queryGateway.query(getAllBookQuery, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
        return list;
    }
}
