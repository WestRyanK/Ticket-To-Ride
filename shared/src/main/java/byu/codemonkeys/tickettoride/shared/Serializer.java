package byu.codemonkeys.tickettoride.shared;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import byu.codemonkeys.tickettoride.shared.commands.CommandData;

public class Serializer {
    private Gson gson;

    public Serializer() {
        gson = new Gson();
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public <T> T deserialize(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public <T> T deserialize(InputStream stream, Class<T> classOfT) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return gson.fromJson(reader, classOfT);
    }
}
