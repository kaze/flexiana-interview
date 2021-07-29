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
    (if (or (= "null" charlist)
            (empty? charlist)
            (= "null" word)
            (empty? word))
      (response/bad-request {:body {:error "Wrong parameters"}})
      (response/ok {:body {:answer (scramble? charlist word)}}))))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/check-scramble" {:get check-scramble}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])
