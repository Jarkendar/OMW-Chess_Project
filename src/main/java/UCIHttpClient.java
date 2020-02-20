import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

public class UCIHttpClient {

    private JSONObject config;
    private HttpClient httpClient;
    private String token;
    private JSONParser parser = new JSONParser();
    private String engine;
    private WebSocket socket;
    private WebSocketClient socketListener;

    public UCIHttpClient(){
        URL resource = UCIHttpClient.class.getResource("server_config.json");
        try {
            this.config = loadConfig(Paths.get(resource.toURI()).toString());
            httpClient = HttpClient.newBuilder().build();
            login();
            getAvailableEngines();
            startEngine();
            initializeSocket(4);
            //stopEngine();
        }catch(URISyntaxException e){
            e.printStackTrace();
        }
    }

    private JSONObject getConfig(){
        return this.config;
    }
    private String getUser(){
        return this.config.get("login").toString();
    }

    private String getPassword(){
        return this.config.get("password").toString();
    }

    private String getUrl(){
        return this.config.get("uci_server_url").toString();
    }

    private String getWstUrl(){
        return this.config.get("uci_server_socket_url").toString();
    }

    private String getEngine(){
        return this.engine;
    }

    private JSONObject loadConfig(String configPath){
        JSONObject configObject = new JSONObject();
        try{
            configObject = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(configPath)));
        }catch(ParseException | IOException e){
            e.printStackTrace();
        }
        return configObject;
    }

    private void login(){
        JSONObject json = new JSONObject();
        json.put("login", getUser());
        json.put("password", getPassword());
        String data = json.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .uri(URI.create(getUrl() + "user/login"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode()==200){
                System.out.println("Logged succesfully");
                JSONObject loginResponse = (JSONObject) parser.parse(response.body());
                this.token = loginResponse.get("token").toString();
            }
        }catch(IOException | InterruptedException | ParseException e){
            System.out.println("Login failed");
            e.printStackTrace();
        }
    }

    private void getAvailableEngines(){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(getUrl()+"engine/available"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Authorization", "Bearer " + this.token)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject enginesResponse = (JSONObject) parser.parse(response.body());
            if (response.statusCode()==200) {
                JSONArray engines = (JSONArray) enginesResponse.get("info");
                JSONObject firstEngine = (JSONObject) engines.get(0);
                this.engine = firstEngine.get("name").toString();
            }
            else{
                System.out.println("Problem with JSON occured");
            }
        }catch(IOException | InterruptedException | ParseException e){
            e.printStackTrace();
        }
    }

    public void startEngine(){
        JSONObject json = new JSONObject();
        json.put("engine", getEngine());
        String jsonData = json.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .uri(URI.create(getUrl() + "engine/start"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.token)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode()==200){
                System.out.println("Started " + this.engine);
                JSONObject loginResponse = (JSONObject) parser.parse(response.body());
            }
        }catch(IOException | InterruptedException | ParseException e){
            System.out.println("Engine start failed");
            e.printStackTrace();
        }
    }

    public void stopEngine() {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("null"))
                .uri(URI.create(getUrl() + "engine/stop"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + this.token)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Stopped " + this.engine);
                JSONObject loginResponse = (JSONObject) parser.parse(response.body());
            }
        } catch (IOException | InterruptedException | ParseException e) {
            System.out.println("Engine stop failed");
            e.printStackTrace();
        }
    }

    private static class WebSocketClient implements WebSocket.Listener {
        List<String> msgs = new ArrayList<>();
        private Boolean flag = false;

        public WebSocketClient() {}

        @Override
        public void onOpen(WebSocket webSocket) {
            System.out.println("WebSocket opened");
            msgs = new ArrayList<>();
            WebSocket.Listener.super.onOpen(webSocket);
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence message, boolean last) {
            msgs.add(message.toString());
            String msg = message.toString();
            if (msg.contains("bestmove")) {
                flag = true;
            }
            return WebSocket.Listener.super.onText(webSocket, message, last);
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            System.out.println("Error concerning socket occured" + webSocket.toString());
            WebSocket.Listener.super.onError(webSocket, error);
        }

        public Boolean getFlag() {
            return flag;
        }

        public List<String> getResponse() {
            return msgs;
        }
    }

    private void initializeSocket(Integer cores){
        WebSocketClient wsListener = new WebSocketClient();
        try {
            WebSocket ws = httpClient.newWebSocketBuilder()
                    .header("Authorization", "Bearer " + this.token)
                    .buildAsync(URI.create(getWstUrl()), wsListener)
                    .join();
            this.socket = ws;
        }catch(CompletionException e){
            System.out.println("Connection to the websocket failed");
        }

        this.socketListener = wsListener;
        socket.sendText("setoption name Threads value " + cores.toString(), true);
    }

    public Integer rateGame(String fen, Integer depth){
        fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        socket.sendText("ucinewgame", true);
        socket.sendText("position fen " + fen, true);
        socket.sendText("go depth " + depth.toString(), true);

        while(!socketListener.getFlag()){
            try {
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        List<String> response = socketListener.getResponse();
        Integer value = processResponse(response);
        //socket.sendText("stop", true);
        return value;

    }

    private Integer processResponse(List<String> response){
        ArrayList<String> depths = new ArrayList<String>();
        ArrayList<String> cps = new ArrayList<String>();
        ArrayList<String> pvs = new ArrayList<String>();
        for (String line: response){
            if (line.contains("info depth") && line.contains("multipv")){
                List<String> tokens = Arrays.asList(line.split(" "));
                int id = tokens.indexOf("depth");
                String d = tokens.get(id+1);
                depths.add(d);
                int ic = tokens.indexOf("cp");
                String c = tokens.get(ic+1);
                cps.add(c);
                int ip = tokens.indexOf("pv");
                String p = tokens.get(ip+1);
                pvs.add(p);
            }
        }
        return Integer.parseInt(cps.get(cps.size()-1));
    }




}