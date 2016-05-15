(ns ^{:added "0.1.0" :author "Pedro Pereira Santos"}
  obb.models.game-info
  "This model represents a game between two players"
  (:require [schema.core :as s]))

(def IdSchema s/Str)

(def GameInfo
  "The schema definition for a game-info"
  {:id IdSchema
   (s/optional-key :p1) IdSchema})

(s/defn create :- GameInfo
  "Creates a new empty game-info"
  []
  {:id (str (gensym))})

(s/defn id :- IdSchema
  "Gets the id of the game"
  [game-info :- GameInfo]
  (:id game-info))

(s/defn set-p1 :- GameInfo
  "Sets the p1 identifier"
  [game-info :- GameInfo
   p1-id :- IdSchema]
  (assoc game-info :p1 p1-id))

(s/defn p1 :- IdSchema
  "Gets the p1 identifier"
  [game-info :- GameInfo]
  (:p1 game-info))
