(ns flexiana-interview.core
  (:require
   [day8.re-frame.http-fx]
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [goog.events :as events]
   [goog.history.EventType :as HistoryEventType]
   [markdown.core :refer [md->html]]
   [flexiana-interview.ajax :as ajax]
   [flexiana-interview.events]
   [reitit.core :as reitit]
   [reitit.frontend.easy :as rfe]
   [clojure.string :as string])
  (:import goog.History))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href   uri
    :class (when (= page @(rf/subscribe [:common/page-id])) :is-active)}
   title])

(defn navbar []
  (r/with-let [expanded? (r/atom false)]
              [:nav.navbar.is-info>div.container
               [:div.navbar-brand
                [:a.navbar-item {:href "/" :style {:font-weight :bold}} "flexiana-interview"]
                [:span.navbar-burger.burger
                 {:data-target :nav-menu
                  :on-click #(swap! expanded? not)
                  :class (when @expanded? :is-active)}
                 [:span][:span][:span]]]
               [:div#nav-menu.navbar-menu
                {:class (when @expanded? :is-active)}
                [:div.navbar-start
                 [nav-link "#/" "Home" :home]
                 [nav-link "#/about" "About" :about]
                 [nav-link "#/scramble" "Scramble?" :about]]]]))

(defn scramble-page []
  (let [charlist @(rf/subscribe [:charlist])
        word @(rf/subscribe [:word])
        scramble-result @(rf/subscribe [:scramble-result])]
    [:section.section>div.container>div.content.is-medium.columns.is-flex.is-justify-content-center
     [:form.is-two-thirds.mt-6
      [:legend
       [:h3 "Scramble?"
        (cond
          (= scramble-result true) [:span {:style {:color "green"}} " Yesss!"]
          (= scramble-result false) [:span {:style {:color "red"}} " No..."]
          :default "")]]

      [:div.field
       [:label.label {:for "charlist"} "Characters"]
       [:div.control
        [:input.input {:type "text"
                       :name "charlist"
                       :id "charlist"
                       :value charlist
                       :on-change (fn [e]
                                    (let [value (.-value (.-target e))]
                                      (rf/dispatch [:set-charlist value])))}]]
       [:p.help "List of letters we can check if it has all the letters for the word you will submit."]]

      [:div.field
       [:label.label {:for "word"} "Word"]
       [:div.control
        [:input.input {:type "text"
                       :name "word"
                       :id "word"
                       :value word
                       :on-change (fn [e]
                                    (let [value (.-value (.-target e))]
                                      (rf/dispatch [:set-word value])))}]]
       [:p.help "Word for you want to find letters on the list above."]]

      [:div.control
       [:button.button.is-link {:type "submit"
                                :on-click (fn [e]
                                            (.preventDefault e
                                                             (rf/dispatch [:get-scramble-result {:charlist charlist :word word}])))}
        "Check!"]]]]))

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])

(defn home-page []
  [:section.section>div.container>div.content
   (when-let [docs @(rf/subscribe [:docs])]
     [:div {:dangerouslySetInnerHTML {:__html (md->html docs)}}])])

(defn page []
  (if-let [page @(rf/subscribe [:common/page])]
    [:div
     [navbar]
     [page]]))

(defn navigate! [match _]
  (rf/dispatch [:common/navigate match]))

(def router
  (reitit/router
    [["/" {:name        :home
           :view        #'home-page
           :controllers [{:start (fn [_] (rf/dispatch [:page/init-home]))}]}]
     ["/about" {:name :about
                :view #'about-page}]
     ["/scramble" {:name :scramble
                   :view #'scramble-page}]]))

(defn start-router! []
  (rfe/start!
    router
    navigate!
    {}))

;; -------------------------
;; Initialize app
(defn ^:dev/after-load mount-components []
  (rf/clear-subscription-cache!)
  (rdom/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (start-router!)
  (ajax/load-interceptors!)
  (mount-components))
