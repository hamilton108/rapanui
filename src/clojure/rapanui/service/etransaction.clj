(ns rapanui.service.etransaction
  (:import
    [com.gargoylesoftware.htmlunit Page WebClient]
    [com.gargoylesoftware.htmlunit.html
      HtmlPage HtmlForm HtmlSubmitInput HtmlTextInput HtmlRadioButtonInput HtmlPasswordInput HtmlAnchor]
    [oahu.financial Derivative]
    [oahu.financial.html
     EtradeDownloader]
    [critterrepos.beans.options OptionPurchaseBean]
    [critterrepos.beans.critters CritterBean])
  (:require
    [rapanui.protocols.rules :as R]
    [rapanui.service.logservice :as LOG]
    [rapanui.common :as COM]))

;(def ^:dynamic *login*)
;(def ^:dynamic *logout*)
;(def ^:dynamic *get-order-page*)
;(def ^:dynamic *confirm-transaction*)
(def ^:dynamic *sell-option*)
(def ^:dynamic *logon-params*)

(defn memoize2 [f]
  (let [mem (atom {})]
    (with-meta
      (fn [& args]
        (if-let [e (find @mem args)]
          (val e)
          (let [ret (apply f args)]
            (swap! mem assoc args ret)
            ret)))
      {:mematom mem})))

(def login
  (memoize2
    (fn []
      (if (.isPresent *logon-params*)
        (let [prm (.get *logon-params*)]
          (LOG/info "Logging in for the first time...")
          (let [client (WebClient.)
                page (.getPage client "https://bang.netfonds.no/auth.php")
                form (.getFormByName page "login")
                button (.getInputByName form "confirm")
                customer (.getInputByName form "customer")
                password (.getInputByName form "password")]
            (.setValueAttribute customer (.first prm))
            (.setValueAttribute password (.second prm))
            (let [loginPage (.click button)]
              [client loginPage])))))))

(defn logout []
  (let [mem (:mematom (meta login))]
    (if-not (empty? @mem)
      (let [[_ login-page] (login)
            logout (.getAnchorByHref login-page "https://www.netfonds.no/account/logout.php")]
        (LOG/info (str "Logging out from ..." login-page))
        (reset! mem {})
        (.click logout)))))

(defn get-inputs [page is-sell]
  (let [;page (get-order-page client opx)
        form ^HtmlForm (.getElementById page "order_form")
        radio ^HtmlRadioButtonInput
          (first
            (filter #(= (.getAttribute % "value") "limit")
              (.getInputsByName form (if is-sell "skind" "bkind"))))
        limit ^HtmlTextInput (.getInputByName form (if is-sell "slimit" "blimit"))
        amount ^HtmlTextInput (.getInputByName form (if is-sell "snumber" "bnumber"))
        button ^HtmlSubmitInput (.getInputByName form (if is-sell "sell" "buy"))]
    {:form form
     :radio radio
     :amount amount
     :limit limit
     :button button}))

(defn confirm-transaction [page]
  (let [order-form ^HtmlForm (.getElementById page "order_form")
        order-button ^HtmlSubmitInput (.getInputByName order-form "confirm")]
    (.click order-button)))

(defn get-order-page [^String opname]
  (let [[^WebClient client, _] (login)
        url (str "https://www.netfonds.no/account/order.php?paper=" opname ".OMFE")]
    (.getPage client url)))

(defn get-check-calc-page [^String opname
                           ^double price
                           ^double volume
                           is-sell]
  (let [page (get-order-page opname)
        inp (get-inputs page is-sell)]
    (.setChecked   (:radio inp) true)
    (.setAttribute (:amount inp) "value" (str volume))
    (.setAttribute (:limit inp) "value" (str price))
    (.click (:button inp))))


(defn test-sell-option [^OptionPurchaseBean opx
                        ^CritterBean critter]
  (let [opname (.getOptionName opx)
        d (.getDerivativePrice opx)
        price (-> d .get .getBuy)
        volume (.getSellVolume critter)]
        ;page (get-check-calc-page opname price volume true)]
    (LOG/warn
      (str "[sell-option] Test run mode, will not execute transaction for ticker: " (.getTicker opx)
        ", " (R/whoami critter)))
    (logout)))

(defn sell-option [^OptionPurchaseBean opx
                   ^CritterBean critter]
  (let [opname (.getOptionName opx)
        d (.getDerivativePrice opx)
        price (-> d .get .getBuy)
        volume (.getSellVolume critter)
        page (get-check-calc-page opname price volume true)]
    (confirm-transaction page)
    (logout)))

(defn buy-option [^String opname
                  ^double price
                  ^double volume]
  (let [page (get-check-calc-page opname price volume false)]
    (confirm-transaction page)
    (logout)))

;(defmacro std-binding [& body])
