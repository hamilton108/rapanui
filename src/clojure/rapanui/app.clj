(ns rapanui.app
  (:gen-class)
  (:import
   [java.time LocalTime])
  (:use
   [rapanui.cli :only (cli)]))

(defn block-task [test wait]
  (while (test)
    (prn "Market not open yet...")
    (Thread/sleep wait)))

(defn while-task [test wait f]
  (while (test) (do (f) (Thread/sleep wait))))

(defn time-less-than [cur-time]
  (fn []
    (< (.compareTo (LocalTime/now) cur-time) 0)))

(defn -main [& args]
  (let [parsed-args-vec (cli args
                             ["-h" "--[no-]help" "Print cmd line options and quit" :default false]
                             ["-x" "--xml" "Spring xml filename" :default "rapanui.xml"]
                             ["-o" "--open" "Market opening time" :default "9:30"]
                             ["-c" "--close" "Market closing time" :default "17:20"]
                             ["-i" "--interval" "Check time interval (minutes)" :default "10"]
                             ["-s" "--startindex" "Start Index for saved html" :default "0"]
                             ["-t" "--[no-]test" "Test run (will not perform Netfonds transaction)" :default false]
                             ["-m" "--[no-]mail" "Email when option sale has been executed" :default false]
                             ["-q" "--[no-]query" "Print critter info (with purchase type from -p) and quit" :default false]
                             ["-p" "--purchase" "Purchase type [11: paper, 3: real time]" :default "11"]
                             ["-b" "--[no-]buy" "Buy real-time from Netfonds" :default false]
                             ["-P" "--price" "With -b, real-time option price"]
                             ["-O" "--option-name" "With -b, real-time option name"]
                             ["-S" "--spot" "With -b, real-time spot at purchase"]
                             ["-B" "--buy-at-purchase" "With -b, real-time buy at purchase"]
                             ["-v" "--volume" "With -b, real-time option volume" :default 10])
        parsed-args (first parsed-args-vec)
        check-arg (fn [arg] (= (arg parsed-args) true))]

    (if (check-arg :help)
      (do
        (println parsed-args-vec)
        (System/exit 0)))))
