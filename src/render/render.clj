(ns render.render
  (:require [hiccup.core :refer [html deftag]]
            [hiccup.page :refer [include-css]]
            [render.index]))

(def head
  [:head
   (include-css "https://fonts.googleapis.com/css?family=Montserrat")
   (include-css "https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css")])

(defn render
  [c]
  (html head (-> c :entry :content read-string)))
