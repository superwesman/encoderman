(ns encoderman.handbrake
  (:require [clojure.string :as str]))

(def success?
  "HandbrakeCLI seems to exit with 0 always, so consider stderr"
  (comp empty? :err))

(def failed?
  "HandbrakeCLI seems to exit with 0 always, so consider stderr"
  (complement success?))

(defn ->flag
  "creates a command-line flag like: --verbose"
  [option]
  (format "--%s" (name option)))

(defn boolean-flag
  "add flag, if true"
  [option enabled?]
  (if enabled?
    [(->flag option)]
    []))

(defn ->option
  "creates a command-line option like: --file /other/here.txt"
  [option value]
  [(->flag option) (str value)])

(defn comma-separated
  [coll]
  (str/join "," (map name coll)))

(defmulti compile-option
          (fn [option value] option))

(defmethod compile-option :default
  [option value]
  (throw (IllegalArgumentException. (format "unknown option %s with %s value %s" option (type value) value))))

(defmethod compile-option :aencoder
  [option aencoder]
  (->option option (name aencoder)))

(defmethod compile-option :all-audio
  [option all-audio?]
  (boolean-flag option all-audio?))

(defmethod compile-option :arate
  [option arate]
  (->option option (name arate)))

(defmethod compile-option :audio-lang-list
  [option audio-lang-list]
  (->option option (comma-separated audio-lang-list)))

(defmethod compile-option :comb-detect
  [option comb-detect?]
  (boolean-flag option comb-detect?))

(defmethod compile-option :decomb
  [option decomb?]
  (boolean-flag option decomb?))

(defmethod compile-option :encoder
  [option encoder]
  (->option option (name encoder)))

(defmethod compile-option :encoder-preset
  [option encoder-preset]
  (->option option (name encoder-preset)))

(defmethod compile-option :input
  [option input]
  (->option option (name input)))

(defmethod compile-option :markers
  [option markers?]
  (boolean-flag option markers?))

(defmethod compile-option :mixdown
  [option mixdown]
  (->option option (name mixdown)))

(defmethod compile-option :native-language
  [option native-language]
  (->option option (name native-language)))

(defmethod compile-option :output
  [option output]
  (->option option (name output)))

(defmethod compile-option :pfr
  [option pfr?]
  (boolean-flag option pfr?))

(defmethod compile-option :quality
  [option quality]
  (->option option quality))

(defmethod compile-option :rate
  [option rate]
  (->option option rate))

(defmethod compile-option :subtitle
  [option subtitle]
  (->option option (name subtitle)))

(defmethod compile-option :subtitle-burned
  [option subtitle-burned?]
  (boolean-flag option subtitle-burned?))

(defmethod compile-option :subtitle-forced
  [option subtitle-forced?]
  (boolean-flag option subtitle-forced?))

(defmethod compile-option :verbose
  [option verbose?]
  (boolean-flag option verbose?))

(defn ->args
  "converts encoder options to vector of CLI argument Strings"
  [options]
  (vec (mapcat
         (partial apply compile-option)
         options)))


(comment
  --aencoder flac24
  --all-audio
  --arate auto
  --audio-lang-list eng
  --comb-detect
  --decomb
  --encoder x265
  --encoder-preset $preset
  --input "$source"
  --markers
  --mixdown auto
  --native-language eng
  --output "$destination"
  --pfr
  --quality $quality
  --rate 30
  --subtitle scan
  --subtitle-burned
  --subtitle-forced
  --verbose
  )