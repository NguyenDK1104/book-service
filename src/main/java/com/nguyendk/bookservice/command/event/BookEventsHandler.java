package com.nguyendk.bookservice.command.event;

import com.nguyendk.bookservice.command.data.Book;
import com.nguyendk.bookservice.command.data.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {
    @Autowired
    BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookUpdateEvent event) {
        Book book = bookRepository.findById(event.getBookId()).get();
        BeanUtils.copyProperties(event, book);
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookDeletedEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.delete(book);
    }
}
