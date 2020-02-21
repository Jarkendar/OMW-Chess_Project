import chess.parser.Entity;
import chess.parser.Move;
import filters.FilterResult;
import filters.RatedEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ServerConnector {

    private int maxDepth;
    UCIHttpClient uciClient;

    public ServerConnector(String pathToServerConfigFile, int maxDepth) {
        this.maxDepth = maxDepth;
        uciClient = new UCIHttpClient(pathToServerConfigFile);
    }

    public LinkedList<RatedGame> ratedGames(List<FilterResult> filterResults){
        LinkedList<RatedGame> ratedGames = new LinkedList<>();
        for (FilterResult result: filterResults) {
            ratedGames.addLast(new RatedGame(result.getPgnGame(), ratePotentialMoves(result.getPotentialMoves())));
        }
        return ratedGames;
    }

    private LinkedList<RatedEntity> ratePotentialMoves(LinkedList<Entity> potentialMoves){
        LinkedList<RatedEntity> ratedEntities = new LinkedList<RatedEntity>();
        for (int i = 0; i < potentialMoves.size()-1; i++){
            Entity entity = potentialMoves.get(i);
            if (entity instanceof Move) {
                RatedEntity re = new RatedEntity((Move) entity, uciClient.rateGame(((Move) entity).getBoardAfter(), maxDepth, i), ((Move) entity).getBoardAfter());
                ratedEntities.add(re);
            }
        }

        return ratedEntities;
    }

    public void closeConnection(){
        uciClient.stopSocket();
        uciClient.stopEngine();
    }
}
