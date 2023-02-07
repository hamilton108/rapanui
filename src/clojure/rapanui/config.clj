(ns rapanui.config
  (:require
   [clojure.core.match :refer [match]]
   [rapanui.adapter.critteradapter :as C]
   [rapanui.adapter.nordnetadapter :as D])
  (:import
   ;(vega.financial.calculator BlackScholes)
   ;(nordnet.redis NordnetRedis)
   (java.time LocalDate)))

;; (defn redis [ct]
;;   (match ct
;;     :prod (NordnetRedis. "172.20.1.2" 0)
;;     :test (NordnetRedis. "172.20.1.2" 5)
;;     :demo (NordnetRedis. "172.20.1.2" 4)))

(defn cur-date [ct]
  (match ct
    :prod (LocalDate/now)
    :test (LocalDate/of 2022 5 25)
    :demo (LocalDate/of 2022 10 17)))

(defn get-context [ct]
  (let [service "http://localhost:8082"]
    (match ct
      :prod
      {:purchase-type 4
       :price-fn (partial D/fetch-stockoption-price service)}
      :test
      {:purchase-type 11
       :price-fn (partial D/fetch-stockoption-price-stub service)}
      :demo
      {:purchase-type 11
       :price-fn (partial D/fetch-stockoption-price-demo service)})))