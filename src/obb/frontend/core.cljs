(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"
      :figwheel-always true}
  obb.frontend.core
  "Entry point of the frontend app"
  (:require [cljs.reader :as reader]
            [ajax.core :refer [GET]]))

(enable-console-print!)

(def rest-options
  {:format :edn
   :handler (fn [response]
              (prn (reader/read-string response)))})

(defn on-js-reload []
  (prn "Reloaded...")
  (GET "http://localhost:54321/" rest-options))

(defn init []
  (on-js-reload))

(defonce start
  (init))
