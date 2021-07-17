(ns flexiana-interview.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[flexiana-interview started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[flexiana-interview has shut down successfully]=-"))
   :middleware identity})
