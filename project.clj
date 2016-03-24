(defproject obb-game "0.1.0"
  :description "Orion's Belt BattleGrounds Game"
  :url "https://github.com/orionsbelt-battlegrounds/game"

  :license {:name         "MPLv2"
            :distribution :repo}

  :min-lein-version "2.6.0"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.8.34"]]

  :aliases {"server"  ["with-profile" "clj" "run" "-m" "obb.server.core/-main"]
            "test"  ["with-profile" "clj-test" "test"]}

  :scm {:name "git"
        :url "git@github.com:orionsbelt-battlegrounds/game.git"}

  :profiles {

     ;;
     ;; Main profile for the Clojure/JVM
     ;;

     :clj {

        :dependencies [[compojure "1.5.0"]
                       [aleph "0.4.1-beta4"]]

       :plugins [[com.jakemccrary/lein-test-refresh "0.10.0"]
                 [lein-cloverage "1.0.2"]]

       :main obb.game.server}

     :clj-test [:test :clj]

     ;;
     ;; Common test configuration
     ;;

     :test {:dependencies [[ring/ring-mock "0.3.0"]]}})
