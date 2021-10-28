(ns hack-assembler.io
  (:require [clojure.java.io :as file-io]
            [clojure.string :as string]
            [hack-assembler.utils :as utils]))

(defn read-asm-file [file-name]
  (defn comment? [string]
    (and (= (first string) \/) (= (fnext string) \/)))
  (defn ignore? [string]
    (or (empty? string) (comment? string)))
  (defn space? [c]
    (= c \space))
  (def clean-lines (map #(loop [orig %
                                result (transient [])]
                           (if (ignore? orig)
                             (utils/persistent-string! result)
                             (let [[first-char & rest-chars] orig]
                               (recur rest-chars (if (space? first-char) result (conj! result first-char))))))))
  (def filter-lines (remove #(ignore? %)))
  (with-open [rdr (file-io/reader file-name)]
    (into [] (comp clean-lines filter-lines) (line-seq  rdr))))

(defn write-hack-file [file-name data]
  (with-open [w (file-io/writer file-name)]
    (.write w (str (string/join "\n" data) "\n"))))
