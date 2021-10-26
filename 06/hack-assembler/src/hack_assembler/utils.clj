(ns hack-assembler.utils)

(defn persistent-string!
[transient-chars]
(apply str (persistent! transient-chars)))
