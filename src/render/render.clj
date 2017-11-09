(ns render.render
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [render.index]))

(def head
  [:head
   (include-css "https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css")
   (include-css "https://fonts.googleapis.com/css?family=Montserrat")
   (include-css "/css/blog.css")
   (include-css "https://fonts.googleapis.com/css?family=Neuton")])

(defn base
  [c]
  (html5 [:head [:meta {:charset "utf-8"}]
          (include-css "/css/blog.css")
          (include-css "https://storage.googleapis.com/app.klipse.tech/css/codemirror.css")
          (include-js "/js/scripts/klipse.js")
          (include-css "https://fonts.googleapis.com/css?family=Montserrat")
          (include-css "https://fonts.googleapis.com/css?family=Neuton")]
         (-> c :entry :content)
         (include-js "https://storage.googleapis.com/app.klipse.tech/plugin/js/klipse_plugin.js")
         ))

(defn render
  [c]
  (html5 head (-> c :entry :content read-string)))
