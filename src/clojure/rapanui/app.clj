(ns rapanui.app
  (:gen-class)
  (:require
   [rapanui.common :refer [with-session str->date]]
   [rapanui.config :refer [get-context]]
   [rapanui.purchase :as P]
   [clojure.tools.cli :refer [parse-opts]])
  (:import
   [java.time LocalTime]))

(defn block-task [test wait]
  (while (test)
    (prn "Market not open yet...")
    (Thread/sleep wait)))

(defn while-task [test wait f]
  (while (test) (do (f) (Thread/sleep wait))))

(defn time-less-than [cur-time]
  (fn []
    (< (.compareTo (LocalTime/now) cur-time) 0)))

;; (def cli-options
;;   ;; An option with a required argument
;;   [["-p" "--port PORT" "Port number"
;;     :default 80
;;     :parse-fn #(Integer/parseInt %)
;;     :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
;;    ;; A non-idempotent option (:default is applied first)
;;    ["-v" nil "Verbosity level"
;;     :id :verbosity
;;     :default 0
;;     :update-fn inc] ; Prior to 0.4.1, you would have to use:
;;                    ;; :assoc-fn (fn [m k _] (update-in m [k] inc))
;;    ;; A boolean option defaulting to nil
;;    ["-h" "--help"]])

(defn arg->int [arg]
  (Integer/parseInt arg))

(def opts
  [["-h" "--help"]
   ["-p" "--profile PROFILE" "Profile: demo or prod" :parse-fn #(keyword %) :default "demo"]
   ["-o" "--open OPEN" "Market opening time" :default "9:30"]
   ["-c" "--close CLOSE" "Market closing time" :default "17:20"]
   ["-i" "--interval INTERVAL" "Check time interval (minutes)" :parse-fn arg->int :default 10]])

(defn check [arg opts]
  (= (arg opts) true))

(defn run [opts]
  (let [opening-time (str->date (:open opts))
        closing-time (str->date (:close opts))
        interval (* 60000 (:interval opts))
        profile (:profile opts)
        ctx (get-context (:profile opts))]
    (prn ctx)
    (prn (class profile))))

(defn -main [& args]
  (let [pargs (parse-opts args opts)
        opts (:options pargs)]
    (prn opts)
    (if (check :help opts)
      (print (:summary pargs) "\n")
      (run opts))))

(defn demo []
  (with-session critter.mybatis.CritterMapper
    (.activePurchasesWithCritters it 11)))

(defn demo2 []
  (let [ctx {:purchase-type 11}]
    (P/apply-purchases ctx)))

;; (defn -main [& args]
;;   (let [parsed-args-vec (cli args
;;                              ["-h" "--[no-]help" "Print cmd line options and quit" :default false]
;;                              ["-x" "--xml" "Spring xml filename" :default "rapanui.xml"]
;;                              ["-o" "--open" "Market opening time" :default "9:30"]
;;                              ["-c" "--close" "Market closing time" :default "17:20"]
;;                              ["-i" "--interval" "Check time interval (minutes)" :default "10"]
;;                              ["-s" "--startindex" "Start Index for saved html" :default "0"]
;;                              ["-t" "--[no-]test" "Test run (will not perform Netfonds transaction)" :default false]
;;                              ["-m" "--[no-]mail" "Email when option sale has been executed" :default false]
;;                              ["-q" "--[no-]query" "Print critter info (with purchase type from -p) and quit" :default false]
;;                              ["-p" "--purchase" "Purchase type [11: paper, 3: real time]" :default "11"]
;;                              ["-b" "--[no-]buy" "Buy real-time from Netfonds" :default false]
;;                              ["-P" "--price" "With -b, real-time option price"]
;;                              ["-O" "--option-name" "With -b, real-time option name"]
;;                              ["-S" "--spot" "With -b, real-time spot at purchase"]
;;                              ["-B" "--buy-at-purchase" "With -b, real-time buy at purchase"]
;;                              ["-v" "--volume" "With -b, real-time option volume" :default 10])
;;         parsed-args (first parsed-args-vec)
;;         check-arg (fn [arg] (= (arg parsed-args) true))]

;;     (if (check-arg :help)
;;       (do
;;         (println parsed-args-vec)
;;         (System/exit 0)))))
