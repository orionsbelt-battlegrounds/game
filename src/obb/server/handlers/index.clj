(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.handlers.index
  "Controller to handle the root/index request to the server"
  (:require [clanhr.reply.core :as reply]
            [result.core :as result]))

(defn handle
  "Returns a intro to the game"
  [request]
  (-> {:name "OBB Game"
       :version (System/getProperty "obb-game.version")}
      result/success
      reply/ok))
