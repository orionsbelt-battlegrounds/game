(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"
      :figwheel-always true}
  obb.frontend.core
  "Entry point of the frontend app")

(enable-console-print!)

(defn on-js-reload []
  (println "Reloaded..."))

(defn init []
  (on-js-reload))

(defonce start
  (init))
