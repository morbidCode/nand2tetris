(ns hack-assembler.parser
  (:require [clojure.string :as string]))

(defn parse [] "fuck")

(defn label?
[str]
(and (string/starts-with? str "(") (string/ends-with? str ")")))

(defn a-instruction?
[chars]
(= (first chars) \@))

(defn comment?
[chars]
(and (= (first chars) \/) (= (fnext chars) \/)))

(defn space? [c] (= c \space))

(defn instruction-end?
[chars]
(or (empty? chars) (comment? chars)))

(defn convert-to-string [transient-chars] (apply str (persistent! transient-chars)))

(defn parse-a-instruction
[instruction]
(loop [orig (next instruction)
result (transient [])]
(if (instruction-end? orig)
{
:type \a
:value (str (convert-to-string result))
}
(let [[first-char & rest-chars] orig]
(recur rest-chars (if (space? first-char) result (conj! result first-char)))))))

(defn parse-c-instruction
[instruction]
(defn split
[]
(defn transient-empty? [transient] (= (count transient) 0))
(defn separator?
[c]
(or (= c \=) (= c \;)))
(loop [orig instruction
result (transient [])
fragment (transient [])]
(if (instruction-end? orig)
(persistent! (if (transient-empty? fragment) result (conj! result (convert-to-string fragment))))
(let [[first-char & rest-chars] orig]
(cond (separator? first-char) (recur rest-chars (conj! (conj! result (convert-to-string fragment)) first-char) (transient []))
(space? first-char) (recur rest-chars result fragment)
:else (recur rest-chars result (conj! fragment first-char)))))))
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
