(ns obb.server.handlers.test-request
  (:use clojure.test)
  (:require [obb.server.http-component :as http-component]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defn parsed-response
  "Gets the response for a HTTP request"
  [method path]
  (let [response (http-component/app (mock/request method path))]
    (assoc response :body (edn/read-string (:body response)))))
