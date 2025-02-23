package com.phonon.loastesting.service;

import com.phonon.loastesting.enums.RequestStatus;
import com.phonon.loastesting.exception.RequestNotFound;
import com.phonon.loastesting.model.Request;
import com.phonon.loastesting.model.RequestData;
import com.phonon.loastesting.repository.RequestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    private final AsyncRequestProcessor asyncRequestProcessor;

    public RequestService(RequestRepository requestRepository, AsyncRequestProcessor asyncRequestProcessor) {
        this.requestRepository = requestRepository;
        this.asyncRequestProcessor = asyncRequestProcessor;
    }

    @Transactional
    public RequestData create(RequestData requestData){
        Request request = requestRepository.save(mapRequestDataToRequest(requestData));
        requestData.setUuid(request.getUuid());
        LOGGER.info("Request saved in DB with uuid: {}",requestData.getUuid());
        requestData.setName(request.getName());
        requestData.setTimestamp(request.getRequestTime());
        asyncRequestProcessor.sendMessage(requestData);
        return requestData;
    }

    @Transactional
    public RequestData update(UUID requestId) throws RequestNotFound {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if(optionalRequest.isEmpty()){
            throw new RequestNotFound("Request not found with id: "+requestId);
        }
        Request request = optionalRequest.get();
        request.setRequestStatus(RequestStatus.PROCESSED);
        request.setRequestTime(new Timestamp(System.currentTimeMillis()));
        request =  requestRepository.save(request);
        return mapRequestToRequestData(request);
    }

    public List<RequestData> getAll() {
        List<RequestData> requestDataList = new ArrayList<>();
        requestRepository.findAll().forEach(request -> {
            requestDataList.add(mapRequestToRequestData(request));
        });
        return requestDataList;
    }

    private RequestData mapRequestToRequestData(Request request) {
        RequestData requestData = new RequestData();
        requestData.setName(request.getName());
        requestData.setTimestamp(request.getRequestTime());
        requestData.setUrl(requestData.getUrl());
        requestData.setRequestStatus(request.getRequestStatus());
        requestData.setUuid(request.getUuid());
        return requestData;
    }


    private Request mapRequestDataToRequest(RequestData requestData){
        if(requestData == null){
            throw new IllegalArgumentException("RequestData is null");
        }
        Request request = new Request(requestData.getName());
        return request;
    }
}
