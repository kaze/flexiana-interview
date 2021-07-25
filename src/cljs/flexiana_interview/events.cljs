(ns flexiana-interview.events
  (:require
    [re-frame.core :as rf]
    [ajax.core :as ajax]
    [reitit.frontend.easy :as rfe]
    [reitit.frontend.controllers :as rfc]))

;;dispatchers

(rf/reg-event-db
  :common/navigate
  (fn [db [_ match]]
    (let [old-match (:common/route db)
          new-match (assoc match :controllers
                                 (rfc/apply-controllers (:controllers old-match) match))]
      (assoc db :common/route new-match))))

(rf/reg-fx
  :common/navigate-fx!
  (fn [[k & [params query]]]
    (rfe/push-state k params query)))

(rf/reg-event-fx
  :common/navigate!
  (fn [_ [_ url-key params query]]
    {:common/navigate-fx! [url-key params query]}))

(rf/reg-event-db
  :set-docs
  (fn [db [_ docs]]
    (assoc db :docs docs)))

(rf/reg-event-fx
  :fetch-docs
  (fn [_ _]
    {:http-xhrio {:method          :get
                  :uri             "/docs"
                  :response-format (ajax/raw-response-format)
                  :on-success       [:set-docs]}}))

(rf/reg-event-db
 :set-scramble-result
 (fn [db [_ result]]
   (assoc db :scramble-result (:answer (:body result)))))

(rf/reg-event-db
 :set-charlist
 (fn [db [_ value]]
   (assoc db :charlist value)))

(rf/reg-event-db
 :set-word
 (fn [db [_ value]]
   (assoc db :word value)))

(rf/reg-event-fx
 :get-scramble-result
 (fn [_ [_ data]]
   (js/console.log (str "data before request: " data))
   {:http-xhrio {:method :get
                 :uri "/check-scramble"
                 :params data
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:set-scramble-result]
                 :on-failure [:common/set-error]}}))

(rf/reg-event-db
  :common/set-error
  (fn [db [_ error]]
    (assoc db :common/error error)))

(rf/reg-event-fx
  :page/init-home
  (fn [_ _]
    {:dispatch [:fetch-docs]}))

;;subscriptions

(rf/reg-sub
  :common/route
  (fn [db _]
    (-> db :common/route)))

(rf/reg-sub
  :common/page-id
  :<- [:common/route]
  (fn [route _]
    (-> route :data :name)))

(rf/reg-sub
  :common/page
  :<- [:common/route]
  (fn [route _]
    (-> route :data :view)))

(rf/reg-sub
  :docs
  (fn [db _]
    (:docs db)))

(rf/reg-sub
  :common/error
  (fn [db _]
    (:common/error db)))

(rf/reg-sub
 :charlist
  (fn [db _]
    (:charlist db)))

(rf/reg-sub
 :word
  (fn [db _]
    (:word db)))

(rf/reg-sub
 :scramble-result
  (fn [db _]
    (:scramble-result db)))
