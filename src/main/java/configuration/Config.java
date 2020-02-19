package configuration;

import java.util.Arrays;

public class Config {

    private Header header = Header.MINIMAL;
    private int minCentiPawns = 50;
    private int minEngineSearch = 30;
    private int minAcceptableValue = 2;
    private String pathToServerConfig = "uciServer.json";
    private int[] filtersNumber = new int[]{1,2,3,4,5,6};//default all
    private String inputPath;
    private String outputPath;

    public Config(Header header, int minCentiPawns, int minEngineSearch, int minAcceptableValue, String pathToServerConfig, int[] filtersNumber, String inputPath, String outputPath) {
        this.header = header;
        this.minCentiPawns = minCentiPawns;
        this.minEngineSearch = minEngineSearch;
        this.minAcceptableValue = minAcceptableValue;
        this.filtersNumber = filtersNumber;
        this.pathToServerConfig = pathToServerConfig;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public Config(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    @Override
    public String toString() {
        return "configuration.Config{" +
                "header=" + header +
                ", minCentiPawns=" + minCentiPawns +
                ", minEngineSearch=" + minEngineSearch +
                ", minAcceptableValue=" + minAcceptableValue +
                ", pathToServerConfig='" + pathToServerConfig + '\'' +
                ", filtersNumber=" + Arrays.toString(filtersNumber) +
                ", inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                '}';
    }

    //GETTERS and SETTERS

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public int getMinCentiPawns() {
        return minCentiPawns;
    }

    public void setMinCentiPawns(int minCentiPawns) {
        this.minCentiPawns = minCentiPawns;
    }

    public int getMinEngineSearch() {
        return minEngineSearch;
    }

    public void setMinEngineSearch(int minEngineSearch) {
        this.minEngineSearch = minEngineSearch;
    }

    public int getMinAcceptableValue() {
        return minAcceptableValue;
    }

    public void setMinAcceptableValue(int minAcceptableValue) {
        this.minAcceptableValue = minAcceptableValue;
    }

    public String getPathToServerConfig() {
        return pathToServerConfig;
    }

    public void setPathToServerConfig(String pathToServerConfig) {
        this.pathToServerConfig = pathToServerConfig;
    }

    public int[] getFiltersNumber() {
        return filtersNumber;
    }

    public void setFiltersNumber(int[] filtersNumber) {
        this.filtersNumber = filtersNumber;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
