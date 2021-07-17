(ns flexiana-interview.app
  (:require [flexiana-interview.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
