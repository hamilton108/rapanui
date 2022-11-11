(ns rapanui.sellrules.accruletest
  (:require
   [clojure.test :refer :all]
   [rapanui.sellrules.accrule :as acc]
   [rapanui.protocols.rules :as R]))



(deftest yar-stock-price-should-not-be-nil
  (let [x 1]
    (is (= x 1))))