(ns ^{:added "0.1.0" :author "Joaquim Torres"}
    obb.repositories.game-repository
  (:require [datomic.api :as datomic]))

(defn enum-value-fn
  "Returns a function that transforms the attribute into a Datomic enum value."
  [attr-name attr-val]
  (-> (str "game." (name attr-name) "/" (name attr-val))
      keyword))

(def attr-names->datomic-attr-names
  {:mode :game/mode
   :state :game/state
   :terrain :game/terrain
   :first-player :game/first-player
   :width :game/board-width
   :height :game/board-height})

(def enum-attributes
  [:mode :state :terrain :first-player])

(defn attr-name->datomic-attr-name
  "Returns the name of the Datomic attribute for the given game attribute name."
  [attr-name]
  (attr-name attr-names->datomic-attr-names))

(defn attr-name->transform-value-fn
  "Returns the value transformation function that needs to be applied to
  the attribute value before sending it to Datomic."
  [attr-name]
  (if (some #(= % attr-name) enum-attributes)
    (partial enum-value-fn attr-name)
    identity))

(defn attr-val->datomic-attr-val
  "Returns the value of an attribute, after transforming it for Datomic.
  In some cases, no transformation needs to be done and the same value
  is returned."
  [attr-name attr-val]
  ((attr-name->transform-value-fn attr-name) attr-val))

(defn game->create-transact
  "Creates the data structure representing a game creation transaction
  for Datomic."
  [game tempid]
  (-> (reduce-kv (fn [datomic-game attr-name attr-val]
                   (assoc datomic-game
                          (attr-name->datomic-attr-name attr-name)
                          (attr-val->datomic-attr-val attr-name attr-val)))
                 {}
                 game)
      (assoc :db/id tempid)
      vector))

(defn- simulate
  "Simulate the transaction without actually performing the
  operation on the database.
  If datomic/with and datomic/transact exposed exactly the same
  interface, this function would not be needed. It only exists
  to make the two functions transparently interchangeable."
  [conn tx-data]
  (future (datomic/with (datomic/db conn) tx-data)))

(defn- transact-fn
  "Get the transact function to use based on the the given options"
  [{:keys [dry-run]}]
  (if dry-run
    simulate
    datomic/transact))

(defn create
  "Create a game in the database"
  [conn game & [options]]
  (let [tempid (datomic/tempid :db.part/user)
        transact (transact-fn options)
        tx-data (game->create-transact game tempid)
        {:keys [db-after tempids]} @(transact conn tx-data)]
    {:created-id (datomic/resolve-tempid db-after tempids tempid)
     :db db-after}))
