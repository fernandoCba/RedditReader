package ar.edu.unc.famaf.redditreader.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import ar.edu.unc.famaf.redditreader.model.Listing;
import ar.edu.unc.famaf.redditreader.model.PostModel;


public class Parser {

    public Listing readJsonStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.registerTypeAdapter(Listing.class, new PostModelDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(sb.toString(), Listing.class);
    }

    private class PostModelDeserializer implements JsonDeserializer<Listing> {
        public Listing deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            if (!json.getAsJsonObject().get("kind").getAsString().equalsIgnoreCase("Listing"))
                return null;
            Listing listing = new Listing();

            JsonObject data = json.getAsJsonObject().get("data").getAsJsonObject();

            JsonElement before = data.get("before");
            if(!before.isJsonNull())
                listing.setBefore(before.getAsString());

            JsonElement after = data.get("after");
            if(!after.isJsonNull())
                listing.setAfter(after.getAsString());

            JsonArray children = data.getAsJsonObject().get("children").getAsJsonArray();
            for (int i = 0; i < children.size(); i++) {
                JsonObject childrenArrayElement = children.get(i).getAsJsonObject();
                JsonObject childrenData = childrenArrayElement.get("data").getAsJsonObject();
                listing.getPosts().add(parsePost(childrenData));
            }
            return listing;
        }

        private PostModel parsePost(JsonObject postJson) {
            PostModel p = new PostModel();
            p.setAuthor(postJson.get("author").getAsString());
            p.setCreatedOn(postJson.get("created").getAsString());
            p.setTitle(postJson.get("title").getAsString());
            p.setComments(postJson.get("num_comments").getAsInt());
            p.setImageUrl(postJson.get("thumbnail").getAsString());
            return p;
        }
    }
}