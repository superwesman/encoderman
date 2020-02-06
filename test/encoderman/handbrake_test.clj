(ns encoderman.handbrake-test
  (:require [clojure.test :refer :all]
            [encoderman.handbrake :as h]))


(deftest test-compile-options
  "Examples of all the different options"
  (are [expected option value]
    (= expected (h/compile-option option value))

    ["--aencoder" "copy:flac"]
    :aencoder :copy:flac

    ["--aencoder" "flac24"]
    :aencoder :flac24

    ["--all-audio"]
    :all-audio true

    []
    :all-audio false

    ["--arate" "auto"]
    :arate :auto

    ["--audio-lang-list" "eng"]
    :audio-lang-list [:eng]
    ["--audio-lang-list" "eng,ita"]
    :audio-lang-list [:eng :ita]

    ["--comb-detect"]
    :comb-detect true
    []
    :comb-detect false

    ["--decomb"]
    :decomb true
    []
    :decomb false

    ["--encoder" "x265"]
    :encoder :x265

    ["--encoder-preset" "medium"]
    :encoder-preset :medium

    ["--input" "/Path/to/File.mkv"]
    :input "/Path/to/File.mkv"

    ["--input" "/Path/to/File with spaces.mkv"]
    :input "/Path/to/File with spaces.mkv"

    ["--markers"]
    :markers true
    []
    :markers false

    ["--mixdown" "auto"]
    :mixdown :auto

    ["--native-language" "eng"]
    :native-language :eng

    ["--output" "/Path/to/File.mkv"]
    :output "/Path/to/File.mkv"

    ["--output" "/Path/to/File with spaces.mkv"]
    :output "/Path/to/File with spaces.mkv"

    ["--pfr"]
    :pfr true
    []
    :pfr false

    ["--quality" "12.3"]
    :quality 12.3

    ["--rate" "222"]
    :rate 222

    ["--subtitle" "scan"]
    :subtitle :scan

    ["--subtitle-burned"]
    :subtitle-burned true
    []
    :subtitle-burned false

    ["--subtitle-forced"]
    :subtitle-forced true
    []
    :subtitle-forced false

    ["--verbose"]
    :verbose true
    []
    :verbose false
    ))


(deftest test-->args-defaults
  "what happens if we pass nothing?"
  (is (= []
         (h/->args {}))))