(ns rapanui.adapter.critteradapter
  (:gen-class)
  (:require
   [rapanui.common :refer [with-session]])
  (:import
   (critter.mybatis
    CritterMapper
    StockMapper
    StockOptionMapper)))


;; (def stock-market-repos
;;   (reify StockMarketRepository
;;     (findStock [_ oid]
;;       nil)
;;               ;(.createStock factory oid))
;;     (findStockOption [_ stockOptInfo]
;;       (Optional/empty))
;;     (activePurchasesWithCritters [_  purchaseType]
;;       ;Int -> List<StockOptionPurchase>
;;       [])
;;     (purchasesWithSalesAll [_ purchaseType status optionType]
;;       ;Int -> Int -> StockOption.OptionType -> List<StockOptionPurchase> 
;;       [])))


(defn fetch-purchases [env]
  (with-session CritterMapper
    (.activePurchasesWithCritters it env)))


;; (defrecord StockMarketAdapter []
;;   StockMarketRepository
;;   (findStock [this oid]
;;       ;Int -> Stock
;;     (find-stock oid))
;;   (findStockOption [this stockOptInfo]
;;     (with-session StockOptionMapper
;;       (if-let [result (.findStockOption it stockOptInfo)]
;;         (Optional/of result)
;;         (Optional/empty))))
;;   (activePurchasesWithCritters [this purchaseType]
;;       ;Int -> List<StockOptionPurchase>
;;     [])
;;   (purchasesWithSalesAll [this purchaseType status optionType]
;;       ;Int -> Int -> StockOption.OptionType -> List<StockOptionPurchase> 
;;     (with-session CritterMapper
;;       (.purchasesWithSalesAll it purchaseType 1 nil))))
