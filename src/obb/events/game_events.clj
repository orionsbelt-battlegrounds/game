(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.game-events
  "Manages, subscribes and publishes game related events"
  (:require [com.stuartsierra.component :as component]
            [obb.events.pub-sub :as pub-sub]))

(defprotocol GameEvents
  (publish [this event-name game-info])
  (subscribe [this event-name subscriber-ch])
  (subscribe-all [this subscriber-ch]))

(defrecord GameEventSystem [pub-sub-impl]

  component/Lifecycle

  (start [component] component)
  (stop [component] component)

  GameEvents

  (publish [this event-name game-info]
    (pub-sub/publish pub-sub-impl event-name game-info))

  (subscribe [this event-name subscriber-ch]
    (pub-sub/subscribe pub-sub-impl event-name subscriber-ch))

  (subscribe-all [this subscriber-ch]
    (pub-sub/subscribe-all pub-sub-impl subscriber-ch)))

(defn create
  "Creates a new component for game events"
  []
  (component/using (map->GameEventSystem {}) [:pub-sub-impl]))

