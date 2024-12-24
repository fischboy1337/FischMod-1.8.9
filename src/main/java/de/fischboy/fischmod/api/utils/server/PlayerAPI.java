package de.fischboy.fischmod.api.utils.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.fischboy.fischmod.api.utils.console.ConsoleUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayerAPI {
    private static final String API_URL = "http://localhost:3000/api/players/";
    private static final String CAPE_URL = "http://localhost:3000/api/capes/";

    /* Ruft die Spielerinformationen von der API ab */
    public JsonObject getPlayerInfo(String uuid) throws Exception {
        String urlString = API_URL + uuid;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("API-Abfrage fehlgeschlagen: HTTP-Fehlercode " + responseCode);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            JsonObject responseJson = new Gson().fromJson(br, JsonObject.class);
            return responseJson;
        } catch (JsonParseException e) {
            System.out.println("Fehler beim Parsen der JSON-Antwort: " + e.getMessage());
            return null;
        } finally {
            conn.disconnect();
        }
    }

    /* Ruft die Cape-Informationen von der API ab */
    public JsonObject getCape(String capeName) throws Exception {
        String urlString = CAPE_URL + capeName;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Cape-Abfrage fehlgeschlagen: HTTP-Fehlercode " + responseCode);
            return null;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            JsonObject responseJson = new Gson().fromJson(br, JsonObject.class);
            return responseJson;
        } catch (JsonParseException e) {
            System.out.println("Fehler beim Parsen der JSON-Antwort: " + e.getMessage());
            return null;
        } finally {
            conn.disconnect();
        }
    }

    /* Fügt einen Spieler zur Datenbank hinzu */
    public boolean addPlayer(String name, String uuid, boolean isPlaying) throws Exception {
        String urlString = API_URL;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JsonObject playerData = new JsonObject();
        playerData.addProperty("name", name);
        playerData.addProperty("uuid", uuid);
        playerData.addProperty("isPlaying", isPlaying);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = playerData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        ConsoleUtil.info("Spieler hinzugefügt");
        return responseCode == HttpURLConnection.HTTP_CREATED;
    }

    /* Aktualisiert den `isPlaying`-Status eines Spielers */
    public boolean updatePlayerStatus(String uuid, boolean isPlaying) throws Exception {
        String urlString = API_URL + uuid;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PATCH");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JsonObject statusUpdate = new JsonObject();
        statusUpdate.addProperty("isPlaying", isPlaying);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = statusUpdate.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        return responseCode == HttpURLConnection.HTTP_OK;
    }
}
