package ntut.csie.selab.adapter.websocket;

import org.json.JSONException;
import org.json.JSONObject;

class Cursor {
    public String userName;
    public int x;
    public int y;

    public Cursor(String userName, int x, int y) {
        this.userName = userName;
        this.x = x;
        this.y = y;
    }

    public String toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", userName);
        jsonObject.append("x", x);
        jsonObject.append("y", y);
        return jsonObject.toString();
    }
}
