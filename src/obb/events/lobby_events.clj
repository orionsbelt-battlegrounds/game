(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.lobby-events
  "Manages subscribers and processes lobby events"
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]))

(defprotocol LobbyEvents
  (add-game [this game-info])
  (remove-game [this game-info])
  (subscribe [this subscriber-ch]))

(defrecord LobbyEventSystem [master-ch multiplier]

  component/Lifecycle

  (start [component]
    (let [master-ch (async/chan)
          multiplier (async/mult master-ch)]
      (assoc component :master-ch master-ch
                       :multiplier multiplier)))

  (stop [component]
    (async/close! master-ch)
    (-> component
        (dissoc :master-ch)
        (dissoc :multiplier)))

  LobbyEvents

  (add-game [this game-info]
    (async/go
      (async/>! master-ch
                {:action :added
                 :data game-info})))

  (remove-game [this game-info]
    (async/go
      (async/>! master-ch
                {:action :removed
                 :data game-info})))

  (subscribe [this subscriber-ch]
    (async/tap multiplier subscriber-ch)))

(defn create
  "Creates a new component for lobby events"
  []
  (map->LobbyEventSystem {}))
