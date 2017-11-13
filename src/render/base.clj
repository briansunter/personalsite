(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn render
  [content]
  (html5 [:head ][:div.foo (-> content :entry :content)]))
