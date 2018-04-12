(ns rapanui.sellrules.denyrule
  (:import [critterrepos.beans.critters CritterBean AcceptRuleBean DenyRuleBean])
  ;(:use [clojure.core.match :only (match)])
  (:require
    [rapanui.service.logservice :as LOG]
    [rapanui.protocols.rules :as R]))

(def memory-status (atom {}))

(defn get-curval [^DenyRuleBean rule args]
  (let [rtyp (.getRtyp rule)
        sp-floor R/SP-FLOOR
        sp-roof R/SP-ROOF
        op-floor R/OP-FLOOR
        op-roof R/OP-ROOF]
    (cond
      (= rtyp sp-floor) (:spot args)
      (= rtyp sp-roof) (:spot args)
      (= rtyp op-floor) (:opx args)
      (= rtyp op-roof) (:opx args)
      :default nil)))

(defn block [^DenyRuleBean this args]
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
      (let [result (cond
                     (= rtyp sp-floor) (> cur-val rule-value)
                     (= rtyp sp-roof) (< cur-val rule-value)
                     (= rtyp op-floor) (> cur-val rule-value)
                     (= rtyp op-roof) (< cur-val rule-value)
                     :default false)]
        (LOG/info (str
                    "[Deny Rule " (.getOid this) "] rtyp: " rtyp
                    ", rule value: " rule-value
                    ", cur value: " cur-val
                    ", result: " result))
        (if (and (= has-memory true) (= result true))
          (reset! memory-status (assoc @memory-status (.getOid this) 1)))
        result))))



