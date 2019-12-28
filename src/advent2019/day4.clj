(ns advent2019.day4)

;; https://stackoverflow.com/questions/29929325/how-to-split-a-number-in-clojure
(defn digits [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn contains-duplicate-pair? [password]
  (some #(<= 2 (count %)) (partition-by identity password)))

(defn monotonic-reducer [col item]
  (if ((fnil > 0) (last col) item)
    (reduced false)
    (conj col item)))

(defn digits-monotonic? [password]
  (reduce monotonic-reducer [] password))

(defn answer-1 []
  (count (sequence (comp (map digits)
                         (filter digits-monotonic?)
                         (filter contains-duplicate-pair?))
                   (range 245318 765748))))

(answer-1)
; => 1079

(defn contains-only-tuple-duplicate-pair? [password]
  (some #(= 2 (count %)) (partition-by identity password)))

(defn answer-2 []
  (count (sequence (comp (map digits)
                         (filter digits-monotonic?)
                         (filter contains-only-tuple-duplicate-pair?))
                   (range 245318 765748))))

(answer-2)
; => 699
