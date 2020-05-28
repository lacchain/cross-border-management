package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;

public interface IEventService {

    boolean processEvent(EventRequest request);

}