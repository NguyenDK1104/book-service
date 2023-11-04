package com.nguyendk.bookservice.command.controller;

import com.nguyendk.bookservice.command.command.CreateBookCommand;
import com.nguyendk.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(name = "/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model) {
        CreateBookCommand command =
                new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        commandGateway.sendAndWait(command);
        return "added Book";
    }
}
