(ns obb.server.controllers.index-test
  (:use clojure.test)
  (:require [obb.server.controllers.index :as index]
            [obb.server.core :as core]
            [ring.mock.request :as mock]))

(deftest basic-test
  (testing "Index returns OK"
    (let [response (core/app (mock/request :get "/"))]
      (is (= 200 (:status response))))))
