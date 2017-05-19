package ch.brj.jms;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jakob on 19.05.2017.
 */
public class JMSMessage {
    private String id;
    private String text;

    private Map<String, Object> properties = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "JMSMessage{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", properties=" + properties +
                '}';
    }
}
