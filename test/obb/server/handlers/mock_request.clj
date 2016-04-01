(ns obb.server.handlers.mock-request
  (:use clojure.test)
  (:require [obb.server.core :as core]
            [clojure.edn :as edn]
            [ring.mock.request :as mock]))

(defn http-get
  "Mocks a HTTP GET request. Returns the body as edn"
  [path]
  (let [response (core/app (mock/request :get "/"))]
    (assoc response :body (edn/read-string (:body response)))))
