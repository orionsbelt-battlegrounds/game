(ns ^{:added "0.1.0" :author "Joaquim Torres"}
    obb.repositories.db-component
  "The Datomic database component"
  (:require [com.stuartsierra.component :as component]
            [datomic.api :as datomic]
            [obb.repositories.db-repository :as db-repository]))

(defrecord DbComponent [uri conn]
  component/Lifecycle

  (start [component]
    (db-repository/create uri)
    (assoc component :conn (datomic/connect uri)))

  (stop [component]
    (when conn (datomic/release conn))
    (assoc component :conn nil)))

(defn create
  "Create a new DbComponent"
  [uri]
  (map->DbComponent {:uri uri}))
