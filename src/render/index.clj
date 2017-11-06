(ns render.index
  (:require [hiccup.core :refer [deftag]]))

(def title-font-size "8em")
(def header-font-size "6em")
(def sub-header-font-size "4em")
(def social-font-size "3em")
(def content-font-size "2em")

(defn style [s]
  (clojure.string/join ";" (map #(str (name %) ":" ((keyword %) s)) (keys s))))

(deftag :cljs
  (fn [attrs content]
    [:div
     {:style (style {:grid-column "2 / span 20"})}
     [:code.language-klipse
          content]]))

(deftag :project
  (fn [attrs content]
    [:body
     [:div {:style (style {:display "grid"
                           :grid-template-columns "repeat(24, [col-start] 1fr)"
                           :grid-auto-flow "column"
                           :font-family "'Montserrat', sans-serif"
                           :grid-row-gap "3em"})}
      content]]))

(defn project-section
  [name {:keys [href]} content]
  [:div
   [:a {:href href
        :style (style {:font-size sub-header-font-size
                       :padding-bottom ".5em"})}
    name]
   [:div {:style (style {:font-size content-font-size
                         :padding-bottom "1em"
                         :padding-top "1em"})}
    content]])

(deftag :personal-site
  (fn [attrs content]
    (project-section "Personal Site" attrs content)))

(deftag :projects
  (fn [attrs content]
    [:div {:style (style {:grid-column "2 / span 20"})}
     [:h3 {:style (style {:font-size header-font-size
                          :padding-bottom ".25em"})}
      "Projects"]
     [:div content]]))

(defn work-section
  [name content]
  [:div
   [:h4 {:style (style {:font-size sub-header-font-size
                        :padding-bottom ".25em"})}
    name]
   [:div {:style (style {:font-size content-font-size
                         :padding-bottom "1em"})}
    content]])

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
    [:div {:style (style {:grid-column "2 / span 20"})}
     [:h3 {:style (style {:font-size header-font-size :padding-bottom ".25em"})} "Work"]
     [:div {:style (style {:padding-top "1em"})} content]]))

(defn social-link
  [{:keys [href]} content]
  [:a {:href href} content])

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
    [:div {:style (style {:display "flex"
                          :justify-content "space-between"
                          :font-size social-font-size
                          :grid-column "2 / span 20"})}
     content]))

(deftag :about
  (fn [attrs content]
    [:div {:style (style {:grid-column "2 / span 20"
                          :font-size sub-header-font-size})}
     [:h2 attrs content]]))

(deftag :title
  (fn [attrs content]
    [:div {:style (style {:font-size title-font-size
                          :grid-column "2/ -1"})}
     [:h1 content]]))

(deftag :landing-page
  (fn [attrs content]
    [:body
     [:div {:style (style {:display "grid"
                           :grid-template-columns "repeat(24, [col-start] 1fr)"
                           :grid-auto-flow "column"
                           :font-family "'Montserrat', sans-serif"
                           :grid-row-gap "3em"})}
