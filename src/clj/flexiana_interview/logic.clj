(ns flexiana-interview.logic)

(def charmap (atom nil))

(defn available?
  [charmap letter]
  (and (contains? @charmap letter)
       (> (@charmap letter) 0)))

(defn scramble?
  ([word]
   (if (empty? word)  ;; we are at the end of the word
     true
     (let [letter (first word)]
       (if (not (available? charmap letter))
         false        ;; we won't go any further, found a missing letter
         (do          ;; change state and recur
           (reset! charmap (update @charmap letter dec))
           (recur (rest word)))))))
  ([charlist word]    ;; initial round: we need to populate the `charmap` first
   (if (or (empty? charlist) (empty? word))
     false            ;; invalid arguments, early return
     (do
       (reset! charmap (frequencies charlist))
       (scramble? word)))))
