(ns render.stylesheet
  (:require [garden.def :as gdn]
            [garden.stylesheet :refer [at-media]]))

(gdn/defstyles base [[:h1 :h2 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
                     [:.hljs {:border "solid 1px #90B4FE"
                              :font-family "FiraCode"}]
                     [:ul.social {:display "flex"
                                  :text-align "center"
                                  :justify-content "space-between"}]

                     (at-media {:max-width "1000px"}
                               [:p {:font-family "'Lora', serif"}]
                               [:.container {:display :grid
                                             :grid-template-columns "5vw 90vw 5vw"}]
                               [:.content {:grid-column "2"}])

                     (at-media {:min-width "1050px"}
                               [:div.container {:display :grid
                                                :grid-gap "20px"
                                                :grid-template-columns "auto 1050px auto"
                                                :grid-template-rows "auto"}]
                               [:.content {:grid-column 2
                                           :max-width "1050px"}])])

(gdn/defstyles index
  [[:h1 :h2 :h3 :h4
    {:font-family "'Monserrat', sans-serif"
     :line-height 1.2
     :margin 0}]
   [:p {:font-size "1.5rem"
        :font-family "'Lora', serif"}]
   [:h1 {:font-size "3.5rem"}]

   [:.social-profile [:h3 {:font-size "1.25rem"}]]

   (at-media {:max-width "1000px"}
             [:.home {:padding "20px"}]
             [:.home [[:h1 {:font-size "3rem"
                            :font-style "bold"
                            :margin-top "20px"
                            :margin-bottom "20px"                           }]
                      [:h3 {:font-size "2rem"
                            :margin-top "10px"
                            :margin-bottom "10px"                           }]
                      [:p {:font-size "1.5rem"}]]]
             [:.content [[:h1 {:font-size "3rem"
                               :margin-top "20px"
                               :margin-bottom "20px"}]
                         [:h2 {:font-size "2rem"
                               :margin-top "20px"
                               :margin-bottom "20px"}]
                         [:h3 {:font-size "2rem"
                               :margin-top "20px"
                               :margin-bottom "20px"}]
                         [:p {:font-size "1.5rem"
                              :margin-top "20px"}]

                         [:.CodeMirror {:margin-top "20px"
                                        :margin-bottom "20px"}]
                         [:.CodeMirror-code {:font-size "1rem"
                                             :padding "10px"}]]]
             [:.social-profile {:margin-top "10px"} [:h3 {:font-size "1.25rem"}]]
             [:i {:font-size "4rem"}])

   (at-media {:min-width "1000px"}
             [[:ul.social
               {:display "flex"
                :justify-content "space-between"}]
              [:h3 {:font-size "2rem"}]
              [:.home [[:h1 {:font-size "6rem"}]
                       [:p {:font-size "2rem"}]]]
              [:.content [[:h1 {:font-size "5rem"
                                :margin-top "20px"
                                :margin-bottom "20px"                               }]
                          [:h2 {:font-size "4rem"
                                :margin-top "20px"
                                :margin-bottom "20px"                               }]
                          [:p {:font-size "2rem"}]
                          [:.CodeMirror {:margin-top "30px"
                                         :margin-bottom "30px"}]
                          [:.CodeMirror-code {:font-size "2rem"
                                              :padding "10px"}]]]
              [:.social-profile {:margin-top "20px"}[:h3 {:font-size "3rem"}]
               [:i {:font-size "6rem"}]
               ]
              [:h3 {:font-size "4rem"}]

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
                :grid-template-areas " \"header\" \"blog-section\" \"projects-section\" \"photo-section\" \"work-section\""}]])])

(gdn/defstyles screen
  [:body
   {:font-family "Helvetica Neue"
    :font-size   "16px"
    :line-height 1.5}])

(gdn/defstyles combined
  screen
  base
  index)
