(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.events.pub-sub
  "Interface for a pub sub system")

(defprotocol PubSub
  (publish [this event-name event-data])
  (subscribe [this event-name subscriber-ch])
  (subscribe-all [this subscriber-ch]))

