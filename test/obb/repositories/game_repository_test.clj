(ns obb.repositories.game-repository-test
  (:require [obb.repositories.game-repository :as sut]
            [clojure.test :refer :all]
            [datomic.api :as datomic]))

(def uri "datomic:mem://obb-game-test")

(def conn (delay (datomic/connect uri)))

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

(defn- setup-test-db
  "Creates a datomic database for testing"
  []
  (datomic/create-database uri)
  (datomic/transact @conn db-schema))

(defn- teardown-test-db
  "Deletes the datomic database used for testing"
  []
  (datomic/delete-database uri))

(defn- use-test-db
  "Sets up and tears down a datomic db for the tests"
  [f]
  (setup-test-db)
  (f)
  (teardown-test-db))

(use-fixtures :once use-test-db)

(let [tempid "test-id"
      game {:mode :annihilation
            :state :deploy
            :terrain :ice
            :first-player :p1
            :width 5
            :height 5}]
  (deftest create-game
    (testing "game is created"
      (is (:created-id (sut/create game)))))

  (deftest create-game-transact
    (testing "transact data contains a temp id"
      (is (-> (sut/game->create-transact game tempid)
              first
              :db/id)))
    (testing "transact data contains transformed attribute names"
      (let [tx-data (first (sut/game->create-transact game tempid))]
        (is :game/mode)
        (is :game/board-height)))
    (testing "transact data contains transformed enum values"
      (is (= :game.mode/annihilation
             (-> (sut/game->create-transact game tempid)
                 first
                 :game/mode))))
    (testing "transact data contains attribute values without transformations"
      (is (= 5
             (-> (sut/game->create-transact game tempid)
                 first
                 :game/board-width))))))
