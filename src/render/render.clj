(ns render.render
  (:require [hiccup.core :refer [html deftag]]
            [render.index]))

(defn render
  [c]
  (-> c :entry :content read-string html))
