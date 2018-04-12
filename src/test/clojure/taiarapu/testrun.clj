(ns taiarapu.testrun
  (:require
    [clojure.test :as T]
    [taiarapu.service.etransaction :as ET]
    [taiarapu.service.mail :as MAIL]
    [taiarapu.service.logservice :as LOG]
    [taiarapu.common :as COM]
    [taiarapu.protocols.derivatives :as D]
    [taiarapu.derivatives.purchase :as P]
    [taiarapu.service.db :as DB])
  (:import
    [org.springframework.context.support ClassPathXmlApplicationContext]
    [oahu.financial.repository DerivativeRepository]
    [taiarapu.mocks MockDownloader]))

(def test-count
  (let [counter (atom 0)]
    (fn []
      (do
        (swap! counter inc)
        @counter))))

(defn while-task [test wait f]
  (while (test) (do (f) (Thread/sleep wait))))

(binding [COM/*factory* (ClassPathXmlApplicationContext. "test-taiarapu.xml")
          ET/*sell-option* ET/test-sell-option
          DB/*register-critter-sale* DB/test-register-critter-sale
          MAIL/*email-option-sale* false]
  (let [beans (DB/critters 11)
        repos ^DerivativeRepository (.getBean COM/*factory* "repos")
        run (fn []
              (doseq [b beans]
                (D/applyCritters b))
              (.invalidateAll repos))
        interval 1000]
    (doseq [bean beans]
      (LOG/info (str "Bean ticker: "(.getTicker bean)))
      (.setRepository bean repos))
    (while-task #(<= (test-count) 3) interval run)))
      ;(LOG/info ("Test run nr: ")))))

    ;(T/is (= true (= 1 1)))))

