# Orion's Belt BattleGrounds
[![Build Status](https://travis-ci.org/orionsbelt-battlegrounds/game.svg?branch=master)](https://travis-ci.org/orionsbelt-battlegrounds/game)

### Running

You need to install [lein](http://leiningen.org/).

* `lein server` - starts the server
* `lein frontend` - starts the frontend, in development mode

After both processes started, go to [http://localhost:3450/dev.html](http://localhost:3450/dev.html).

### Tests

* `lein test` - runs the full test suite
* `lein autotest` - listen for file changes and is always running tests
