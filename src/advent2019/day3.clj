(ns advent2019.day3)

(defn U-points [[x y] distance]
  (for [y-prime (range y (+ (+ 1 y) distance))] [x y-prime]))

(defn R-points [[x y] distance]
  (for [x-prime (range x (+ (+ 1 x) distance))] [x-prime y]))

(defn L-points [[x y] distance]
  (for [x-prime (reverse (range (- x distance) x))] [x-prime y]))

(defn D-points [[x y] distance]
  (for [y-prime (reverse (range (- y distance) y))] [x y-prime]))

(defn extract-distance [code]
  (Integer. (apply str (drop 1 code))))

;; cond is likely faster than multimethod ? 
(defmulti make-wires (fn [code _] (first code)))

(defmethod make-wires \R
  [code point]
  (R-points point (extract-distance code)))
(defmethod make-wires \L
  [code point]
  (L-points point (extract-distance code)))
(defmethod make-wires \U
  [code point]
  (U-points point (extract-distance code)))
(defmethod make-wires \D
  [code point]
  (D-points point (extract-distance code)))

(defn form-full-wire-from-codes [coll code]
  (apply conj coll (vec (make-wires code (last coll)))))

(defn manhattan-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(def data
  (->> "src/advent2019/data/day3.txt"
       slurp
       clojure.string/split-lines))

(def data-1 (clojure.string/split (first data) #","))
(def data-2 (clojure.string/split (last data) #","))

(def wire-1
  (set (reduce form-full-wire-from-codes [[0 0]] data-1)))

(def wire-2
  (set (reduce form-full-wire-from-codes [[0 0]] data-2)))

(defn answer-1 []
  (->> (clojure.set/intersection wire-1 wire-2)
       (group-by manhattan-distance)
       sort
       rest
       ffirst))

(answer-1)
; => 258
