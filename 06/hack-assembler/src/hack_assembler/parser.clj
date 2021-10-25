(ns hack-assembler.parser
  (:require [clojure.string :as string])
  (:require [clojure.java.io :as io]))

(defn parse [] "fuck")

(defn label?
[str]
(and (string/starts-with? str "(") (string/ends-with? str ")")))

(defn persistent-string! [transient-chars] (apply str (persistent! transient-chars)))

(defn comment?
[chars]
(and (= (first chars) \/) (= (fnext chars) \/)))

(defn a-instruction?
[chars]
(= (first chars) \@))

(defn parse-a-instruction
[instruction]
{
:type \a
:value (apply str (next instruction))
})

(defn parse-c-instruction
[instruction]
(defn split
[]
(defn transient-empty? [transient] (= (count transient) 0))
(defn separator?
[char]
(or (= char \=) (= char \;)))
(loop [orig instruction
result (transient [])
fragment (transient [])]
(if (empty? orig)
(persistent! (if (transient-empty? fragment) result (conj! result (persistent-string! fragment))))
(let [[first-char & rest-chars] orig]
(if (separator? first-char)
(recur rest-chars (conj! (conj! result (persistent-string! fragment)) first-char) (transient []))
(recur rest-chars result (conj! fragment first-char)))))))
(let [instruction-vec (split)
fragment-count (count instruction-vec)
separator1 (get instruction-vec 1)
separator2 (get instruction-vec 3)]
{
:type \c
:dest (if (= separator1 \=) (get instruction-vec 0) "NULL")
:comp (if (= separator1 \=) (get instruction-vec 2) (get instruction-vec 0))
:jump (cond (= separator1 \;) (get instruction-vec 2)
(= separator2 \;) (get instruction-vec 4)
:else "NULL")
}))

(defn load-asm-file 
[file-name]
(defn ignore?
[chars]
(or (empty? chars) (comment? chars)))
(defn space? [char] (= char \space))
(def clean-lines (map #(loop [orig %
result (transient [])]
(if (ignore? orig)
(persistent-string! result)
(let [[first-char & rest-chars] orig]
(recur rest-chars (if (space? first-char) result (conj! result first-char))))))))
(def filter-lines (remove #(ignore? %)))
(with-open [rdr (io/reader file-name)]
(into [] (comp clean-lines filter-lines) (line-seq  rdr))))
