package configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CommandParser {

    public Config parseCommandArguments(String[] arguments){
        return updateConfig(new Config(arguments[arguments.length-2], arguments[arguments.length-1]), createMap(arguments));
    }

    private HashMap<String, LinkedList<String>> createMap(String[] arguments){
        HashMap<String, LinkedList<String>> map = new HashMap<>();
        String currentSwitch = "";
        for (int i = 0; i < arguments.length-2; i++){
            if (arguments[i].contains("-")){
                currentSwitch = arguments[i];
                map.put(arguments[i], new LinkedList<>());
            } else {
                map.get(currentSwitch).addLast(arguments[i]);
            }
        }
        return map;
    }

    private Config updateConfig(Config config, HashMap<String, LinkedList<String>> map){
        for (Map.Entry<String, LinkedList<String>> entry: map.entrySet()){
            switch (entry.getKey()){
                case "-h":
                    config.setHeader(Header.valueOf(entry.getValue().getFirst().toUpperCase()));
                    break;
                case "-cp":
                    config.setMinCentiPawns(Integer.parseInt(entry.getValue().getFirst()));
                    break;
                case "-d":
                    config.setMinEngineSearch(Integer.parseInt(entry.getValue().getFirst()));
                    break;
                case "-n":
                    config.setMinAcceptableValue(Integer.parseInt(entry.getValue().getFirst()));
                    break;
                case "-e":
                    config.setPathToServerConfig(String.join("",entry.getValue()));
                    break;
                case "-f":
                    config.setFiltersNumber(
                            Arrays.stream(String.join("", entry.getValue()).trim().split(","))
                                    .map(Integer::parseInt)
                                    .distinct()
                                    .mapToInt(i -> i)
                                    .toArray());
                    break;
            }
        }
        return config;
    }

}
