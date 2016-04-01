(ns obb.server.handlers.test-request
  (:use clojure.test)
  (:require [obb.server.core :as core]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defn parsed-response
  "Gets the response for a HTTP request"
  [method path]
  (let [response (core/app (mock/request method path))]
    (assoc response :body (edn/read-string (:body response)))))
