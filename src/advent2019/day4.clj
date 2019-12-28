(ns advent2019.day4)

(map identity "111234")

(reduce #(if #p (= (last %1) %2) %1 (conj %1 %2)) [] "123445")

;; break the reduction with true when two equal items are found:
(reduce #(if (= (last %1) %2) (reduced "true") (conj %1 %2)) [] "12345")
; => true

(defn password-predicate [col item]
  (and (= (last col) item) (< (item col) item)))

(reduce #(if (password-predicate %1 %2) %1 (conj %1 %2)) [] "12345")

(partition-by identity "12345")

(map #(Character/digit % 10) "1234")
