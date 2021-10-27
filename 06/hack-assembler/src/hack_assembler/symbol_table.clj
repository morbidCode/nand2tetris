(ns hack-assembler.symbol-table)

(defn make-table [labels]
(let [predefined-symbols {
"R0" 0
"SP" 0
"R1" 1
"LCL" 1
"R2" 2
"ARG" 2
"R3" 3
"THIS" 3
"R4" 4
"THAT" 4
"R5" 5
"R6" 6
"R7" 7
"R8" 8
"R9" 9
"R10" 10
"R11" 11
"R12" 12
"R13" 13
"R14" 14
"R15" 15
"SCREEN" 16384
"KBD" 24576
}
symbols (atom (into predefined-symbols labels))
register-counter (atom 15)]
#(let [value (get @symbols %)]
(if value
value
(do (swap! register-counter inc)
(swap! symbols assoc % @register-counter)
@register-counter)))))
