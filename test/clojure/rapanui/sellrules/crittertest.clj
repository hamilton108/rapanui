(ns rapanui.sellrules.crittertest
  (:require
   [clojure.test :refer [deftest is]])
  (:import
   (critter.critterrule AcceptRule DenyRule Critter)))


(deftest dfb-should-pass  
  (let [rule (AcceptRule. 1 3.0 7 "y")
        args {:dfb 3.2}]
    (is (= true (acc/pass rule args)))))