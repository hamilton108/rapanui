(ns rapanui.adapter.nordnetadapter
  (:gen-class)
  (:require
   [cheshire.core :as json]
   [clj-http.client :as client]))


;(defn fetch-stockoption-price [^StockOptionPurchase bean]
  ;Item itemWithOwner = new ObjectMapper().readValue(json, Item.class);
;  )

(defn req-parse [req]
  (json/parse-string (:body req) true))

(defn fetch-stockoption-price [service ticker]
  (let [url (str service "/option/" ticker)
        resp (req-parse (client/get url))]
    {:price (get-in resp [:option :buy])
     :spot (get-in resp [:stock :c])}))

(defn fetch-stockoption-price-stub [_ _]
  {:price 10.2
   :spot 30.4})

(def demo (partial fetch-stockoption-price "http://localhost:8082"))