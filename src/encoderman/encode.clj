(ns encoderman.encode
  (:require [clojure.java.shell :as sh]
            [me.raynes.fs :as fs]
            [taoensso.timbre :as log]

            [encoderman.handbrake :as enc]))

(defn reduction
  "size reduction as percent, assuming same units"
  [source-size destination-size]
  (int (/ (* 100 destination-size)
          source-size)))



(defn encode
  [executable encoder-options]
  (let [arguments (enc/->args encoder-options)
        _ (log/infof "encoding: %s %s" executable arguments)
        script (partial sh/sh executable)]
    (apply script arguments)))

(defn encoder
  "returns a file encoder"
  [{:keys [executable encoder-options]}]
  (fn [source-file destination-file]
    (encode executable (assoc encoder-options
                         :input source-file
                         :output destination-file))))

;
;source="$1"
;destination="${source/$STAGED/$ENCODED}"
;final="${destination/$ENCODED/$PLEX}"
;
;echo "$(date): clean up!"
;find $COMPLETED $LOGS -mtime +7 -exec ls -lrtdh {} \;
;find $COMPLETED $LOGS -mtime +7 -delete
;find $RIPPED -name .DS_Store -delete
;find $RIPPED -type d -ctime +1 -empty -exec ls -lrtdh {} \;
;find $RIPPED -type d -ctime +1 -empty -delete
;
;echo "$(date): Converting $source -> $destination ..."
;mkdir -p $( dirname "$destination" )
;chmod 444 "$source"
;rm -f "$destination"
;ls -lrtdh "$source" "$destination"
;

;
;echo "$(date): done!"
;chmod 444 "$destination"
;ls -lrtdh "$source" "$destination"
;
;if [[ $? -eq 0 ]]
;then
;mv "$destination" "$final"
;mv "$source" $COMPLETED/
;else
;echo >&2 "FAILED!"
;mv "$source" "$destination" $FAILED/ 2>&-
;fi
