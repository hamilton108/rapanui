(ns rapanui.sellrules.accrule
  (:import
    [critterrepos.beans.critters CritterBean AcceptRuleBean DenyRuleBean])
  ;(:use
  ;  [clojure.core.match :only (match)])
  (:require
    [rapanui.service.logservice :as LOG]
    [rapanui.protocols.rules :as R]))

(defn pass [^AcceptRuleBean this args]
  (let [rtyp (.getRtyp this)
        rule-value (.getAccValue this)
        dfw R/DFW
        dfw-pct R/DFW-PCT
        dfb R/DFB
        result (cond
                 (= rtyp dfw) (> (:dfw args) rule-value)
                 (= rtyp dfw-pct) (> (:dfw args) rule-value)
                 (= rtyp dfb) (> (:dfb args) rule-value)
                 :default false)]
        ;result (match rtyp
        ;          dfw     (> (:dfw args) rule-value)
        ;          dfw-pct (> (:dfw args) rule-value)
        ;          dfb     (> (:dfb args) rule-value)
        ;          :else false)]
    (LOG/info (str
                "[Accept rule " (.getOid this) "] rtyp: " rtyp
                ", rule value: " rule-value
                ", dfw: " (:dfw args)
                ", dfb: " (:dfb args)
                ", result: " result))
    result))

