(ns obb.server.controllers.index-test
  (:use clojure.test)
  (:require [obb.server.controllers.mock-request :as mock-request]
            [result.core :as result]))

(deftest basic-test
  (testing "Index returns OK"
    (let [response (mock-request/http-get "/")
          body (:body response)]
      (is (= 200 (:status response)))
      (is (result/succeeded? body)))))
