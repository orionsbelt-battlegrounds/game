(ns obb.models.game-info-test
  (:use clojure.test)
  (:require [obb.models.game-info :as game-info]))

(deftest create
  (testing "creates an empty game info"
    (is (game-info/create))))
