(ns render.tag
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn render
  [content]
  (html5 [:div [:p (str content)]]))
