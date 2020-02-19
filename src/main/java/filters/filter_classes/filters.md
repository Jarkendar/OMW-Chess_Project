# Filters doc

Add -f \<filter numbers separate comma\> before paths in command to select specific filters. Default all filters are include. Results is intersection results of all include filters.

#### Abstract classes
* **Filter** - Abstract class filter with methods to inherit.

#### Filters
The number is the abbreviation for the filter in the command 
* 1 - **NoRecapture** - The filter looks for moves in which player don't kill enemy after loss figure. Don't recaptures field.
* 2 - **FigureBeat** - The filter looks for all beats.
* 3 - **QueenBeat** - The filter looks for all queen beats.
* 4 - **QueenSacrifice** - The filter looking for all queen sacrifice. Queen sacrifice is all lost queen, doesn't matter queen beats or no beats.
* 5 - **RookFilter** - The filter looks for all rooks.
* 6 - **Sacrifice** - The filter looks for moves where figure with greater power is beats by figure (or pawn) with smaller power.
