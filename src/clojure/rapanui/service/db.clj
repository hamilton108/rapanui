(ns rapanui.service.db
  (:import
    [oahu.financial StockPrice DerivativePrice]
    [critterrepos.models.mybatis CritterMapper StockMapper DerivativeMapper]
    [critterrepos.beans.options
      DerivativeBean
      OptionPurchaseBean
      OptionSaleBean]
    [critterrepos.beans.critters
      CritterBean]
    ;[maunakea.util MyBatisUtils]
    ;[org.apache.ibatis.session SqlSession])
    [org.apache.ibatis.io Resources]
    [org.apache.ibatis.session SqlSession SqlSessionFactoryBuilder])
  (:require
    [rapanui.service.logservice :as LOG]))

(def ^:dynamic *register-critter-sale*)

(def get-session
  (memoize
    (fn []
      (let [reader (Resources/getResourceAsReader "mybatis.conf.xml")
            builder (SqlSessionFactoryBuilder.)
            factory (.build builder reader)]
        (.close reader)
        factory))))

(defmacro with-session [mapper & body]
  `(let [^SqlSession session# (.openSession (get-session)) ;(MyBatisUtils/getSession)
         ~'it (.getMapper session# ~mapper)
         result# ~@body]
    (doto session# .commit .close)
    result#))

(defn critters [purchase-type]
  (let [result (with-session CritterMapper
                 (.activePurchasesAll it purchase-type))]
    result))

(defn register-critter-sale [^OptionPurchaseBean purchase, ^CritterBean critter]
  (let [^DerivativePrice d (.getDerivativePrice purchase)
        ^OptionSaleBean sale-bean (OptionSaleBean. (.getOid purchase) (-> d .get .getBuy) (.getSellVolume critter))]
    (.addSale purchase sale-bean)
    (with-session CritterMapper
      (do
        (.insertCritterSale it sale-bean)
        (.setSaleId critter (.getOid sale-bean))
        (.registerCritterClosedWithSale it critter)
        (.setStatus critter 9)
        (if (= (.isFullySold purchase) true)
          (.registerPurchaseFullySold it purchase))))))


(defn test-register-critter-sale [^OptionPurchaseBean opx
                                  ^CritterBean critter]
  (.setStatus critter 9))


(defn find-derivative [opname]
  (with-session DerivativeMapper
    (.findDerivative it opname)))

(defn register-purchase [opname price volume spot-at-purchase buy-at-purchase]
  (if-let [d (find-derivative opname)]
    (let [p (OptionPurchaseBean.)]
      (doto p
        (.setOptionId (.getOid d))
        (.setDx (java.util.Date.))
        (.setPrice price)
        (.setVolume volume)
        (.setStatus 1)
        (.setPurchaseType 3)
        (.setSpotAtPurchase spot-at-purchase)
        (.setBuyAtPurchase buy-at-purchase))
      (with-session CritterMapper
        (.insertPurchase it p))
      p)))

(comment
 (defn maxdx->map [mx]
   (loop [x mx result {}]
     (if (not (seq x))
       result
       (let [m (first x)
             tix (.get m "ticker_id")
             dx (DateMidnight. (.getTime (.get m "max_dx")))]
         (recur (rest x) (assoc result tix dx))))))

 (defn get-max-dx []
   (let [result (with-session StockMapper
                  (.selectMaxDate it))]
     (maxdx->map result))))
