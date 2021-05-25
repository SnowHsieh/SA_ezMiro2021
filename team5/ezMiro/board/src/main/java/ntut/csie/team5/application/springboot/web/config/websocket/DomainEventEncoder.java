package ntut.csie.team5.application.springboot.web.config.websocket;

import com.google.gson.Gson;
import ntut.csie.sslab.ddd.model.DomainEvent;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DomainEventEncoder implements Encoder.Text<DomainEvent> {

    @Override
    public String encode(DomainEvent domainEvent) throws EncodeException {
        // TODO: refactor
//        try {
//            String eventType = domainEvent.getClass().getSimpleName();
//            ObjectMapper objectMapper = new ObjectMapper();
//            String json = objectMapper.writeValueAsString(domainEvent);
//            json = json.substring(0, json.length() - 1) + "\"type\":\"" + eventType + "\"";
//            return json;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
        String event = domainEvent.getClass().getSimpleName();
        String obstr = new Gson().toJson(domainEvent);
        obstr = obstr.substring(0, obstr.length()-1) + ",\"type\":\"" + event + "\"}";
        return obstr;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}
