(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.server.controllers.index
  "Controller to handle the root/index request to the server")

(defn handler
  "Returns a intro to the game"
  [request]
  {:body "Hello World!"
   :status 200})
