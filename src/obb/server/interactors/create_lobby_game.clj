(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.interactors.create-lobby-game
  "Creates a game and registers it on the lobby"
  (:refer-clojure :exclude [run!])
  (:require [clojure.core.async :as async]
            [obb.models.game-info :as game-info]
            [obb.events.game-events :as game-events]
            [obb.events.lobby-events :as lobby-events]
            [result.core :as result]))

(defn run!
  "Creates a game and registers it on the lobby"
  [{:keys [game-events lobby-events] :as context} {:keys [player-id] :as args}]
  (async/go
    (let [game-info (-> (game-info/create)
                        (game-info/set-p1 player-id))]
      (game-events/publish game-events game-info)
      (lobby-events/add-game lobby-events game-info)
      (result/success {:game-info game-info}))))
