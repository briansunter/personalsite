(ns render.index
  (:require [hiccup.core :refer [deftag]]))

(deftag :capital-one
  (fn [attrs content]
    [:li "Capital One"]))

(deftag :snap-up
  (fn [attrs content]
    [:li "Snap Up"]))

(deftag :partender
  (fn [attrs content]
    [:li "Snap Up"]))

(deftag :amazon
  (fn [attrs content]
    [:li "Amazon"]))

(deftag :disney
  (fn [attrs content]
    [:li "Disney"]))

(deftag :work
  (fn [attrs content]
    [:ui attrs content]))

(deftag :facebook
  (fn [attrs content]
    [:li "Facebook"]))

(deftag :twitter
  (fn [attrs content]
    [:li "Twitter"]))

(deftag :linkedin
  (fn [attrs content]
    [:li "LinkedIn"]))

(deftag :github
  (fn [attrs content]
    [:li "Github"]))

(deftag :social
  (fn [attrs content]
    [:ul attrs content]))

(deftag :about
  (fn [attrs content]
    [:h2 attrs content]))

(deftag :title
  (fn [attrs content]
    [:h1 attrs content]))

(deftag :landing-page
  (fn [attrs content]
    [:div attrs content]))
