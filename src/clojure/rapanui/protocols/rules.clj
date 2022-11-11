(ns rapanui.protocols.rules)

(def  DFW       1)  ;Diff from watermark
(def  DFW-PCT   2)  ; Diff from watermark percent
(def  SP-FLOOR  3)  ; Stock price floor (valid if current stock value above price, i.e. in the house)
(def  SP-ROOF   4)  ; Stock price roof (valid if current stock value below price, i.e. in the house)
(def  OP-FLOOR  5)  ; Option price floor (valid if current option value above price)
(def  OP-ROOF   6)  ; Option price floor (valid if current option value below price)
(def  DFB       7)  ; Diff from bought
(def  DW-SLIDE  9)  ; Diff from watermark slide (gradient rule)
(def  CALL      10)
(def  PUT       11)
(def  SPOT      12)
(def  WM-SPOT   13)  ; Watermark spot
(def  OPTION-PRICE  14)

(defprotocol IDenyRule
  (whoamDny [this])
  (block [this args]))

(defprotocol IAcceptRule
  (whoamAcc [this])
  (pass [this args]))

(defprotocol ICritter
  (strutMyStuff [this prefix])
  (applyRules [this args])
  (whoami [this]))
