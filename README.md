# Overview

TinyLands Alpha is a turn-based strategy game.
+ **The objective of the player is to estabilish a city with 100 population.**
+ The game starts with a 100x100 land (only grass) and the player starts with a town center.
+ At each turn the player can make one building.
+ Player manages three resources: wood, food, stone.
+ Player starts with 5 wood, 10 food, 0 stone.
+ Buildings are freely placed, can not shock with objects already in the map (other buildings and trees)

## Buildings

| Name        | Wood to build | Stone to build | Resource given            | Maintenance | Number of Jobs | Info             | 
|-------------|---------------|----------------|---------------------------|-------------|----------------|------------------|
| Town center | 0             | 0              | 0                         | 0           | 0              | Can only have 1. |
| House       | 1             | 0              | Population+3 (WHEN BUILT) | Food-1      | 0              |                  |
| Farm        | 2             | 0              | Food+5  /turn             | Wood-1      | 1              |                  |
| Woodcamper  | 0             | 0              | Wood+5  /turn             | 0           | 1              |                  |
| Mine        | 2             | 0              | Stone+2 /turn             | Wood-2      | 2              |                  |

## TODO

+ [X] Screen shows the terrain (grass) and a Town Center in the middle of the screen.
+ [X] HUD with buttons of buildings and button of end turn.
+ [X] HUD with Player resources;
+ [X] User can click button of building, then mouse will turn into the building and if he can't place it will show red. When he clicks, if is red won't do anything. Otherwise, place building in location. Before placing check if there are enough resources to build.
+ ~~[ ] Woodcamper can only be placed near tree. Mine can only be placed near stones.~~
+ [X] When user clicks to end turn, resources are updated according the city buildings production/consumption.
+ [X] When player reaches 20 population, show message win.
+ [X] When all resources are 0, show game over.


