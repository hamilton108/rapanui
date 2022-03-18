(ns rapanui.adapter.crittersadapter
  (:gen-class)
  (:require
   [rapanui.ports :as ports])
  (:import
   [oahu.financial StockOption$OptionType]
   [critterrepos.models.impl StockMarketReposImpl]))

(def CALL StockOption$OptionType/CALL)
(def PUT StockOption$OptionType/PUT)

(def repos (StockMarketReposImpl.))

(defn tryme []
  (let [stocks (.getStocks repos)]
    (prn stocks)))

(defn crits [ptype status]
  (let [p (.purchasesWithSalesAll repos ptype status CALL)]
    (prn p)))

(defrecord CrittersAdapter
           ports/MaunaloaDB
  (demo [this]
    3))