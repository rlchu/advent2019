(ns advent2019.day2
  (:require [clojure.string :as str]))

;; why chose to recur instead of reduce

;;; want to do the part about not using 'let' as your data
;; transformation pipeline and then using #p to debug

(def data [1,9,10,3,2,3,11,0,99,30,40,50])

(def index 0)

(nth (partition 4 data) index)

; (defn check-opcode [fragment]
;   (let [[opcode position1 position2 swap-position] fragment
;         p1-value (nth data position1)
;         p2-value (nth data position2)]
;     (case opcode
;       1 "1"
;       2 "2")))

; (defn check-opcode [fragment]
;   (let [[opcode position1 position2 swap-position] fragment
;         p1-value (nth a position1)
;         p2-value (nth a position2)]
;     (case opcode
;       1 (assoc data swap-position (+ p1-value p2-value))
;       2 (assoc data swap-position (* p1-value p2-value)))))

(check-opcode data 0)

(defn- opcode-case-and-swap [opcode data swap-position p1-value p2-value]
  (case opcode
    1 (assoc data swap-position (+ p1-value p2-value))
     ;; note about assoc for the data swap in vector ^^
    2 (assoc data swap-position (* p1-value p2-value))))

(defn check-opcode [data index]
  (let [fragment #p (nth (partition 4 data) index)
        [opcode position1 position2 swap-position] fragment
        p1-value #p (nth data position1)
        p2-value #p (nth data position2)
        swapped-data (opcode-case-and-swap opcode data swap-position p1-value p2-value)]
    (when (not= 99 opcode)  ;; oops no when get recur error
      swapped-data
      (recur data (inc index)))))

;; notes p2

; screams list comprehension
;
;
; (defn attempt-thru-lc []
;   (first (drop-while (fn [[x y]] (not= 19690720 (compute x y data))) lc)))
; ;; => [64 17]
;
; attempted take-whiel first
