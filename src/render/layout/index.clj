(ns render.layout.index
  (:require [hiccup.core :refer [deftag]]
            [utils :refer [has-tag?]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]))

(def head
  [:head
   [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
   [:script
    "WebFontConfig = { google: { families:
    ['Source Sans Pro:400,600,700,400italic,700italic',
    'Montserrat',
    'Open Sans',
    'Lora']}};

   (function(d) {
      var wf = d.createElement('script'), s = d.scripts[0];
      wf.src = 'https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js';
      wf.async = true;
      s.parentNode.insertBefore(wf, s);
   })(document);"]

   [:script {:async true :src "https://www.googletagmanager.com/gtag/js?id=UA-18360473-1"}]
   [:script
    "window.dataLayer = window.dataLayer || [];
     function gtag(){dataLayer.push(arguments);}
     gtag('js', new Date());
     gtag('config', 'UA-18360473-1');"]
   (include-css "https://use.fontawesome.com/releases/v5.0.13/css/all.css")
   (include-css "/css/garden.css")])

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
    (for [{:keys [link name type]} social-profiles]
      [:li.social-profile.flex-item
       [:a {:href link} [:i {:class (str "fab " (social-type-to-icon type))}]
        [:h3 name]]])]])

(defn blog-entry
  [{:keys [title description permalink]}]
  [:li.blog.entry [:h3 [:a {:href permalink} title]] [:p description]])

(defn blog-section
  [entries]
  [:div.blog.section [:h1 "Blog"]
   [:ul (for [entry entries] (blog-entry entry))]])

(defn photo-entry
  [{:keys [title description gphotos]}]
  [:li.photo.entry [:h3 [:a {:href gphotos} title]] [:p description]])

(defn photo-section
  [entries]
  [:div.photo.section [:h1 "Photos"]
   [:ul (for [entry entries] (photo-entry entry))]])

(defn job [name desc] [:li.job [:h3 name] [:p desc]])

(defn work-section
  [entries]
  [:div.work.section [:h1 "Work"]
   [:ul
    (for [{:keys [name description], :as entry} entries]
      (job name description))]])

(defn project
  [{:keys [name description permalink]}]
  [:li.project [:h3 [:a {:href permalink} name]] [:p description]])

(defn projects-section
  [entries]
  [:div.projects.section [:h1 "Projects"]
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

(defn render [content] (html5 head (home-page content)))
