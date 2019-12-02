(ns advent2019.day1
  (:require [clojure.string :as str]))

(def data
  (->> "src/advent2019/data/day1.txt"
       slurp
       (str/split-lines)
       (map #(Integer/parseInt %))))

(defn fuel-needed [mass]
  (- (Math/floor (/ mass 3)) 2))

(defn fuel-with-additional-fuels [mass]
  (->> (fuel-needed mass)     ;; calculate fuel-needed pt1 style
       (iterate fuel-needed)  ;; repeatedly call fuel-needed on itself lazily
       (take-while pos?)      ;; take from this sequence ^ while result positive
       (reduce +)))           ;; add results

;; part one:
(reduce + (map fuel-needed data))
; => 3262991.0

;; part two:
(reduce + (map fuel-with-additional-fuels data))
; => 4891620.0
