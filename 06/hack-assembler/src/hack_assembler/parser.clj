(ns hack-assembler.parser
  (:require [clojure.string :as string]))

(defn parse [] "fuck")

(defn label?
[str]
(and (string/starts-with? str "(") (string/ends-with? str ")")))

(defn a-instruction?
[str]
(string/starts-with? str "@"))

(defn comment?
[char-seq]
(and (= (first char-seq) \/) (= (fnext char-seq) \/)))

(defn parse-c-instruction
[instruction]
(defn split
[]
(defn convert-to-string [transient-chars] (apply str (persistent! transient-chars)))
(defn transient-empty? [transient] (= (count transient) 0))
(defn instruction-separator?
[c]
(or (= c \=) (= c \;)))
(loop [char-seq instruction
result (transient [])
fragment (transient [])]
(if (or (empty? char-seq) (comment? char-seq))
(persistent! (if (transient-empty? fragment) result (conj! result (convert-to-string fragment))))
(let [[first-char & rest-chars] char-seq]
(cond (instruction-separator? first-char) (recur rest-chars (conj! (conj! result (convert-to-string fragment)) first-char) (transient []))
(= first-char \space) (recur rest-chars result fragment)
:else (recur rest-chars result (conj! fragment first-char)))))))
(let [instruction-vec (split)
fragment-count (count instruction-vec)
separator1 (get instruction-vec 1)
separator2 (get instruction-vec 3)]
{
:dest (if (= separator1 \=) (get instruction-vec 0) "NULL")
:comp (if (= separator1 \=) (get instruction-vec 2) (get instruction-vec 0))
:jump (cond (= separator1 \;) (get instruction-vec 2)
(= separator2 \;) (get instruction-vec 4)
:else "NULL")
}))
