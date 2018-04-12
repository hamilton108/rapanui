(ns rapanui.app
  (:gen-class)
  (:import
     [java.time LocalTime]
     [org.springframework.context.support ClassPathXmlApplicationContext]
     [critterrepos.beans.options OptionPurchaseBean]
     [critterrepos.beans.critters CritterBean]
     [oahu.financial.repository ChachedEtradeRepository])
  (:require
    [rapanui.common :as COM]
    [rapanui.service.db :as DB]
    [rapanui.service.etransaction :as ET]
    [rapanui.service.mail :as MAIL]
    [rapanui.service.logservice :as LOG]
    [rapanui.protocols.rules :as R]
    [rapanui.protocols.derivatives :as D]
    [rapanui.derivatives.purchase :as P])
  (:use
    [rapanui.cli :only (cli)]))


(defn block-task [test wait]
  (while (test)
    (LOG/info "Market not open yet...")
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
          check-arg (fn [arg] (= (arg parsed-args) true))
          ptyp (read-string (:purchase parsed-args))]


    (if (check-arg :help)
      (do
        (println parsed-args-vec)
        (System/exit 0)))

    (println args)

    (if (check-arg :buy)
      (let [price (read-string (:price parsed-args))
            volume (read-string (:volume parsed-args))
            opname (:option-name parsed-args)
            spot (:spot parsed-args)
            buy-at-purchase (:buy-at-purchase parsed-args)]
        (println (str "Option: " opname ", price: " price ", volume: " volume))
        (if (= (check-arg :test) false)
          (ET/buy-option opname price volume))
        (let [new-purchase (DB/register-purchase opname price volume spot buy-at-purchase)]
          (println new-purchase))
        (System/exit 0)))

    (if (check-arg :test)
      (LOG/warn "Test run mode!"))

    (if (check-arg :query)
      (binding [COM/*factory* (ClassPathXmlApplicationContext. (:xml parsed-args))]
        (let [beans (DB/critters ptyp)]
          (println (str "Purchase type: " ptyp))
          (doseq [b beans]
            (D/strutMyStuff b))
          (System/exit 0))))
    (let [factory (ClassPathXmlApplicationContext. (:xml parsed-args))]
      (binding [COM/*factory* factory
                ET/*sell-option* (if (check-arg :test) ET/test-sell-option ET/sell-option)
                ET/*logon-params* (let [downloader (.getBean factory "downloader")] (.logonParam downloader))
                DB/*register-critter-sale* DB/register-critter-sale
                MAIL/*email-option-sale* (:mail parsed-args)]
        (let [
              beans (DB/critters ptyp)
              repos ^CachedEtradeRepository (.getBean COM/*factory* "cached-etrade")
              run (fn []
                    (doseq [b beans]
                      (D/applyCritters b))
                    (.invalidateCache repos))
              opening-time (COM/str->date (:open parsed-args))
              closing-time (COM/str->date (:close parsed-args))
              interval (* 60000 (read-string (:interval parsed-args)))]
          (doseq [bean beans] (.setRepository bean repos))
          (block-task (time-less-than opening-time) interval)
          (while-task (time-less-than closing-time) interval run))))))


;(-main "-h")
;(-main "-q")
<<<<<<< Updated upstream
;(-main "-t" "-m" "-i" "1")
;(-main *command-line-args*)
