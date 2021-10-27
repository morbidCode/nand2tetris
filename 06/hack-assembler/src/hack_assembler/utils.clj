(ns hack-assembler.utils)

(defn persistent-string!
[transient-chars]
(apply str (persistent! transient-chars)))

(defn numeric?
[string]
(.matches string "\\d+"))
