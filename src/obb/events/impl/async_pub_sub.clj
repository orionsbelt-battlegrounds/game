(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.impl.async-pub-sub
  "Provides a pub/sub implementation on top of core.async"
  (:require [com.stuartsierra.component :as component]))

(defrecord AsyncPubSub []

  component/Lifecycle

  (start [component]
    component)

  (stop [component]
    component))

(defn create
  "Creates a new component for pub sub based on core.async"
  []
  (map->AsyncPubSub {}))
