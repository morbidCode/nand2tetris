(ns hack-assembler.converter
  (:require [clojure.string :as string]))

(defn convert-to-binary
[x]
(let [binary-string (Integer/toBinaryString x)
bits-count 15
padding (.repeat "0" (- bits-count (count binary-string)))]
(str padding binary-string)))
