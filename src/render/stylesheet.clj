(ns render.stylesheet
  (:require [garden.def :as gdn]
            [garden.selectors :as s]
            [garden.stylesheet :refer [at-media]]))

(def body-font-family "'Lora', serif")
(def code-font-family "FiraCode")
(def header-font-family "'Monserrat', sans-serif")

(def block-quote [:blockquote {:font-size "1.4em"
                               :margin "50px auto"
                               :font-family "Open Sans"
                               :color "#555555"
                               :padding "1em"
                               :border-left "8px solid black"
                               :line-height "1.6"
                               :position "relative"
                               :background "#EDEDED"}])
(def font-defaults
  [[:h1 :h2 :h3 :h4
    {:font-family header-font-family
     :margin-top "20px"
     :margin-bottom "20px"}]
   [:h1 {:text-align "center"}]
   [:p {:font-family body-font-family}]
   [:body {:line-height "1.3"}]
   [:code {:background-color "rgba(27,31,35,0.05)"}]
   (at-media {:max-width "1000px"}
             [:h1 {:font-size "3rem"}]
             [:h2 {:font-size "2.5rem"}]
             [:h3 {:font-size "2rem"}]
             [:p  {:font-size "1.5rem"}]
             [:li  {:font-size "1.5rem"}])
   (at-media {:min-width "1000px"}
             [[:h1 {:font-size "5rem"}]
              [:h2 {:font-size "2.5rem"}]
              [:h3 {:font-size "2rem"}]
              [:p  {:font-size "1.5rem"}]
              [:li {:font-size "1.5rem"}]])])

(def highlight-js
  [:.hljs {:border "solid 1px #90B4FE", :font-family code-font-family,
           :padding "10px"
           :margin-top "20px"
           :margin-bottom "20px"}])

(def social
  [[:.social
    {:display "flex",
     :margin-top "10px",
     :text-align "center",
     :justify-content "space-between"}]
   (at-media {:max-width "1000px"}
             [:.social-profile {:margin-top "10px"}
              [:h3 {:font-size "1.25rem", :margin-top "10px"}]
              [:i {:font-size "3rem"}]])
   (at-media {:min-width "1000px"}
             [:.social-profile {:margin-top "20px"} [:h3 {:font-size "2rem"}]
              [:i {:font-size "4rem"}]])])

(def container
  [(at-media {:max-width "1000px"}
             [:.container
              {:display :grid, :grid-template-columns "5vw 90vw 5vw"}]
             [:.content {:grid-column "2"}])
   (at-media {:min-width "1050px"}
             [:.container
              {:display :grid,
               :grid-gap "20px",
               :grid-template-columns "auto 760px auto",
               :grid-template-rows "auto"}]
             [:.content>h1 {:font-size "3rem"}]
             [:.content {:grid-column 2, :max-width "760px"}])])

(def header [[:.header {:grid-area "header", :grid-column 2}]
             [:.header>* {:text-align :center}]
             (at-media {:min-width "1050px"}
                       [:.header>p {:font-size "2rem"}])

             (at-media {:max-width "1000px"}
                       [:.header>p {:font-size "1.5rem"}])])

(def section [[:.section {:grid-column 2}]
              [:.section>h1 {:text-align "center"}]])

(gdn/defstyles base [font-defaults highlight-js container])

(def home
  [[:.home
    {:display :grid,
     :grid-template-areas
       " \"header\" \"blog-section\" \"projects-section\" \"photo-section\" \"work-section\""}]
   [:.home.ul {:list-style-type "none"}]
   (at-media {:max-width "1000px"}
             [:.home {:grid-template-columns "5vw 90vw 5vw"}])
   (at-media {:min-width "1000px"}
             [:.home {:grid-template-columns "1fr 760px 1fr"}])])

(gdn/defstyles index [home header social section])

(def code-mirror
  [(at-media {:max-width "1000px"}
             [:.CodeMirror {:margin-top "20px", :margin-bottom "20px"}]
             [:.CodeMirror-code {:font-size "1rem", :padding "10px"}])
   (at-media {:min-width "1000px"}
             [:.CodeMirror {:margin-top "30px", :margin-bottom "30px"}]
             [:.CodeMirror-code {:font-size "1.25rem", :padding "10px"}])])

(s/defselector *)

(def post
  [:.content [:ul {:list-style-type "disc"
                   :padding-left ".5em"}]])

(gdn/defstyles page code-mirror block-quote post)


(gdn/defstyles combined base index page)
