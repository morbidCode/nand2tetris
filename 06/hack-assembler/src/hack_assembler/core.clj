(ns hack-assembler.core
  (:require [hack-assembler.io :as io])
  (:require [hack-assembler.parser :as parser :refer [parse]]))

  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [lines (io/load-asm (first args))]
(println lines)
))
