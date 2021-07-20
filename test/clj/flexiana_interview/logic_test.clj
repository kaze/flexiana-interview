(ns flexiana-interview.logic-test
  [:require [clojure.test :refer [testing is]]
            [flexiana-interview.logic :refer [available? scramble?]]])

;; data -------------------------------------------------------------------- ;;

(def list1 "rekqodlw")
(def word1 "world")

(def list2 "cedewaraaossoqqyt")
(def word2"codewars")

(def list3 "katas")
(def word3"steak")

(def list4 "bceklnporz")
(def word4 "broken")


;; tests ------------------------------------------------------------------- ;;

(testing "Logic"
  (testing "available?"
    (let [charmap (atom (frequencies list2))]
      (testing "handles `nil` for `word` argument"
        (is (= (available? charmap nil) false)))
      (testing "returns false on letter that is not present"
        (is (= (available? charmap \z) false)))
      (testing "returns true on letters that are present"
        (is (= (available? charmap \a) true))
        (is (= (available? charmap \r) true))
        (is (= (available? charmap \w) true)))))
  (testing "scramble?"
    (testing "handles `nil` `charlist` argument"
      (is (= (scramble? nil word1) false)))
    (testing "handles `nil` `word` argument"
      (is (= (scramble? list1 nil) false)))
    (testing "returns true on contained words"
      (is (= (scramble? list1 word1) true))
      (is (= (scramble? list2 word2) true)))
    (testing "returns false when letters of the word do not present"
      (is (= (scramble? list3 word3) false)))))
