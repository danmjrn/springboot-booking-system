package com.samantha.bookingsystem;

import com.samantha.bookingsystem.command.InitializeDataSourceDataCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingSystemApplication implements CommandLineRunner {

    @Autowired
    private InitializeDataSourceDataCommand initializeDataSourceDataCommand;

    public static void main(String[] args) {
        SpringApplication.run(BookingSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializeDataSourceDataCommand.initializeRoles();

        initializeDataSourceDataCommand.initializeAdmins();

        initializeDataSourceDataCommand.initializeGuests();

        initializeDataSourceDataCommand.initializeRooms();
    }
}
