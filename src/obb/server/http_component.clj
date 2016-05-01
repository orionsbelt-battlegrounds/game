(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.http-component
  "The aleph server as a component"
  (:require [com.stuartsierra.component :as component]
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

(def app
  "The main app handler"
  (-> (compojure/routes routes/public-routes)
      (setup-cors)))

(defrecord HttpServerComponent [server port]

  component/Lifecycle

  (start [component]
    (println (str "** OBB HTTP Server " (or (get (System/getenv) "OBB_ENV" "development"))
                  " running on port " port))
    (assoc component :server (http/start-server app {:port port})))

  (stop [component]
    (.close server)
    (dissoc component server)))

(defn create
  "Creates a new http component"
  []
  (map->HttpServerComponent {:port 54321}))
