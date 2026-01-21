package org.stadium.eventservice.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.stadium.common.proto.EventListResponse;
import org.stadium.common.proto.EventResponse;
import org.stadium.common.proto.EventSearchRequest;
import org.stadium.common.proto.StadiumEventServiceGrpc;
import org.stadium.eventservice.dto.EventDto;
import org.stadium.eventservice.service.EventService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@RequiredArgsConstructor
public class GrpcEventService extends StadiumEventServiceGrpc.StadiumEventServiceImplBase {

    private final EventService eventService;

    @Override
    public void searchEvents(EventSearchRequest request, StreamObserver<EventListResponse> responseObserver) {
        String query = request.getQuery();

        List<EventDto> results = query.isEmpty() ? eventService.getAllEvents() : eventService.searchEvents(query);

        List<EventResponse> protoEvents = results.stream()
                .map(this::toProto)
                .collect(Collectors.toList());

        EventListResponse response = EventListResponse.newBuilder()
                .addAllEvents(protoEvents)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Helper to convert DTO -> Proto
    private EventResponse toProto(EventDto dto) {
        return EventResponse.newBuilder()
                .setId(dto.getId())
                .setTitle(dto.getTitle())
                .setCategory(dto.getCategory())
                .setDateTime(dto.getDate().toString())
                .setLocation(dto.getLocation())
                .setPrice(dto.getPrice().doubleValue())
                .setAvailableSeats(dto.getAvailableSeats())
                .setStatus(dto.getStatus())
                .build();
    }
}
