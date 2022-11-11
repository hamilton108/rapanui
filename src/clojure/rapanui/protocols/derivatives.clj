(ns rapanui.protocols.derivatives)

(defprotocol IPurchase
  (strutMyStuff [this])
  (whoami [this])
  (applyCritters [this]))
