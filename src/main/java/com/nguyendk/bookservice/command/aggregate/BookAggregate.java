package com.nguyendk.bookservice.command.aggregate;

import com.nguyendk.bookservice.command.command.CreateBookCommand;
import com.nguyendk.bookservice.command.command.DeleteBookCommand;
import com.nguyendk.bookservice.command.command.UpdateBookCommand;
import com.nguyendk.bookservice.command.event.BookCreateEvent;
import com.nguyendk.bookservice.command.event.BookDeletedEvent;
import com.nguyendk.bookservice.command.event.BookUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;
    public BookAggregate() {

    }
    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        BookCreateEvent bookCreateEvent =
                new BookCreateEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreateEvent);
        bookCreateEvent.setIsReady(createBookCommand.getIsReady());
        AggregateLifecycle.apply(bookCreateEvent);
    }
    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand) {
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(updateBookCommand, bookUpdateEvent);
        bookUpdateEvent.setIsReady(updateBookCommand.getIsReady());
        AggregateLifecycle.apply(bookUpdateEvent);
    }
    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand) {
        BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
        AggregateLifecycle.apply(bookDeletedEvent);
    }
    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event) {
        this.bookId = event.getBookId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
    }
    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.bookId = bookId;
    }
}
