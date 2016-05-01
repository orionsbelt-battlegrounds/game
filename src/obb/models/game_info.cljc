(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.models.game-info
  "This model represents a game between two players")

(defn create
  "Creates a new empty game-info"
  []
  {:id (gensym)})

(defn id
  "Gets the id of the game"
  [game-info]
  (:id game-info))
