(ns render.stylesheet
  (:require [garden.def :as gdn]
            [garden.stylesheet :refer [at-media]]))

(gdn/defstyles base [[:h1 :h2 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
                     [:ul.social {:display "flex"
                                  :justify-content "space-between"}]
                     [:p {:font-family "'Lora', serif"}]
                     [:div.container {:display :grid
                                      :grid-gap "20px"
                                      :grid-template-rows "auto 1fr auto"
                                      :align-items "center"
                                      :grid-template-columns "repeat(24, [col-start] 1fr)"}]
                     (at-media {:min-width "320px"}
                               [:div.content {:grid-column " 2 / 23"}])
                     (at-media {:min-width "1200px"}
                               [:div.content {:grid-column " 5 / 20"}])])

(gdn/defstyles index
  [[:h1 :h2 :h3 :h4
    {:font-family "'Monserrat', sans-serif"
     :margin 0}]
   [:p {:font-size "1.5rem"
        :font-family "'Lora', serif"
        }]
   [:.header [[:h1 {:font-size "3.5rem"}]]]
   [:.social-profile [:h3 {:font-size "1.25rem"}]]
   [:h3 {:font-size "2rem"}]
   [:.blog-entry [[:p {:font-size "1.5rem"}]]]

   [:ul.social
    {:display "flex"
     :justify-content "space-around"}]
   (at-media {:min-width "1200px"}
             [:ul.social
              {:display "flex"
               :justify-content "space-between"}])

   [:ul
    {:list-style-type "none"
     :padding 0}]

   (at-media {:min-width "320px"}
             [:html
              {:font-size "calc(18px + 6 * ((100vw - 320px) / 680))"}])

   [:div.header
    {:grid-area "header"
     :grid-column "1 / 13"}]

   [:div.work-section
    {:grid-area "work-section"
     :grid-column " 1 / 13"}
    [:h1 {:font-size "3.5rem"}]]

   [:div.projects-section
    {:grid-area "projects-section"
     :grid-column " 1 / 13"}
    [:h1 {:font-size "3.5rem"}]]

   [:div.blog-section
    {:grid-area "blog-section"
     :grid-column " 1 / 13"}
    [:h1 {:font-size "3.5rem"}]]

   [:div.photo-section
    {:grid-area "photo-section"
     :grid-column " 1 / 13"}[:h1 {:font-size "3.5rem"}]]


   [:div.home
    {:display :grid
     :grid-gap "20px"
     :grid-template-columns "repeat(12, [col-start] 1fr)"
     :grid-template-areas " \"header\" \"blog-section\" \"projects-section\" \"photo-section\" \"work-section\""}]

   (at-media {:min-width "1000px"}
             [:div.header {:grid-column "4 / span 6"}]
             [:div.blog-section {:grid-column "4 / span 6"}]
             [:div.projects-section {:grid-column "4 / span 6"}]
             [:div.photo-section {:grid-column "4 / span 6"}]
             [:div.work-section {:grid-column "4 / span 6"}])])

(gdn/defstyles screen
  [:body
   {:font-family "Helvetica Neue"
    :font-size   "16px"
    :line-height 1.5}])

(gdn/defstyles combined
  screen
  base
  index)
