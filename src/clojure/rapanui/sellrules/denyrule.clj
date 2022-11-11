(ns rapanui.sellrules.denyrule
  (:import [critter.critterrules Critter AcceptRule DenyRule])
  ;(:use [clojure.core.match :only (match)])
  (:require
   [clojure.core.match :refer [match]]
    ;[rapanui.service.logservice :as LOG]
   [rapanui.ports :as R]))

(def memory-status (atom {}))

(defn get-curval [^DenyRule rule args]
  (let [rtyp (.getRtyp rule)
        sp-floor R/SP-FLOOR
        sp-roof R/SP-ROOF
        op-floor R/OP-FLOOR
        op-roof R/OP-ROOF]
    (match rtyp
      sp-floor (:spot args)
      sp-roof (:spot args)
      op-floor (:opx args)
      op-roof (:opx args)
      :else nil)))

(defn block [^DenyRule this args]
  (if-let [blocked (@memory-status (.getOid this))]
    true
    (let [rtyp (.getRtyp this)
          rule-value (.getDenyValue this)
          cur-val (get-curval this args)
          has-memory (= (.getMemory this) "y")
          sp-floor R/SP-FLOOR
          sp-roof R/SP-ROOF
          op-floor R/OP-FLOOR
          op-roof R/OP-ROOF]
      (let [result (match rtyp
                     sp-floor (> cur-val rule-value)
                     sp-roof  (< cur-val rule-value)
                     op-floor  (> cur-val rule-value)
                     op-roof  (< cur-val rule-value)
                     :else false)]
        ;; (LOG/info (str
        ;;            "[Deny Rule " (.getOid this) "] rtyp: " rtyp
        ;;            ", rule value: " rule-value
        ;;            ", cur value: " cur-val
        ;;            ", result: " result))
        (if (and (= has-memory true) (= result true))
          (reset! memory-status (assoc @memory-status (.getOid this) 1))
          nil)
        result)
      nil)))



