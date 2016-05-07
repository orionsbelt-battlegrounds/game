(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.core
  "This namespace has the main fn that starts the game server, and all
  the necessary config, such as routing and system/context build up."
  (:gen-class)
  (:require
    [com.stuartsierra.component :as component]
    [clojure.core.async :as async]
    [obb.server.system :as system]))

(defn- on-shutdown
  "Runs shutdown hooks"
  [system]
  (println "Shutdown system...")
  (component/stop system))

(defn -main [& args]
  (let [system (component/start (system/create))]
    (.addShutdownHook (Runtime/getRuntime) (Thread. (partial on-shutdown system)))
    (async/<!! (-> system :http-server :closed-ch))))
