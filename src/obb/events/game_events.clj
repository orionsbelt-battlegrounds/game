(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.game-events
  "Manages, subscribes and publishes game related events"
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]))

(defprotocol GameEvents
  (publish [this game-info])
  (subscribe-game [this game-info subscriber-ch])
  (subscribe-all [this subscriber-ch]))

(defrecord GameEventSystem [master-ch game-publication multiplier]

  component/Lifecycle

  (start [component]
    (let [master-ch (async/chan)
          multiplier (async/mult master-ch)
          game-publisher-ch (async/chan)]
      (async/tap multiplier game-publisher-ch)
      (assoc component :master-ch master-ch
                       :game-publication (async/pub game-publisher-ch :game-id)
                       :multiplier multiplier)))

  (stop [component]
    (async/close! master-ch)
    (-> component
        (dissoc :game-publication)
        (dissoc :multiplier)))

  GameEvents

  (publish [this game-info]
    (async/go
      (async/>! master-ch
                {:game-id (game-info/id game-info)
                 :data game-info})))

  (subscribe-game [this game-info subscriber-ch]
    (async/sub game-publication
               (game-info/id game-info)
               subscriber-ch))

  (subscribe-all [this subscriber-ch]
    (async/tap multiplier subscriber-ch)))

(defn create
  "Creates a new component for game events"
  []
  (map->GameEventSystem {}))
