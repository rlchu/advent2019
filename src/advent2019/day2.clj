(ns advent2019.day2
  (:require [clojure.string :as str]))

(def a [1,9,10,3,2,3,11,0,99,30,40,50])

(partition 4 a)

;; a four-tuple ex: (1 9 10 3)
;; an int-code-program ex: [1,9,10,3,2,3,11,0,99,30,40,50])

(defn get-opcode-and-react1
  [four-tuple int-code-program]
  (let [[opcode p1 p2 swap-position] four-tuple
        v1 (nth int-code-program p1)
        v2 (nth int-code-program p2)]
    (case opcode
      1 (assoc int-code-program swap-position (+ v1 v2))
      2 (assoc int-code-program swap-position (* v1 v2)))))

(get-opcode-and-react [1 9 10 3] a)

(get-opcode-and-react [2 3 11 0] (get-opcode-and-react [1 9 10 3] a))

(defn get-opcode-and-react2
  [int-code-program partitioned-icp]
  (let [four-tuple (first partitioned-icp)
        [opcode p1 p2 swap-position] four-tuple
        v1 (nth int-code-program p1)
        v2 (nth int-code-program p2)]
    (case opcode
      1 (assoc int-code-program swap-position (+ v1 v2))
      2 (assoc int-code-program swap-position (* v1 v2))
      99 int-code-program)))
(get-opcode-and-react a (partition 4 a))
