(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.test-system
  "A specific system just for tests"
  (:require [com.stuartsierra.component :as component]
            [obb.server.system :as system]
            [obb.server.web-app-component :as web-app-component]
            [obb.events.lobby-events :as lobby-events]
            [obb.events.game-events :as game-events]
            [obb.repositories.db-component :as db-component]))

(defn create
  "Creates a new system to execute the app"
  []
  (component/system-map :web-app (web-app-component/create)
                        :game-events (game-events/create)
                        :lobby-events (lobby-events/create)
                        :db (db-component/create "datomic:mem://obb-game-test")))
