package configuration;

public class CommandParser {

    //todo
    public Config parseCommandArguments(String[] arguments){
        return new Config(arguments[0], arguments[1]);
    }

}
