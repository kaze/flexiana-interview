(ns flexiana-interview.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [flexiana-interview.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[flexiana-interview started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[flexiana-interview has shut down successfully]=-"))
   :middleware wrap-dev})
