(ns obb.repositories.db-repository-test
  (:require  [clojure.test :refer :all]
             [obb.repositories.db-repository :as sut]))

(def db-uri "datomic:mem://obb-test-create-db-new")

(deftest create-db
  (testing "returns the newly created db value"
    (sut/delete db-uri)
    (is (:db (sut/create db-uri))))
  (testing "returns nil when db already exists"
    (sut/create db-uri)
    (is (not (sut/create db-uri)))))
