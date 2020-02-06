(defproject super/encoderman "0.1.0-SNAPSHOT"
  :description "clojure wrapper around HandBrake command-line interface (HandBrakeCLI)"

  :license {}

  :dependencies [[com.taoensso/timbre "4.10.0"]

                 [me.raynes/fs "1.4.6"]

                 [org.clojure/clojure "1.10.0"]]

  :profiles {:dev {:plugins  [[lein-githooks "0.1.0"]]
                   :githooks {:auto-install true
                              :pre-push     ["lein test"]
                              }}})
