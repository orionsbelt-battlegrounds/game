(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.impl.async-pub-sub
  "Provides a pub/sub implementation on top of core.async"
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.events.pub-sub :as pub-sub]))

(defrecord AsyncPubSub [master-ch publication broadcast]

  component/Lifecycle

  (start [component]
    (let [master-ch (async/chan)
          broadcast (async/mult master-ch)
          publisher-ch (async/chan)]
      (async/tap broadcast publisher-ch)
      (assoc component :master-ch master-ch
                       :publication (async/pub publisher-ch :event-name)
                       :broadcast broadcast)))

  (stop [component]
    (async/close! master-ch)
    (-> component
        (dissoc :publication)
        (dissoc :broadcast)))

  pub-sub/PubSub

  (publish [this event-name event-data]
    (async/go
      (async/>! master-ch
                {:event-name event-name :data event-data})))

  (subscribe [this event-name subscriber-ch]
    (async/sub publication
               event-name
               subscriber-ch))

  (subscribe-all [this subscriber-ch]
    (async/tap broadcast subscriber-ch)))

(defn create
  "Creates a new component for pub sub based on core.async"
  []
  (map->AsyncPubSub {}))
