(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn render
  [content]
  (html5 [:head [:meta {:charset "utf-8"}]]
         [:div.foo (-> content :entry :content)]))
