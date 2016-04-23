(ns obb.events.lobby-events-test
  (:use clojure.test)
  (:require [clojure.core.async :as async]
            [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]
            [obb.events.lobby-events :as lobby-events]))

(defn lobby-events-system
  "Buils a specific test system with lobby events and it's dependencies"
  []
  (component/system-map
    :lobby-events (lobby-events/create)))

(deftest create-start-stop-component
  (testing "creates a ready to use component"
    (is (-> (lobby-events-system)
            (component/start)
            (component/stop)))))

(deftest lobby-events-test
  (let [system (-> (lobby-events-system) component/start)
        lobby-events (:lobby-events system)
        game-info (game-info/create)
        subscriber1 (async/chan)
        subscriber2 (async/chan)
        ]

    (lobby-events/subscribe lobby-events subscriber1)
    (lobby-events/subscribe lobby-events subscriber2)

    (testing "adding a game"
      (lobby-events/add-game lobby-events game-info)
      (let [expected-message {:action :added :data game-info}]
        (is (= expected-message (async/<!! subscriber1)))
        (is (= expected-message (async/<!! subscriber2)))))

    (testing "removing a game"
      (lobby-events/remove-game lobby-events game-info)
      (let [expected-message {:action :removed :data game-info}]
        (is (= expected-message (async/<!! subscriber1)))
        (is (= expected-message (async/<!! subscriber2)))))

    (component/stop system)))
