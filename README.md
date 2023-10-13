# Summary
This repository is a Scrabble clone for Android and iOS made with Compose Multiplatform. This project serves two primary purposes:
1. To act as a learning tool for me to learn about Compose Multiplatform development
2. To demonstrate the kinds of cross-platform UIs that can be built using Kotlin Multiplatform + Compose Multiplatform

# Scope
For the time being, the scope of this project is to support offline play between two players. In the future, single-player mode against an AI and/or online play against another player may be added.

Note: It is specifically not a goal to publish this app for obvious copyright reasons. This repository exists purely for learning and demonstration purposes.

# Challenges
1. A significant portion of the work to get the project in its current state was creating the `DragContext` API, a custom API for handling drag-and-drop behavior. While I could have simply embedded the drag-and-drop behavior into the Scrabble game logic, that would have too deeply coupled code that could be app-agnostic (gesture handling) and app-specific code (game logic). Instead, I opted to keep the `DragContext` API completely independent from the game itself, and it is now an [external library](https://github.com/susumunoda/compose-gestures/) that this project simply depends on.
2. Another concern is performance, specifically avoiding unnecessary recompositions of the cells in the grid. Since there are 15 * 15 = 225 cells in the grid, it is important that we minimize the number of times that the entire grid is recomposed. Even if Compose intelligently decides not to recompose a leaf composable (i.e. a cell in the grid), if we are not careful, we may still end up in situation where Compose loops through all 225 iterations to _check_ which cells needs to be recomposed, if any. See [this commit](https://github.com/susumunoda/word-game/commit/38ec6bbd9b215cd794c24329145246c20d3ba72e) for an example.

# Progress
- [X] Track and display game state (e.g. scores, available tiles, etc)
- [X] Implement drag and drop of player's tiles onto the game board
- [X] Implement shuffle and manual rearranging of player's tiles
- [ ] Validate played tiles and compute score
- [ ] Implement resign/skip/swap
- [ ] Display summary after game end

# Demos
https://github.com/susumunoda/word-game/assets/5313576/179cb508-f005-4fc6-86c1-459dcf2cfacb

https://github.com/susumunoda/word-game/assets/5313576/d27d44d1-5fc4-422e-8e6a-f24302d437dd
