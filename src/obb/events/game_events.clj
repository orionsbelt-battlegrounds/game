(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.game-events
  "Manages, subscribes and publishes game related events"
  (:require [com.stuartsierra.component :as component]))

(defprotocol GameEvents
  (publish [this event-name game-info])
  (subscribe [this event-name])
  (subscribe-all [this]))

(defrecord GameEventSystem [pub-sub-impl]

  component/Lifecycle

  (start [component] component)
  (stop [component] component)

  GameEvents

  (publish [this event-name game-info])
  (subscribe [this event-name])
  (subscribe-all [this]))

(defn create
  "Creates a new component for game events"
  []
  (component/using (map->GameEventSystem {}) [:pub-sub-impl]))

