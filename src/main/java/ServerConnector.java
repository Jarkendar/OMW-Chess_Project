import chess.parser.Entity;
import chess.parser.Move;
import filters.FilterResult;
import filters.RatedEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
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
        for (int i = 0; i < potentialMoves.size(); i++){
            Entity entity = potentialMoves.get(i);
            if (entity instanceof Move) {

                ArrayList<ArrayList<String>> resultVar = uciClient.rateGame(((Move) entity).getBoardBefore(), maxDepth, 2*i);
                ArrayList<ArrayList<String>> result = uciClient.rateGame(((Move) entity).getBoardAfter(), maxDepth, (2*i)+1);
                Integer resCp = Integer.parseInt(result.get(1).get(0));
				ArrayList<String> pvs = resultVar.get(0);
				ArrayList<String> cpsStr = resultVar.get(1);
				ArrayList<Integer> cps = new ArrayList<>();

				for (String str: cpsStr){
				    if (str.equals("info")){continue;}
				    cps.add(Integer.parseInt(str));
                }
				
				RatedEntity re = new RatedEntity((Move) entity, resCp, ((Move) entity).getBoardBefore());
                ArrayList<String> rePvs = new ArrayList<>();
                ArrayList<Integer> reCps = new ArrayList<>();
                for (int j = 0; j < cps.size(); j++){
                    if (! ((Move) entity).getPANRepresentation().equals(pvs.get(j))) {
                        rePvs.add(pvs.get(j));
                        reCps.add(cps.get(j));
                    }
                }
				re.setVariationsPv(rePvs);
				re.setVariationsCp(reCps);
                ratedEntities.add(re);
            }
        }

        return ratedEntities;
    }

    public void closeConnection(){
        //uciClient.stopSocket();
        uciClient.stopEngine();
    }
}
