(ns render.layout.index
  (:require [hiccup.core :refer [deftag]]
            [utils :refer [has-tag?]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]
            [garden.core :refer [css]]))

(def garden-style
  [[:h1 :h1 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
   [:ul.social {:display "flex"
                :justify-content "space-between"}]
   [:p {:font-family "'Lora', serif"}]
   [:ul {:list-style-type "none"
         :padding 0}]
   (at-media {:min-width "320px"}
             [:html
              {:font-size "calc(18px + 6 * ((100vw - 320px) / 680))"}])
   [:div.header {:grid-area "header"
                 :grid-column "1 / 13"}]
   [:div.work-section {:grid-area "work-section"
                       :grid-column " 1 / 13"}]
   [:div.projects-section {:grid-area "projects-section"
                           :grid-column " 1 / 13"}]
   [:div.blog-section {:grid-area "blog-section"
                           :grid-column " 1 / 13"}]
   [:div.photo-section {:grid-area "photo-section"
                       :grid-column " 1 / 13"}]
   [:div.home {:display :grid
               :grid-gap "20px"
               :grid-template-columns "repeat(12, [col-start] 1fr)"
               :grid-template-areas " \"header\" \"blog-section\" \"projects-section\" \"photo-section\" \"work-section\""}]

   (at-media {:min-width "1000px"}
             [:div.header {:grid-column "3 / span 8"}]
             [:div.blog-section {:grid-column "3 / span 8"}]
             [:div.projects-section {:grid-column "3 / span 8"}]
             [:div.photo-section {:grid-column "3 / span 8"}]
             [:div.work-section {:grid-column "3 / span 8"}])])

(def head
  [:head
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:script "WebFontConfig = { google: { families:
    ['Source Sans Pro:400,600,700,400italic,700italic',
    'Montserrat',
    'Open Sans',
    'Lora']}};

   (function(d) {
      var wf = d.createElement('script'), s = d.scripts[0];
      wf.src = 'https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js';
      wf.async = true;
      s.parentNode.insertBefore(wf, s);
   })(document);"
    ]
   [:style (css garden-style)]])

(defn header
  [{:keys [social-profiles title description]}]
  [:div.header
   [:h1 title]
   [:p description]
   [:ul.social
    (for [{:keys [link name]} social-profiles]
      [:li.social-profile.flex-item
       [:h3 [:a {:href link} name]]])]])

(defn blog-entry
  [{:keys [title description permalink]}]
  [:li.blog-entry
   [:h3 [:a {:href permalink} title]]
   [:p description]])

(defn blog-section
  [entries]
  [:div.blog-section
   [:h1 "Blog"]
   [:ul
    (for [entry entries]
      (blog-entry entry))]])

(defn photo-entry
  [{:keys [title description permalink]}]
  [:li.photo-entry
   [:h3 [:a {:href permalink} title]]
   [:p description]])

(defn photo-section
  [entries]
  [:div.photo-section
   [:h1 "Photos"]
   [:ul
    (for [entry entries]
      (photo-entry entry))]])

(defn job
  [name desc]
  [:li.job
   [:h3 name]
   [:p desc]])

(defn work-section
  [entries]
  [:div.work-section
   [:h1 "Work"]
   [:ul
    (for [{:keys [name description] :as entry} entries]
      (job name description))]])

(defn project
  [{:keys [name description permalink]}]
  [:li.project
   [:h3 [:a {:href permalink} name]]
   [:p description]])

(defn projects-section
  [entries]
  [:div.projects-section
   [:h1 "Projects"]
   [:ul
    (for [entry entries]
      (project entry))]])

(defn home-page
  [{:keys [entries meta] :as content}]
  (let [jobs (reverse (sort-by :date (filter (partial has-tag? "job") entries)))
        projects (filter (partial has-tag? "project") entries)
        blog-entries (filter (partial has-tag? "blog") entries)
        photo-entries (filter (partial has-tag? "photography") entries)]
    [:div.home
     (header meta)
     (projects-section projects)
     (blog-section blog-entries)
     (photo-section photo-entries)
     (work-section jobs)]))

(defn render
  [content]
  (html5 head (home-page content)))
