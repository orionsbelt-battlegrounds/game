(ns ^{:added "0.1.0" :author "Joaquim Torres"}
    obb.repositories.db-repository
  (:require [datomic.api :as datomic]))

(def db-schema
  [{:db/id #db/id [:db.part/db]
    :db/ident :game/mode
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "Game's mode of play"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/db]
    :db/ident :game/state
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "Game's state indicating the phase of the game"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/db]
    :db/ident :game/terrain
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "Game's kind of terrain"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/db]
    :db/ident :game/first-player
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one
    :db/doc "Game's first player"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/db]
    :db/ident :game/board-width
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc "Width of the game board"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/db]
    :db/ident :game/board-height
    :db/valueType :db.type/long
    :db/cardinality :db.cardinality/one
    :db/doc "Height of the game board"
    :db.install/_attribute :db.part/db}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.mode/annihilation}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.mode/supernova}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.state/deploy}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.state/p1}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.state/p2}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/water}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/ice}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/terrest}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/desert}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/rock}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.terrain/forest}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.state/final}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.first-player/p1}

   {:db/id #db/id [:db.part/user]
    :db/ident :game.first-player/p2}])

(defn delete
  "Delete the database with the given URI"
  [uri]
  (datomic/delete-database uri))

(defn create
  "Create the database with the given URI and adds the schema to it.
  This operation blocks until the transaction to add the schema finishes."
  [uri]
  (if (datomic/create-database uri)
    (let [conn (datomic/connect uri)]
      {:db (:db-after @(datomic/transact conn db-schema))})))
