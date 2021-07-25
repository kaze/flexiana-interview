(ns flexiana-interview.routes.home
  (:require
   [flexiana-interview.layout :as layout]
   [clojure.java.io :as io]
   [flexiana-interview.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]
   [flexiana-interview.logic :refer [scramble?]]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn check-scramble [request]
  (let [params (:query-params request)
        charlist (get params "charlist")
        word (get params "word")]
    (if (not (or (empty? charlist) (empty? word)))
      (response/ok {:body {:params params :answer (scramble? charlist word)}})
      (response/ok {:body {:error "Wrong parameters"}}))))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/check-scramble" {:get check-scramble}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])
