(ns render.stylesheet
  (:require [garden.def :as gdn]
            [garden.stylesheet :refer [at-media]]))

(gdn/defstyles base [[:h1 :h2 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
                     [:ul.social {:display "flex"
                                  :justify-content "space-between"}]
                     [:p {:font-family "'Lora', serif"}]
                     [:div.container {:display :grid
                                      :grid-gap "20px"
                                      :grid-template-columns "auto 1050px auto"
                                      :justify-content "center"
                                      :grid-template-rows "auto"}]
                     [:.content {:grid-column 2
                                 :max-width "1050px"}]])

(gdn/defstyles index
  [[:h1 :h2 :h3 :h4
    {:font-family "'Monserrat', sans-serif"
     :margin 0}]

   [:p {:font-size "1.5rem"
        :font-family "'Lora', serif"}]
   [:h1 {:font-size "3.5rem"}]

   [:.social-profile [:h3 {:font-size "1.25rem"}]]

   [:ul.social
    {:display "flex"
     :justify-content "space-around"}]

   (at-media {:min-width "1000px"}
             [[:ul.social
               {:display "flex"
                :justify-content "space-between"}]
              [:h3 {:font-size "2rem"}]
              [:.home [[:h1 {:font-size "9rem"}]
                       [:p {:font-size "3rem"}]]]
              [:.content [[:h1 {:font-size "5rem"}]
                          [:h2 {:font-size "3rem"}]
                          [:p {:font-size "3rem"}]
                          [:.code {:font-size "2rem"
                                   :padding "10px"}]]]
              [:.social-profile [:h3 {:font-size "4rem"}]]
              [:h3 {:font-size "4rem"}]])

   [:ul
    {:list-style-type "none"
     :padding 0}]

   [:div.header
    {:grid-area "header"
     :grid-column 2}]

   [:div.work-section
    {:grid-area "work-section"
     :grid-column 2}]

   [:div.projects-section
    {:grid-area "projects-section"
     :grid-column 2}]

   [:div.blog-section
    {:grid-area "blog-section"
     :grid-column 2}]

   [:div.photo-section
    {:grid-area "photo-section"
     :grid-column 2}]

   [:div.home
    {:display :grid
     :grid-gap "20px"
     :grid-template-columns "1fr 1050px 1fr"
     :grid-template-areas " \"header\" \"blog-section\" \"projects-section\" \"photo-section\" \"work-section\""}]])

(gdn/defstyles screen
  [:body
   {:font-family "Helvetica Neue"
    :font-size   "16px"
    :line-height 1.5}])

(gdn/defstyles combined
  screen
  base
  index)
