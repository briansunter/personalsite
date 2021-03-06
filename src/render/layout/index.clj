(ns render.layout.index
  (:require [hiccup.core :refer [deftag]]
            [utils :refer [has-tag?]]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
            [hiccup.page :refer [html5 include-css include-js]]
            [render.base :refer [head]]
            [garden.stylesheet :refer [at-media]]))

(defn social-type-to-icon
  [social-type]
  (case social-type
    :github "fa-github-square"
    :facebook "fa-facebook"
    :twitter "fa-twitter-square"
    :instagram "fa-instagram"
    :linkedin "fa-linkedin"))

(defn header
  [{:keys [social-profiles title description]}]
  [:div.header [:h1.main-title title] [:p description]
   [:ul.social
    (for [{:keys [link name type]} (take 4 social-profiles)]
      [:li.social-profile.flex-item
       [:a {:href link} [:i {:class (str "fab " (social-type-to-icon type))}]
        [:h3 name]]])]])

(def blog-entry-date-formatter (f/formatter "MMMM d, y"))

(defn blog-entry
  [{:keys [title tags date-published description permalink]}]
  [:li.blog.entry [:h3 [:a {:href permalink} title]]
   [:div.blog-list-header
   #_[:p.blog-list-date (f/unparse blog-entry-date-formatter (c/from-date date-published))]]
   [:p.blog-entry-description description]])

(defn blog-section
  [entries]
  [:div.blog.section [:h1#blog "Blog"]
   [:ul (for [entry entries] (blog-entry entry))]])

(defn photo-entry
  [{:keys [title description gphotos]}]
  [:li.photo.entry [:h3 [:a {:href gphotos} title]] [:p description]])

(defn photo-section
  [entries]
  [:div.photo.section [:h1#photos "Photos"]
   [:ul (for [entry entries] (photo-entry entry))]])

(defn job [name desc] [:li.job [:h3 name] [:p desc]])

(defn work-section
  [entries]
  [:div.work.section [:h1#work "Work"]
   [:ul
    (for [{:keys [name description] :as entry} entries]
      (job name description))]])

(defn project
  [{:keys [name description permalink]}]
  [:li.project [:h3 [:a {:href permalink} name]] [:p description]])

(defn projects-section
  [entries]
  [:div.projects.section [:h1#projects "Projects"]
   [:ul (for [entry entries] (project entry))]])

(defn home-page
  [{:keys [entries meta], :as content}]
  (let [jobs (reverse (sort-by :date (filter (partial has-tag? "job") entries)))
        projects (filter (partial has-tag? "project") entries)
        blog-entries (filter (partial has-tag? "blog") entries)
        photo-entries (filter (partial has-tag? "photography") entries)]
    [:div.home
     (header meta)
     (blog-section blog-entries)
     (projects-section projects)
     (photo-section photo-entries)
     (work-section jobs)]))

(defn render [content] (html5 {:lang "en"} head (home-page content)))
