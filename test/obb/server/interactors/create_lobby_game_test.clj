(ns obb.server.interactors.create-lobby-game-test
  (:use clojure.test)
  (:require [clojure.core.async :as async]
            [obb.server.test-system :as system]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]
            [obb.server.interactors.create-lobby-game :as create-lobby-game]
            [obb.events.game-events :as game-events]
            [obb.events.lobby-events :as lobby-events]
            [result.core :as result]))

(deftest create-lobby-game-event
  (let [system (component/start (system/create))
        {:keys [game-events lobby-events]} system
        player-id (gensym)
        game-ch (async/chan)
        lobby-ch (async/chan)]

    (testing "subscribe to expected events"
      (game-events/subscribe-all game-events game-ch)
      (lobby-events/subscribe lobby-events lobby-ch))

    (testing "creating new game"
      (let [result (async/<!! (create-lobby-game/run! system {:player-id player-id}))
            game-info (:game-info result)]
        (is (result/succeeded? result))
        (is game-info)
        (is (= player-id (game-info/p1 game-info)))

        (testing "subsctibers should be notified"
          (is (= {:game-info game-info}
                 (async/<!! game-ch)))
          (is (= {:game-info game-info :meta {:action :add-game}}
                 (async/<!! lobby-ch))))))

    (component/stop system)))
