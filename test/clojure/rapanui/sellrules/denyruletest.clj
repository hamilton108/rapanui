(ns rapanui.sellrules.denyruletest
  (:require
   [clojure.test :refer [deftest is]]
   [rapanui.sellrules.denyrule :as acc]
   [rapanui.protocols.rules :as R]))

(deftest test-deny-roof
  (let [x 3]
    (is (= x 2))))