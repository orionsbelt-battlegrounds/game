(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.controllers.reply
  "Utilities to format an HTTP response")

(defn- data
  "Builds an http response"
  [status obj]
  {:body (str obj)
   :headers {"Content-Type" "application/edn"}
   :status status})

(defn ok
  "Returns a status 200 OK, edn response"
  [obj]
  (data 200 obj))
