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
  (for [x-prime (range x (+ (+ 1 x) distance))] [(- 0 x-prime) y]))

(L-points [0 0] 5)
; => ([0 0] [-1 0] [-2 0] [-3 0] [-4 0] [-5 0])

(defn D-points [[x y] distance]
  (for [y-prime (range y (+ (+ 1 y) distance))] [x (- 0 y-prime)]))

(D-points [0 0] 5)
; => ([0 0] [0 -1] [0 -2] [0 -3] [0 -4] [0 -5])

;; we now want a function that takes in an instruction (say "R8")
;; and calls (dispatches) the proper function above ^^
;; seems a good chance to use multimethods:
;; https://www.braveclojure.com/multimethods-records-protocols/
(defmulti make-wires (fn [code _] (first code)))
(defmethod make-wires \R
  [code point]
  (R-points point (Character/digit (last code) 10)))

(make-wires "R8" [1 1])

; => ([1 1] [2 1] [3 1] [4 1] [5 1] [6 1] [7 1] [8 1] [9 1])
;; oh hm at the repl is there a gotcha in multimethod redefinition?
;; => https://clojuredocs.org/clojure.core/defmulti#example-55d9e498e4b0831e02cddf1b

;; could put the specific X-points definitions in our multimethods, but sometimes
;; a little repetition is more clear than over-abstraction
