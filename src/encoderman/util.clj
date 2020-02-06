(ns encoderman.util
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(defn read-edn
  [filename]
  (with-open [reader (PushbackReader. (io/reader filename))]
    (edn/read reader)))