(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.handlers.create-lobby-game
  "Create lobby game handlers"
  (:require [clanhr.reply.core :as reply]
            [obb.server.interactors.create-lobby-game :as create-lobby-game]
            [result.core :as result]))

(defn handle
  "Creates a new game"
  [request]
  (let [system (:system request)
        body (read-string (slurp (:body request)))]
    (reply/async-result
      (create-lobby-game/run! system body))))
