(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.web-app-component
  "Gathers all deps required by the web app component"
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [taoensso.timbre :as logger]
            [ring.middleware.cors :as cors]
            [aleph.http :as http]
            [compojure.core :as compojure]
            [obb.server.routes :as routes]))

(defrecord WebAppComponent [lobby-events game-events])

(defn create
  "Creates the WebAppComponent"
  []
  (component/using (map->WebAppComponent {})
                   [:lobby-events :game-events]))
