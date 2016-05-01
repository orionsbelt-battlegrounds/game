(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.system
  "The system that has all the server's components"
  (:require [com.stuartsierra.component :as component]
            [obb.server.http-component :as http-component]))

(defn create
  "Creates a new system to execute the app"
  []
  (component/system-map
    :http-server (http-component/create)))
