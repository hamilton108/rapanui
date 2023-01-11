(ns rapanui.common
  (:import
   (org.apache.ibatis.session SqlSession)
   (critter.util MyBatisUtil)
   [java.time LocalTime]))

;(def ^:dynamic *test-run*)

;(def ^:dynamic *factory*)

(defn str->date [arg]
  (if-let [v (re-find #"(\d+):(\d+)" arg)]
    (let [hours (nth v 1)
          minutes (nth v 2)]
      (println (str hours " " minutes))
      (LocalTime/of (read-string hours) (read-string minutes)))
    nil))


(defmacro defcurried [fname bindings & body]
  (let [n# (count bindings)]
    `(def ~fname (fn [& args#]
                   (if (< (count args#) ~n#)
                     (apply partial ~fname args#)
                     (let [fn# (fn ~bindings ~@body)]
                       (apply fn# args#)))))))
;(defcurried add [a b c d] 
;  (+ a b c d)) 
;(add 1)

(def not-nil? (comp not nil?))

(defmacro with-session [mapper & body]
  `(let [factory# (MyBatisUtil/getFactory)
         session# ^SqlSession (.openSession factory#)
         ~'it (.getMapper session# ~mapper)]
     (try
       (let [result# ~@body]
         result#)
       (finally
         (if (not-nil? session#)
           (do
             (prn "Closing session")
             (doto session# .commit .close)))))))

(defmacro nif-let
  "Nested if-let. If all bindings are non-nil, execute body in the context of
  those bindings.  If a binding is nil, evaluate its `else-expr` form and stop
  there.  `else-expr` is otherwise not evaluated.

  bindings* => binding-form else-expr"
  [bindings & body]
  (cond
    (= (count bindings) 0) `(do ~@body)
    (symbol? (bindings 0)) `(if-let ~(subvec bindings 0 2)
                              (nif-let ~(subvec bindings 3) ~@body)
                              ~(bindings 2))
    :else (throw (IllegalArgumentException. "symbols only in bindings"))))
