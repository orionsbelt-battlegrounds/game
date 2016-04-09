(ns obb.events.game-events-test
  (:use clojure.test)
  (:require [com.stuartsierra.component :as component]
            [obb.models.game-info :as game-info]
            [obb.events.game-events :as game-events]
            [obb.events.impl.async-pub-sub :as async-pub-sub]))

(def game-events-system
  "Buils a specific test system with game events and it's dependencies"
  (component/system-map
    :pub-sub-impl (async-pub-sub/create)
    :game-events (game-events/create)))

(deftest create-start-stop-component
  (testing "creates a ready to use component"
    (is (-> game-events-system
            (component/start)
            (component/stop)))))
