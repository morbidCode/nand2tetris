(ns hack-assembler.io
  (:require [clojure.java.io :as file-io])
  (:require [hack-assembler.utils :as utils]))

(defn load-asm
[file-name]
(defn comment?
[chars]
(and (= (first chars) \/) (= (fnext chars) \/)))
(defn ignore?
[chars]
(or (empty? chars) (comment? chars)))
(defn space?
[char]
(= char \space))
(def clean-lines (map #(loop [orig %
result (transient [])]
(if (ignore? orig)
(utils/persistent-string! result)
(let [[first-char & rest-chars] orig]
(recur rest-chars (if (space? first-char) result (conj! result first-char))))))))
(def filter-lines (remove #(ignore? %)))
(with-open [rdr (file-io/reader file-name)]
(into [] (comp clean-lines filter-lines) (line-seq  rdr))))