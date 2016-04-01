(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.core
  "This namespace has the main fn that starts the game server, and all
  the necessary config, such as routing and system/context build up."
  (:gen-class)
  (:require
    [compojure.core :as compojure :refer [GET]]
    [ring.middleware.cors :as cors]
    [ring.middleware.params :as params]
    [compojure.route :as route]
    [clojure.core.async :refer [put! chan <! >! go go-loop close! >!! <!! timeout]]
    [aleph.http :as http]
    [obb.server.handlers.index :as index]))

(compojure/defroutes public-routes
  "The routes available to be served, that don't need auth"
  (GET "/" request (index/handler request))
  (route/not-found "No such page."))

(defn- setup-cors
  "Setup cors"
  [handler]
  (cors/wrap-cors handler
                  :access-control-allow-origin
                  [#"^http://localhost(.*)"]
                  :access-control-allow-methods [:get :put :post :delete]))

(def app
  "The main app handler"
  (-> (compojure/routes public-routes)
      (setup-cors)))

(defn -main [& args]
  (let [port 54321]
    (println (str "** OBB Server " (or (get (System/getenv) "OBB_ENV" "development"))
                  " running on port " port))
    (http/start-server app {:port port})))
