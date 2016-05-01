(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.routes
  "Specifices the routes and associates handlers"
  (:require
    [compojure.core :as compojure :refer [GET]]
    [compojure.route :as route]
    [obb.server.handlers.index :as index]))

(compojure/defroutes public-routes
  "The routes available to be served, that don't need auth"
  (GET "/" request (index/handler request))
  (route/not-found "No such page."))

