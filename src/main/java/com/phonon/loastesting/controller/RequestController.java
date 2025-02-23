package com.phonon.loastesting.controller;

import com.phonon.loastesting.exception.RequestNotFound;
import com.phonon.loastesting.model.Request;
import com.phonon.loastesting.model.RequestData;
import com.phonon.loastesting.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("/request/")
public class RequestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    private final RequestService requestService;

    public RequestController(RequestService requestService){
        this.requestService = requestService;
    }



    @PostMapping("add")
    public ResponseEntity<UUID> create(@RequestBody RequestData requestData){
        LOGGER.info("Got requestData---> "+ requestData);
        if(requestData == null){
            throw  new IllegalArgumentException("RequestData is NULL.");
        }
        requestService.create(requestData);
        return ResponseEntity.ok(requestData.getUuid());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Request> update(String reqeustId) throws RequestNotFound {
        return null;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<RequestData>> getAll(){
        return ResponseEntity.ok(requestService.getAll());
    }

}
