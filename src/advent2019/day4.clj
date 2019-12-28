(ns advent2019.day4)

(defn int->by->decimal [int]
  (map #(Character/digit % 10) (str int)))

(def data (map int->by->decimal (range 245318 765748)))

(first data)

(defn contains-duplicate-pair? [password]
  (not= (count password) (count (partition-by identity password))))

(defn monotonic-reducer [col item]
  (if ((fnil > 0) (last col) item)
    (reduced false)
    (conj col item)))

(defn digits-monotonic? [password]
  (reduce monotonic-reducer [] password))

(->> data
     (filter contains-duplicate-pair?)
     (filter digits-monotonic?)
     count)

(count (sequence (comp (map int->by->decimal)
                       (filter contains-duplicate-pair?)
                       (filter digits-monotonic?)) (range 245318 765748)))

(some #(> (count %) 2) (partition-by identity (list 1 3 3 3 4 4 5)))
