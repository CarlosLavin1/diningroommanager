package com.example.diningroommanager;

import com.example.diningroommanager.entities.Status;
import com.example.diningroommanager.repositories.StatusRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DiningRoomManagerApplication {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(DiningRoomManagerApplication.class, args);
        insertStatuses(applicationContext);
    }

    private static void insertStatuses(ApplicationContext context){
        var statusRepo = context.getBean(StatusRepository.class);

        var statuses = getDefaultStatuses();

        for (var status: statuses) {

            if(!statusRepo.existsByValue(status.getValue())){
                statusRepo.save(status);
            }
        }
    }

    private static List<Status> getDefaultStatuses(){
        var values = new ArrayList<Status>();

        values.add(new Status("pending", "Pending..."));
        values.add(new Status("approved", "Approved"));
        values.add(new Status("denied", "Denied"));

        return values;
    }
}
