(ns rapanui.sellrules.accruletest
  (:import
   [critter.critterrule  AcceptRule])
  (:require
   [clojure.test :refer :all]
   [rapanui.sellrules.accrule :as acc]
   [rapanui.protocols.rules :as R]))

(deftest dfb-should-pass  
  (let [rule (AcceptRule. 1 3.0 7 "y")
        args {:dfb 3.2}]
    (is (= true (acc/pass rule args)))))