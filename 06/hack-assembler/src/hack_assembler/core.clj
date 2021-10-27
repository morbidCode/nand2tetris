(ns hack-assembler.core
  (:require [clojure.string :as string]
  [hack-assembler.io :as io]
  [hack-assembler.parser :as parser :refer [parse]]
  [hack-assembler.converter :as converter :refer [convert]]))

  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
(let [{
instructions :instructions
labels :labels
} (parse (io/load file-name))]
(io/write (string/replace file-name ".asm" "/hack") (convert instructions labels))))
