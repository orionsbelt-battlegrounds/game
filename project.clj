(defproject obb-game "0.1.0"
  :description "Orion's Belt BattleGrounds Game"
  :url "https://github.com/orionsbelt-battlegrounds/game"

  :license {:name         "MPLv2"
            :distribution :repo}

  :min-lein-version "2.6.0"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [org.clojure/clojurescript "1.8.51"]
                 [cljs-ajax "0.5.4"]
                 [prismatic/schema "1.1.1"]
                 [com.stuartsierra/component "0.3.1"]
                 [walmartlabs/system-viz "0.1.1"]
                 [clanhr/result "0.11.0"]]

  :aliases {"server"  ["trampoline" "with-profile" "clj" "run" "-m" "obb.server.core/-main"]
            "frontend" ["trampoline" "with-profile" "cljs-dev" "figwheel"]
            "system-viz" ["with-profile" "clj" "run" "-m" "obb.server.system/-main"]
            "autotest" ["trampoline" "with-profile" "+clj-test" "test-refresh"]
            "test"  ["trampoline" "with-profile" "clj-test" "test"]}

  :scm {:name "git"
        :url "git@github.com:orionsbelt-battlegrounds/game.git"}

  :profiles {

     ;;
     ;; Main profile for the Clojure/JVM
     ;;

     :clj {

        :dependencies [[compojure "1.5.0"]
                       [ring-cors "0.1.7"]
                       [aleph "0.4.1"]]

       :main obb.game.server}

     :clj-test [:test :clj]

     ;;
     ;; Main profile for ClojureScript dev
     ;;

     :cljs-dev {

       :dependencies [[org.clojure/tools.nrepl "0.2.12"]]

       :plugins [[lein-cljsbuild "1.1.2"]
                 [lein-figwheel "0.5.1"]]

       :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

        :figwheel {:server-port 3450
                   :css-dirs ["resources/public/css"]}

       :cljsbuild {:builds [{:id "dev"
                             :figwheel {:on-jsload "obb.frontend.core/on-js-reload"}
                             :source-paths ["src"]
                             :compiler {:main obb.frontend.core
                                        :recompile-dependents true
                                        :asset-path "js/compiled/out"
                                        :output-to "resources/public/js/compiled/obb.js"
                                        :output-dir "resources/public/js/compiled/out"
                                        :source-map-timestamp true}}]}}

     ;;
     ;; Common test configuration
     ;;

     :test {:dependencies [[ring/ring-mock "0.3.0"]
                           [org.clojure/tools.namespace "0.2.11"]]

            :plugins [[com.jakemccrary/lein-test-refresh "0.14.0"]
                      [lein-cloverage "1.0.2"]]}})
