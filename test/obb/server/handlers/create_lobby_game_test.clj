(ns obb.server.handlers.create-lobby-game-test
  (:use clojure.test)
  (:require [obb.server.handlers.test-request :as test-request]
            [result.core :as result]))

(deftest basic-test
  (testing "create lobby game returns OK"
    (let [data {:player-id "player-id"}
          response (test-request/parsed-response :post "/lobby/create-game" data)
          body (:body response)]
      (is (= 200 (:status response)))
      (is (result/succeeded? body))
      (is (= (:player-id data) (get-in body [:game-info :p1]))))))
