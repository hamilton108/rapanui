(ns taiarapu.testsellrules
  (:require [clojure.test :as T])
  (:require
    (taiarapu.protocols [rules :as R])
    (taiarapu.sellrules
      [accrule :as A]
      [denyrule :as D]))
  (:import 
    [critterrepos.beans.options
      OptionPurchaseBean]
    [critterrepos.beans.critters
      DenyRuleBean
      AcceptRuleBean
      GradientRuleBean
      CritterBean]))

(let [d (DenyRuleBean. 1 100.0 R/SP-FLOOR "y" "n")
      args {:spot 100.2}
      args2 {:spot 99.9}]
  (T/is (= true (D/block d args)))
  (T/is (= false (D/block d args2))))

(let [d (DenyRuleBean. 2 100.0 R/SP-ROOF "y" "n")
      args {:spot 100.2}
      args2 {:spot 99.9}]
  (T/is (= false (D/block d args)))
  (T/is (= true (D/block d args2))))

(let [d (DenyRuleBean. 3 12.0 R/OP-FLOOR "y" "n")
      args {:opx 11.9}
      args2 {:opx 12.1}]
  (T/is (= false (D/block d args)))
  (T/is (= true (D/block d args2))))

(let [d (DenyRuleBean. 4 12.0 R/OP-ROOF "y" "n")
      args {:opx 11.9}
      args2 {:opx 12.1}]
  (T/is (= true (D/block d args)))
  (T/is (= false (D/block d args2))))

(let [d (DenyRuleBean. 5 12.0 R/OP-ROOF "y" "y")
      args {:opx 11.9}
      args2 {:opx 12.1}]
  (T/is (= true (D/block d args)))
  (T/is (= true (D/block d args2))))

(let [a (AcceptRuleBean. 6 2.0 R/DFW)
      args  {:dfw 1.9}
      args2 {:dfw 2.1}]
  (T/is (= false (A/pass a args)))
  (T/is (= true (A/pass a args2))))

(let [a (AcceptRuleBean. 7 2.0 R/DFB)
      args  {:dfb 1.9}
      args2 {:dfb 2.1}]
  (T/is (= false (A/pass a args)))
  (T/is (= true (A/pass a args2))))
