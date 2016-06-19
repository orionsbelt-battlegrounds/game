(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.system
  "The system that has all the server's components"
  (:require [com.stuartsierra.component :as component]
            [com.walmartlabs.system-viz :as system-viz]
            [obb.server.http-component :as http-component]
            [obb.server.web-app-component :as web-app-component]
            [obb.events.lobby-events :as lobby-events]
            [obb.events.game-events :as game-events]
            [obb.repositories.db-component :as db-component]))

(defn create
  "Creates a new system to execute the app"
  []
  (component/system-map
   :http-server (http-component/create)
   :web-app (web-app-component/create)
   :game-events (game-events/create)
   :lobby-events (lobby-events/create)
   :db (db-component/create (get (System/getenv)
                                 "DATOMIC_URI"))))

(defn -main
  "Runs a viz of the system"
  [& args]
  (system-viz/visualize-system (create)))
