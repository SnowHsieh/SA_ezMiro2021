package ntut.csie.team5.usecase.figure.line;

import ntut.csie.team5.usecase.figure.FigureDto;

public class LineDto extends FigureDto {

    private EndpointDto endpointA;
    private EndpointDto endpointB;

    public EndpointDto getEndpointA() {
        return endpointA;
    }

    public void setEndpointA(EndpointDto endpointA) {
        this.endpointA = endpointA;
    }

    public EndpointDto getEndpointB() {
        return endpointB;
    }

    public void setEndpointB(EndpointDto endpointB) {
        this.endpointB = endpointB;
    }
}
