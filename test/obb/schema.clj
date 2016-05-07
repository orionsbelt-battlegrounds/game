(ns obb.schema
  (:require [schema.core :as s]))

;; on test, all schema defined fns will validate
;; inputs and outputs
(s/set-fn-validation! true)

