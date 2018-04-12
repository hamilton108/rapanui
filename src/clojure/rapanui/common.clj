(ns rapanui.common
  (:import 
    [java.time LocalTime]))

(def ^:dynamic *test-run*)

(def ^:dynamic *factory*)

(defn str->date [arg]
  (if-let [v (re-find #"(\d+):(\d+)" arg)]
    (let [hours (nth v 1)
          minutes (nth v 2)] 
      (println (str hours " " minutes))
      (LocalTime/of (read-string hours) (read-string minutes)))))
