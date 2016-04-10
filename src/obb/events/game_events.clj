(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.game-events
  "Manages, subscribes and publishes game related events"
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]))

(defprotocol GameEvents
  (publish [this event-name game-info])
  (subscribe-event [this event-name subscriber-ch])
  (subscribe-game [this game-info subscriber-ch])
  (subscribe-all [this subscriber-ch]))

(defrecord GameEventSystem [master-ch event-publication game-publication broadcast]

  component/Lifecycle

  (start [component]
    (let [master-ch (async/chan)
          broadcast (async/mult master-ch)
          event-publisher-ch (async/chan)
          game-publisher-ch (async/chan)]
      (async/tap broadcast event-publisher-ch)
      (async/tap broadcast game-publisher-ch)
      (assoc component :master-ch master-ch
                       :event-publication (async/pub event-publisher-ch :event-name)
                       :game-publication (async/pub game-publisher-ch :game-id)
                       :broadcast broadcast)))

  (stop [component]
    (async/close! master-ch)
    (-> component
        (dissoc :event-publication)
        (dissoc :game-publication)
        (dissoc :broadcast)))

  GameEvents

  (publish [this event-name game-info]
    (async/go
      (async/>! master-ch
                {:event-name event-name
                 :game-id (game-info/id game-info)
                 :data game-info})))

  (subscribe-event [this event-name subscriber-ch]
    (async/sub event-publication
               event-name
               subscriber-ch))

  (subscribe-game [this game-info subscriber-ch]
    (async/sub game-publication
               (game-info/id game-info)
               subscriber-ch))

  (subscribe-all [this subscriber-ch]
    (async/tap broadcast subscriber-ch)))

(defn create
  "Creates a new component for game events"
  []
  (map->GameEventSystem {}))
