package ntut.csie.team5.usecase.figure.line;

import ntut.csie.team5.entity.model.figure.line.Endpoint;

public class ConvertEndpointToDto {

    public static EndpointDto transform(Endpoint endpoint) {
        EndpointDto endpointDto = new EndpointDto();
        endpointDto.setEndpointId(endpoint.getId());
        endpointDto.setPositionX(endpoint.getPositionX());
        endpointDto.setPositionY(endpoint.getPositionY());
        endpointDto.setConnectedFigureId(endpoint.getConnectedFigureId());
        return endpointDto;
    }
}
