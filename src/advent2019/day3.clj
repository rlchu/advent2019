(ns advent2019.day3)

(defn U-points [[x y] ^Integer distance]
  (for [y-prime (range y (+ (+ 1 y) distance))] [x y-prime]))

(defn R-points [[x y] ^Integer distance]
  (for [x-prime (range x (+ (+ 1 x) distance))] [x-prime y]))

(defn L-points [[x y] ^Integer distance]
  (for [x-prime (reverse (range (- x distance) x))] [x-prime y]))

(defn D-points [[x y] ^Integer distance]
  (for [y-prime (reverse (range (- y distance) y))] [x y-prime]))

(defn extract-distance [^String code]
  (Integer. (apply str (drop 1 code))))

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

; (defn make-wires [code point]
;   (let [direction (first code)
;         distance (extract-distance code)]
;     (case direction
;       \R (R-points point distance)
;       \L (L-points point distance)
;       \U (U-points point distance)
;       \D (D-points point distance))))

(defn form-full-wire-from-codes [coll ^String code]
  (apply conj coll (vec (make-wires code (last coll)))))

(defn manhattan-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))

(def data
  (->> "src/advent2019/data/day3.txt"
       slurp
       clojure.string/split-lines))

(def data-1 (clojure.string/split (first data) #","))
(def data-2 (clojure.string/split (last data) #","))

(defn wire-1 []
  (reduce form-full-wire-from-codes [[0 0]] data-1))

(defn wire-2 []
  (reduce form-full-wire-from-codes [[0 0]] data-2))

(defn answer-1 []
  (->> (clojure.set/intersection (set (wire-1)) (set (wire-2)))
       (group-by manhattan-distance)
       sort
       rest
       ffirst))

(answer-1)
; => 258

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; part 2:

; (def data-1 ["R8" "U5" "L5" "D3"])
; (def data-2 ["U7" "R6" "D4" "L4"])

(def wire-1
  (dedupe (reduce form-full-wire-from-codes [[0 0]] data-1)))
(def wire-2
  (dedupe (reduce form-full-wire-from-codes [[0 0]] data-2)))

(def intersections
  (rest (clojure.set/intersection (set wire-1) (set wire-2))))

;;;

(def int-indeces-1 (map (juxt #(.indexOf wire-1 %) identity) intersections))
(def int-indeces-2 (map (juxt #(.indexOf wire-2 %) identity) intersections))

(->>
 (map #(identity [(+ (first %1) (first %2)) (last %1) (last %2)]) int-indeces-1 int-indeces-2)
 sort
 rest
 ffirst)
;=> 12304
