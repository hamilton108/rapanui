(ns rapanui.protocol.stockoption)

(defprotocol IPurchase
  (strutMyStuff [this])
  (whoami [this])
  (applyCritters [this]))
