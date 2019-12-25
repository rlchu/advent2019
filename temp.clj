(ns advent2019.day3)

;; first want to make the 'wires':
;; given a point and a distance, find the set of points
;; along the 'wire' (assuming just 'U' up for now):

;; that is (U-points [2 5] 3)
;; should return ([2 5] [2 6] [2 7] [2 8])

(defn U-points [[x y] distance]
  (for [y-prime (range y (+ (+ 1 y) distance))] [x y-prime]))

(U-points [2 5] 3)
; => ([2 5] [2 6] [2 7] [2 8])

;; we'll make the R,L,D functions as well:

(defn R-points [[x y] distance]
  (for [x-prime (range x (+ (+ 1 x) distance))] [x-prime y]))

(R-points [1 1] 8)
; => ([1 1] [2 1] [3 1] [4 1] [5 1] [6 1] [7 1] [8 1] [9 1])

(defn L-points [[x y] distance]
  (for [x-prime (reverse (range (- x distance) x))] [x-prime y]))

(L-points [0 0] 5)
; => ([0 0] [-1 0] [-2 0] [-3 0] [-4 0] [-5 0])

(defn D-points [[x y] distance]
  (for [y-prime (reverse (range (- y distance) y))] [x y-prime]))

(D-points [0 0] 5)
; => ([0 0] [0 -1] [0 -2] [0 -3] [0 -4] [0 -5])

;; we now want a function that takes in an instruction (say "R8")
;; and calls (dispatches) the proper function above ^^
;; seems a good chance to use multimethods:
;; https://www.braveclojure.com/multimethods-records-protocols/

(defn extract-distance [code]
  (Integer. (apply str (drop 1 code))))

(extract-distance "R384")
; => 384

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

(make-wires "R8" [1 1])
; => ([1 1] [2 1] [3 1] [4 1] [5 1] [6 1] [7 1] [8 1] [9 1])
(make-wires "U3" [0 0])
; => ([0 0] [0 1] [0 2] [0 3])
;; oh hm at the repl is there a gotcha in multimethod redefinition?
;; => https://clojuredocs.org/clojure.core/defmulti#example-55d9e498e4b0831e02cddf1b

;; could put the specific X-points definitions in our multimethods, but sometimes
;; a little repetition is more clear than over-abstraction

;; next, we want to think up a reducing function
;; utilizing the above, to reduce across our code points
;; to make the full set of wires (starting at 0,0).
;; something like:
;; i like to think of this as the "zero, one, many" technique :)

(reduce form-full-wire-from-codes [0 0] ["R8" "U5" "L5" "D3"])

;; we can start from the simplest case, when our vector is empty:

(reduce form-full-wire-from-codes [0 0] [])

;; then one item:

(reduce form-full-wire-from-codes [0 0] ["R8"])

;; reduce accumulation form reminder:
;; https://clojuredocs.org/clojure.core/reduce#example-5cb7605de4b0ca44402ef70b

;; here would be our zero case:

(defn form-full-wire-from-codes [coll code]
  (when (nil? code) coll))

(reduce form-full-wire-from-codes [0 0] [])
; => [0 0]

;; here would be our zero and one case:
(defn form-full-wire-from-codes [coll code]
  ;; 'coll' here is more like 'point' :)
  (if (nil? code)
    coll
    (make-wires code coll)))

(reduce form-full-wire-from-codes [0 0] [])
; => [0 0]

(reduce form-full-wire-from-codes [0 0] ["R4"])
; => ([0 0] [1 0] [2 0] [3 0] [4 0])

;; for our 'many' case we will need to concat the make-wires result to the coll(ection)
(defn form-full-wire-from-codes [coll code]
  (if (nil? code)  ;; probably no longer need this nil check
    coll
    (concat coll (make-wires code (last coll)))))

(reduce form-full-wire-from-codes [[0 0]] ["R8" "U5" "L5" "D3"])
(reduce form-full-wire-from-codes [[0 0]] ["U7" "R6" "D4" "L4"])

(def a (set (reduce form-full-wire-from-codes [[0 0]] ["R8" "U5" "L5" "D3"])))
(def b (set (reduce form-full-wire-from-codes [[0 0]] ["U7" "R6" "D4" "L4"])))

(clojure.set/intersection a b)

(defn manhattan-distance [[x y]]
  (+ (Math/abs x) (Math/abs y)))
