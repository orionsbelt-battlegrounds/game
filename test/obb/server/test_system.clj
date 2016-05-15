(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.test-system
  "A specific system just for tests"
  (:require [com.stuartsierra.component :as component]
            [obb.server.system :as system]))

(defn create
  "Creates a new system to execute the app"
  []
  (dissoc (system/create) :http-server))
