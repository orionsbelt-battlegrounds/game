(ns obb.repositories.game-repository-test
  (:require [obb.repositories.game-repository :as sut]
            [obb.repositories.db-repository :as db-repo]
            [clojure.test :refer :all]
            [datomic.api :as datomic]))

(def uri "datomic:mem://obb-game-test")

(defn- use-test-db
  "Sets up and tears down a datomic db for the tests"
  [f]
  (db-repo/create uri)
  (f)
  (db-repo/delete uri))

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
