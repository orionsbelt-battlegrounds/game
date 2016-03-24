(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.core
  "This namespace has the main fn that starts the game server, and all
  the necessary config, such as routing and system/context build up."
  (:gen-class)
  (:require
    [compojure.core :as compojure :refer [GET]]
    [ring.middleware.params :as params]
    [compojure.route :as route]
    [clojure.core.async :refer [put! chan <! >! go go-loop close! >!! <!! timeout]]
    [aleph.http :as http]
    [manifold.stream :as stream]
    [byte-streams :as bs]
    [manifold.stream :as s]
    [manifold.deferred :as d]
    [manifold.bus :as bus]
    [obb.server.controllers.index :as index]))

(compojure/defroutes public-routes
  "The routes available to be served, that don't need auth"
  (GET "/" request (index/handler request))
  (route/not-found "No such page."))

(def app
  "The main app handler"
  (-> (compojure/routes public-routes)))

(defn -main [& args]
  (let [port 54321]
    (println (str "** OBB Server " (or (get (System/getenv) "OBB_ENV" "development"))
                  " running on port " port))
    (http/start-server app {:port port})))
