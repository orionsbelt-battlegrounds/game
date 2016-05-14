(ns obb.server.handlers.test-request
  (:use clojure.test)
  (:require [obb.server.http-component :as http]
            [com.stuartsierra.component :as component]
            [obb.server.test-system :as system]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defn parsed-response
  "Gets the response for a HTTP request"
  [method path & [body-data]]
  (let [system (component/start (system/create))
        body-data (if (nil? body-data) nil (str body-data))
        response ((http/app system) (mock/request method path body-data))]
    (component/stop system)
    response))
