package ntut.csie.team5.adapter.gateway.repository.springboot.figure.line;

import ntut.csie.team5.entity.model.figure.line.Endpoint;

import java.util.ArrayList;
import java.util.List;

public class EndpointMapper {
    
    public static List<Endpoint> transformToDomain(List<EndpointData> datas) {
        List<Endpoint> endpoints = new ArrayList<>();
        datas.forEach(endpointData -> endpoints.add(transformToDomain(endpointData)));
        return endpoints;
    }

    public static Endpoint transformToDomain(EndpointData data) {
        Endpoint endpoint = new Endpoint(
                data.getEndpointId(),
                data.getPositionX(),
                data.getPositionY(),
                data.getConnectedFigureId()
        );

        return endpoint;
    }

    public static EndpointData transformToData(Endpoint endpoint) {
        EndpointData endpointData = new EndpointData(
                endpoint.getId(),
                endpoint.getPositionX(),
                endpoint.getPositionY(),
                endpoint.getConnectedFigureId()
        );

        return endpointData;
    }
}
