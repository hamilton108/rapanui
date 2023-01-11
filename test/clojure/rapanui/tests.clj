(ns rapanui.tests
  (:require
   [clojure.test :refer [deftest is]]
   [rapanui.config :refer [get-context]]
   [rapanui.common :refer [not-nil?]]
   [rapanui.purchase :as P])
  (:import
   (critter.critterrule
    Critter AcceptRule RuleTypeEnum)
   (critter.stockoption StockOptionPurchase)))


;; (def json 
;;   {"option":
;;    {"days":167.0,"x":370.0,"ticker":"YAR2F370","expiry":"2022-06-17",
;;     "buy":116.25,"sell":119.25,"ivBuy":0.0,"ivSell":0.0,"brEven":0.0},
;;    "stock":
;;    {"o":482.0,"h":488.7,"l":481.0,"c":487.4,"unixTime":1641031800000}})

;; (deftest dfb-should-pass  
;;   (let [rule (AcceptRule. 1 3.0 7 "y")
;;         args {:dfb 3.2}]
;;     (is (= true (acc/pass rule args)))))


(defn create-accrules []
  (let [rule (AcceptRule. 7 2.0 (.getKind RuleTypeEnum/DFB))]
    [rule]))

(defn create-critters []
  (let [critter (Critter.)]
    (doto critter
      (.setOid 1)
      (.setName "Critter-1")
      (.setStatus 7)
      (.setSellVolume 10)
      (.setPurchaseId 1)
      (.setAcceptRules (create-accrules)))
    [critter]))

(defn create-purchase []
  (let [p (StockOptionPurchase.)]
    (doto p
      (.setOid 1)
      (.setTicker "YAR")
      (.setOptionName "YAR2F470")
      (.setStatus 1)
      (.setVolume 20)
      (.setSpotAtPurchase 34.7)
      (.setBuyAtPurchase 12.0)
      (.setPrice 12.8)
      (.setCritters (create-critters)))
    p))

(deftest test-stock-option-purchase
  (let [p (create-purchase)
        ctx (get-context :test)
        result (P/apply-purchase ctx p)
        critter (first (.getCritters p))]
    (is (not-nil? result))
    (prn result)
    (is (= (first result) (P/critter-result critter true)))))
