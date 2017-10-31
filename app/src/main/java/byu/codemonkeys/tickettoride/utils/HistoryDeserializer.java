package byu.codemonkeys.tickettoride.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.commands.SendMessageCommand;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.CommandType;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;

public class HistoryDeserializer {
    private static final RuntimeTypeAdapterFactory<CommandData> typeFactory = RuntimeTypeAdapterFactory
            .of(CommandData.class, "commandType")
            .registerSubtype(SendMessageCommand.class, CommandType.SEND_MESSAGE);

    private static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(typeFactory).create();

    public static List<CommandData> deserialize(String json) {
        Type listType = new TypeToken<List<CommandData>>(){}.getType();

        return gson.fromJson(json, listType);
    }

    public static List<CommandData> deserialize(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Type listType = new TypeToken<List<CommandData>>(){}.getType();

        return gson.fromJson(reader, listType);
    }

    public static HistoryResult deserializeHistoryResult(String json) {
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json);
        List<CommandData> history = new ArrayList<>();
        if (!obj.get("successful").getAsBoolean()) {
            return new HistoryResult(obj.get("errorMessage").getAsString());
        }
        if (obj.has("history")) {
            history = HistoryDeserializer.deserialize(obj.get("history").toString());
        }

        return new HistoryResult(history);
    }
}
