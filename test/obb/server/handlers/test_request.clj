(ns obb.server.handlers.test-request
  (:use clojure.test)
  (:require [obb.server.http-component :as http-component]
            [com.stuartsierra.component :as component]
            [obb.server.test-system :as system]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defn parsed-response
  "Gets the response for a HTTP request"
  [method path]
  (let [system (component/start (system/create))
        response ((http-component/app system) (mock/request method path))]
    (component/stop system)
    (assoc response :body (edn/read-string (:body response)))))
