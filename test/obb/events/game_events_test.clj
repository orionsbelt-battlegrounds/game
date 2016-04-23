(ns obb.events.game-events-test
  (:use clojure.test)
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]
            [obb.events.game-events :as game-events]))

(defn game-events-system
  "Buils a specific test system with game events and it's dependencies"
  []
  (component/system-map
    :game-events (game-events/create)))

(deftest create-start-stop-component
  (testing "creates a ready to use component"
    (is (-> (game-events-system)
            (component/start)
            (component/stop)))))

(deftest send-game-event
  (let [system (-> (game-events-system) component/start)
        game-events (:game-events system)
        game-info (game-info/create)
        game-ch (async/chan 1)
        subscriber-all-ch (async/chan 1)
        expected-message {:game-id (game-info/id game-info)
                          :data game-info}]

      (game-events/subscribe-game game-events game-info game-ch)
      (game-events/subscribe-all game-events subscriber-all-ch)
      (game-events/publish game-events game-info)

      (testing "and both subscribers should receive the event"
        (let [ev1 (async/<!! game-ch)
              ev2 (async/<!! subscriber-all-ch)]
          (is (= ev1 ev2))
          (is (= ev1 expected-message))))

    (component/stop system)))
