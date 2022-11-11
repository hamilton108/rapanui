(ns rapanui.sellrules.accrule
  (:import
   [critter.critterrule Critter AcceptRule DenyRule])
  ;(:use
  ;  [clojure.core.match :only (match)])
  (:require
   [clojure.core.match :refer [match]]
   ;[rapanui.service.logservice :as LOG]
   [rapanui.ports :as R]))

(defn pass [^AcceptRule this args]
  (let [rtyp (.getRtyp this)
        rule-value (.getAccValue this)
        dfw R/DFW
        dfw-pct R/DFW-PCT
        dfb R/DFB
        result (match rtyp
                 dfw     (> (:dfw args) rule-value)
                 dfw-pct (> (:dfw args) rule-value)
                 dfb     (> (:dfb args) rule-value)
                 :else false)]
    ;; (LOG/info (str
    ;;            "[Accept rule " (.getOid this) "] rtyp: " rtyp
    ;;            ", rule value: " rule-value
    ;;            ", dfw: " (:dfw args)
    ;;            ", dfb: " (:dfb args)
    ;;            ", result: " result))
    result))

