(ns rapanui.purchase)

;; (extend-protocol R/IDenyRule
;;   DenyRuleBean
;;   (whoamDny [^DenyRuleBean this]
;;     (str "[Deny rule " (.getOid this) "] rtyp: " (.getRtyp this) " " (.getRtypDesc this) ", value: " (.getDenyValue this)))
;;   (block [this args]
;;     (DNY/block this args)))

;; (extend-protocol R/IAcceptRule
;;   AcceptRuleBean
;;   (whoamAcc [^AcceptRuleBean this]
;;     (str "[Acc.rule " (.getOid this) "] rtyp: " (.getRtyp this) " " (.getRtypDesc this) ", value: " (.getAccValue this)))
;;   (pass [this args]
;;     (let [dny-rules (.getDenyRules this)
;;           dny-results (map #(R/block % args) dny-rules)] ;(map (partial check-deny-rule args) dny-rules)]

;;       (if (some true? dny-results)
;;         false
;;         (ACC/pass this args)))))

;; (extend-protocol R/ICritter
;;   CritterBean
;;   (strutMyStuff [^CritteBean this
;;                  ^String prefix]
;;     (println (str prefix (R/whoami this)))
;;     (let [acc-rules (.getAcceptRules this)]
;;       (doseq [acc acc-rules]
;;         (println (str prefix "    " (R/whoamAcc acc)))
;;         (let [dny-rules (.getDenyRules acc)]
;;           (doseq [dny dny-rules]
;;             (println (str prefix "         " (R/whoamDny dny))))))))

;;   (applyRules [^CritteBean this args]
;;     (let [acc-rules (.getAcceptRules this)
;;           acc-results (map #(R/pass % args) acc-rules)]
;;       (some true? acc-results)))
;;   (whoami [^CritterBean this]
;;     (str "[Critter " (.getOid this) "] name: " (.getName this))))


;; (defn collect-args [^OptionPurchaseBean bean]
;;   (T/nif-let
;;    [cur-dx (.getDerivativePrice bean) :cur-dx
;;     cur-d (if (.isPresent cur-dx)
;;             (.get cur-dx)
;;             nil) :cur-d
;;     cur-wm (.getWatermark bean) :cur-vm
;;     d-name (.getOptionName bean) :d-name
;;     d-type (.getOptionType bean) :d-type
;;     ticker (.getTicker bean) :ticker
;;     cur-spotx (.getSpot bean) :cur-spotx
;;     cur-spot (if (.isPresent cur-spotx)
;;                (.get cur-spotx)
;;                nil) :cur-spot
;;     cur-buy (.getBuy cur-d) :cur-buy
;;     price (.getPrice bean) :price]
;;    (do
;;      (LOG/info (str "[" d-name "] current buy: " cur-buy ", watermark: " cur-wm ", price: " price))
;;      {:dfb (- price cur-buy)
;;       :dfw (- cur-wm cur-buy)
;;       :spot (.getCls cur-spot)
;;       :opx price})))

;; (defn apply-critter-args [this critters args]
;;   (doseq [cr critters]
;;     (if (= true (R/applyRules cr args))
;;       (do
;;         (ET/*sell-option* this cr)
;;         (MAIL/send-email "Options sale" (R/whoami cr))
;;         (DB/*register-critter-sale* this cr)))))

;; (extend-protocol D/IPurchase
;;   OptionPurchaseBean
;;   (strutMyStuff [^OptionPurchaseBean this]
;;     (let [critters (.getCritters this)
;;           opname (.getOptionName this)]
;;       (println "Option name: " opname)
;;       (doseq [critter critters]
;;         (R/strutMyStuff critter "   "))))
;;   (whoami [^OptionPurchaseBean this]
;;     (str "[Oid " (.getOid this) "] " (.getOptionName this)))
;;   (applyCritters [^OptionPurchaseBean this]
;;     (let [critters (filter #(= (.getStatus %) 7) (.getCritters this))]
;;       (if (empty? critters)
;;         (LOG/warn (str (D/whoami this) " was empty of critters!"))
;;         (try
;;           (let [args (collect-args this)]
;;             (if (keyword? args)
;;               (LOG/warn (str args))
;;               (apply-critter-args this critters args)))
;;           (catch Exception ex (LOG/error (str (.getMessage ex) " : " (join " " (.getStackTrace ex))))))))))
