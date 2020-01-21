import chess.parser.Entity;
import filters.FilterResult;
import filters.RatedEntity;

import java.util.LinkedList;
import java.util.List;

public class ServerConnector {

    private int maxDepth;

    public ServerConnector(String pathToServerConfigFile, int maxDepth) {
        this.maxDepth = maxDepth;
        parseServerConfig(pathToServerConfigFile);
        //maybe in constructor prepare to connect
    }

    private void parseServerConfig(String path){
        //todo to something
    }

    public List<RatedGame> ratedGames(List<FilterResult> filterResults){
        LinkedList<RatedGame> ratedGames = new LinkedList<>();
        for (FilterResult result: filterResults) {
            ratedGames.addLast(new RatedGame(result.getPgnGame(), ratePotentialMoves(result.getPotentialMoves())));
        }
        return ratedGames;
    }


    private LinkedList<RatedEntity> ratePotentialMoves(LinkedList<Entity> potentialMoves){
        //rate every move on server?
        for (Entity entity: potentialMoves){
            //connect to server, rate to maxDepth
        }
        return null;
    }
}
