(ns hack-assembler.parser
  (:require [clojure.string :as string])
  (:require [hack-assembler.utils :as utils]))

(defn parse
[lines]
(defn label?
[line]
(and (string/starts-with? line "(") (string/ends-with? line ")")))

(defn a-instruction?
[chars]
(= (first chars) \@))

(defn parse-label
[label]
(subs label 1 (- (count label) 1)))

(defn parse-a-instruction
[instruction]
{
:type \a
:value (subs instruction 1)
})

(defn parse-c-instruction
[instruction]
(defn split
[]
(defn transient-empty?
[transient]
(= (count transient) 0))
(defn separator?
[char]
(or (= char \=) (= char \;)))
(loop [orig instruction
result (transient [])
fragment (transient [])]
(if (empty? orig)
(persistent! (if (transient-empty? fragment) result (conj! result (utils/persistent-string! fragment))))
(let [[first-char & rest-chars] orig]
(if (separator? first-char)
(recur rest-chars (conj! (conj! result (utils/persistent-string! fragment)) first-char) (transient []))
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

(loop [orig lines
labels (transient {})
instructions (transient [])
instruction-number 1]
(if (empty? orig)
{
:labels (persistent! labels)
:instructions (persistent! instructions)
}
(let [[first-line & rest-lines] orig]
(if (label? first-line)
(recur rest-lines (assoc! labels (parse-label first-line) instruction-number) instructions instruction-number)
(recur rest-lines labels (conj! instructions ((if (a-instruction? first-line) parse-a-instruction parse-c-instruction) first-line)) (+ instruction-number 1)))))))
