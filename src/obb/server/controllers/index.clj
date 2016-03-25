(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.controllers.index
  "Controller to handle the root/index request to the server"
  (:require [obb.server.controllers.reply :as reply]
            [result.core :as result]))

(defn handler
  "Returns a intro to the game"
  [request]
  (-> {:name "OBB Game"
       :version (System/getProperty "obb-game.version")}
      result/success
      reply/ok))
