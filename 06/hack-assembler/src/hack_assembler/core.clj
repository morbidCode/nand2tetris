(ns hack-assembler.core
  (:require [clojure.string :as string]
  [hack-assembler.io :as io]
  [hack-assembler.parser :as parser :refer [parse]]
  [hack-assembler.translator :as translator :refer [translate]])
  (:gen-class))

(defn -main [& args]
(let [file-name (first args)
{
instructions :instructions
labels :labels
} (parse (io/read-asm-file file-name))]
(io/write-hack-file (string/replace file-name ".asm" ".hack") (translate instructions labels))))
