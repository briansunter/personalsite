(ns render.index
  (:require [hiccup.core :refer [deftag]]
            [utils :refer [has-tag?]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]
            [garden.core :refer [css]]))

(def garden-style
  [[:h1 :h2 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
   [:p {:font-family "'Lora', serif"}]
   [:ul {:list-style-type :none
         :padding 0}]
   (at-media {:min-width "320px"}
             [:html
              {:font-size "calc(16px + 6 * ((100vw - 320px) / 480))"}])
   [:div.header {:grid-area "header"
                 :grid-column "1 / 12"}]
   [:div.work-section {:grid-area "work-section"
                       :grid-column " 1 / 12"}]
   [:div.projects-section {:grid-area "projects-section"
                           :grid-column " 1 / 12"}]
   [:div.home {:display :grid
               :grid-gap "20px"
               :grid-template-columns "repeat(12, [col-start] 1fr)"
               :grid-template-areas " \"header\" \"work-section\" \"projects-section\""}]

   (at-media {:min-width "1000px"}
             [:div.header {:grid-column "3 / span 8"}]
             [:div.work-section {:grid-column "3 / span 8"}]
             [:div.projects-section {:grid-column "3 / span 8"}])])

(def head
  [:head
   (include-js "https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js")
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:script
    "WebFont.load({
                  google: {
                           families: ['Source Sans Pro:400,600,700,400italic,700italic',
                                      'Montserrat',
                                      'Open Sans',
                                      'Lora'
]
                           }
                  });"
    ]
   [:style (css garden-style)]])

(defn header
  [{:keys [social-profiles title description]}]
  [:div.header
   [:h1 title]
   [:p description]
   [:ul.social
    (for [{:keys [link name]} social-profiles]
      [:li
       [:a {:href link} name]])]])

(defn job
  [name desc]
  [:li.job
   [:h3 name]
   [:p desc]])

(defn work-section
  [entries]
  [:div.work-section
   [:h2 "Work"]
   [:ul
    (for [{:keys [name description] :as entry} entries]
      (job name description))]])

(defn project
  [name desc]
  [:li.project
   [:h3 name]
   [:p desc]])

(defn projects-section
  [entries]
  [:div.projects-section
   [:h2 "Projects"]
   [:ul
    (for [{:keys [name description] :as entry} entries]
      (project name description))]])

(defn home-page
  [{:keys [entries meta] :as content}]
  (let [jobs (reverse (sort-by :date (filter (partial has-tag? "job") entries)))
        projects (filter (partial has-tag? "project") entries)]
    [:div.home
     (header meta)
     (work-section jobs)
     (projects-section projects)]))

(defn render
  [content]
  (html5 head (home-page content)))
