package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;
import java.util.List;

public interface IEventService {

    boolean processEvent(EventRequest request);

}