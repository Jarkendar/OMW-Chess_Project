# OMW-Chess_Project

## Main idea

The program is capable of analyzing, with a help of a chess engine, a series of chess games from a chess database written to a PGN-compatible file, looking for strong, non-trivial moves. For each game, it checks all positions occurring on the main line (skipping variations), to see if they meet certain criteria (filters). For each position where the best move suggested by the engine matches all the criteria, the program puts in the output PGN file a new chess game, containing at least a FEN header, defining starting position, and (at least) two best moves as indicated by the engine. The user should be able to configure search criteria, chess engine options, and format of the output PGN file.

## Run

java jar <jarName> -h <> -cp <> -d <> -n <> -e \<uci-server-configuration-file-path> \<input-PGN-file-path> \<output-PGN-file-path>

-h: what headers should be put to output PGN file (all, concise, minimal); default: minimal; meaning:
-cp: min. required cp (centipawns) difference between best and second best move shown by the engine; default: 50
-d: min engine search depth for best and second best move shown by the engine (in multivariation mode); default: 30
-n: number of variations in multi-variation mode; default: 2 (minimal acceptable value)
-e: path to UCI Server configuration file (relative or absolute); default: uciServer.json
Paths to input/output PGN files may be relative or absolute.
