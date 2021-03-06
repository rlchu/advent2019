(ns advent2019.day2)

;; https://adventofcode.com/2019/day/2
;; data with 'position 1 and 2 replaced' as per instructions
(def data [1,12,2,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,9,19,1,19,5,23,2,23,13,27,1,10,27,31,2,31,6,35,1,5,35,39,1,39,10,43,2,9,43,47,1,47,5,51,2,51,9,55,1,13,55,59,1,13,59,63,1,6,63,67,2,13,67,71,1,10,71,75,2,13,75,79,1,5,79,83,2,83,9,87,2,87,13,91,1,91,5,95,2,9,95,99,1,99,5,103,1,2,103,107,1,10,107,0,99,2,14,0,0])

(defn- opcode-case-and-swap [opcode data swap-position p1-value p2-value]
  (case opcode
    1 (assoc data swap-position (+ p1-value p2-value))
    2 (assoc data swap-position (* p1-value p2-value))))

(defn- translate-positions-to-values [opcode data p1 p2 swap-position]
  (let [p1-value (nth data p1)
        p2-value (nth data p2)]
    (opcode-case-and-swap opcode data swap-position p1-value p2-value)))

(defn check-opcode [data index]
  (let [fragment (nth (partition 4 data) index)
        [opcode p1 p2 swap-position] fragment]
    (if (not= 99 opcode)
      (recur (translate-positions-to-values opcode data p1 p2 swap-position)
             (inc index))
      data)))

(first (check-opcode data 0))
; => 3895705

;;;; day two part two:
(def data2 [1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,9,19,1,19,5,23,2,23,13,27,1,10,27,31,2,31,6,35,1,5,35,39,1,39,10,43,2,9,43,47,1,47,5,51,2,51,9,55,1,13,55,59,1,13,59,63,1,6,63,67,2,13,67,71,1,10,71,75,2,13,75,79,1,5,79,83,2,83,9,87,2,87,13,91,1,91,5,95,2,9,95,99,1,99,5,103,1,2,103,107,1,10,107,0,99,2,14,0,0])

(defn compute [noun verb data]
  (let [data-w-noun-and-verb (assoc data 1 noun 2 verb)]
    (first (check-opcode data-w-noun-and-verb 0))))

(compute 12 2 data)
; => 3895705 -- answer to part one

;; we'll make a list comprehension below:
(def lc (for [x (range 0 100)
              y (range 0 100)]
          [x y]))

(defn attempt-thru-lc []
  (first (filter (fn [[x y]] (= 19690720 (compute x y data))) lc)))
;; => [64 17]

(defn answer [[x y]] (+ (* x 100) y))

(time (answer (attempt-thru-lc)))
;; => 6417


;; this solution from reddit is much faster :)
;; should always go for the reduce over the recursion :)
;; reducing over the indexes (range 0 (count program) 4))
;; i much prefer over my consistent 'partition' calls :)
; (def INPUT (slurp "src/advent2019/day02.txt"))
;
; (defn make-computer [int-code noun verb]
;   (let [numbers (vec (->> (clojure.string/split int-code #",")
;                           (map read-string)))]
;     (assoc numbers 1 noun 2 verb)))
;
; (defn evolve
;   [stack start]
;   (let [opcode (get stack start)
;         num1 (get stack (get stack (inc start)))
;         num2 (get stack (get stack (+ start 2)))
;         addr (get stack (+ start 3))]
;     (cond (= 1 opcode) (assoc stack addr (+ num1 num2))
;           (= 2 opcode) (assoc stack addr (* num1 num2))
;           (= 99 opcode) (reduced stack))))
;
; (defn execute-program [int-code noun verb]
;   (let [program (make-computer int-code noun verb)]
;     (first (reduce #(evolve %1 %2) program (range 0 (count program) 4)))))
;
; (def solution-1 (execute-program INPUT 12 2))
;
; (def solution-2
;   (let [execs (for [noun (range 100) verb (range 100)]
;                 {:noun noun :verb verb :result (execute-program INPUT noun verb)})
;         exec (first (filter #(= 19690720 (:result %)) execs))]
;     (+ (* (:noun exec) 100) (:verb exec))))
