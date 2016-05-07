(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.http-component
  "The aleph server as a component"
  (:require [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [ring.middleware.cors :as cors]
            [aleph.http :as http]
            [compojure.core :as compojure]
            [obb.server.routes :as routes]))

(defn- setup-cors
  "Setup cors"
  [handler]
  (cors/wrap-cors handler
                  :access-control-allow-origin
                  [#"^http://localhost(.*)"]
                  :access-control-allow-methods [:get :put :post :delete]))

(defn- set-system
  "Adds the system to the request data"
  [f system]
  (fn [request]
    (f (assoc request :system system))))

(defn app
  "The main app handler"
  [system]
  (-> (compojure/routes routes/public-routes)
      (set-system system)
      (setup-cors)))

(defrecord HttpServerComponent [server port meta closed-ch]

  component/Lifecycle

  (start [component]
    (println (str "** OBB HTTP Server " (or (get (System/getenv) "OBB_ENV" "development"))
                  " running on port " port))
    (assoc component :server (http/start-server (app (:system meta))
                                                     {:port port})
                     :closed-ch (async/chan)))

  (stop [component]
    (.close server)
    (async/>!! closed-ch {:http-server :closed})
    (dissoc component server)))

(defn create
  "Creates a new http component"
  []
  (component/using
    (map->HttpServerComponent {:port 54321})
    [:meta]))
