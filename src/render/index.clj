(ns render.index
  (:require [hiccup.core :refer [deftag]]))

(defn style [s]
  (clojure.string/join ";" (map #(str (name %) ":" ((keyword %) s)) (keys s))))

(deftag :contact-link
  (fn [attrs content]
    [:div "Contact"]))

(deftag :projects-link
  (fn [attrs content]
    [:div "Projects"]))

(deftag :work-link
  (fn [attrs content]
    [:div "Work"]))

(deftag :about-link
  (fn [attrs content]
    [:div "About"]))

(deftag :navigation
  (fn [attrs content]
    [:div {:style (style {:display "grid"
                          :font-size "3em"
                          :grid-template-columns "repeat(4, 1fr)"
                          :grid-column "2 / span 10"})}
     content]))

(defn work-section
  [name content]
  [:div
   [:h4 {:style (style {:font-size "2.5em" :padding-top "20px"})} name]
   [:li {:style (style {:font-size "1.5em" :padding-top "10px"})} content]])

(deftag :capital-one
  (fn [attrs content]
    (work-section "Capital One" content)))

(deftag :snap-up
  (fn [attrs content]
    (work-section "SnapUp" content)))

(deftag :partender
  (fn [attrs content]
    (work-section "Partender" content)))

(deftag :amazon
  (fn [attrs content]
    (work-section "Amazon" content)))

(deftag :disney
  (fn [attrs content]
    (work-section "Disney" content)))

(deftag :work
  (fn [attrs content]
    [:div {:style (style {:grid-column "2 / span 10"})}
     [:h3 {:style (style {:font-size "4em"})}"Work"]
     [:ul {:style (style {:padding-top "20px"})} content]]))

(defn social-link
  [{:keys [href]} content]
  [:div
   [:a {:href href} content]])

(deftag :facebook
  (fn [attrs content]
    (social-link attrs "Facebook")))

(deftag :twitter
  (fn [attrs content]
    (social-link attrs "Twitter")))

(deftag :linkedin
  (fn [attrs content]
    (social-link attrs "LinkedIn")))

(deftag :github
  (fn [attrs content]
    (social-link attrs "Github")))

(deftag :social
  (fn [attrs content]
    [:div {:style (style {:display "grid"
                          :font-size "2.5em"
                          :grid-template-columns "repeat(4, 1fr)"
                          :grid-column "2 / span 10"})}
    content]))

(deftag :about
  (fn [attrs content]
    [:div {:style (style {:grid-column "2 / span 10"
                          :font-size "2.5em"
                          :font-family "'Montserrat', sans-serif"})}
     [:h2 attrs content]]))

(deftag :title
(fn [attrs content]
    [:div {:style (style {:font-size "7em"
                          :grid-column "2/ -1"
                          :font-family "'Montserrat', sans-serif"})}
     [:h1 content]]))

(deftag :landing-page
  (fn [attrs content]
    [:body
     [:div {:style (style {:display "grid"
                           :grid-template-columns "repeat(12, [col-start] 1fr)"
                           :font-family "'Montserrat', sans-serif"
                           :grid-column-gap ".5 em"
                           :grid-auto-flow "column"
                           :grid-row-gap "2em"})}
      content]]))
