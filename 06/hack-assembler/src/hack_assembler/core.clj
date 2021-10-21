(ns hack-assembler.core
  (:require [hack-assembler.parser :refer [parse]])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (doall (map println args)))
