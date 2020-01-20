import configuration.CommandParser;
import configuration.Config;

public class Main {

    public static void main(String[] args) {
        //todo parse command args
        Config config = new CommandParser().parseCommandArguments(args);

        //todo read file
        //todo parse file
        //todo filters
        //todo save output file || (send to server -> receive result -> save output file)
    }
}
