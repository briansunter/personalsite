(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]
            [garden.core :refer [css]]))

(def async-fonts [:script "WebFontConfig = { google: { families:
    ['Source Sans Pro:400,600,700,400italic,700italic',
    'Montserrat',
    'Open Sans',
    'Lora']}};

   (function(d) {
      var wf = d.createElement('script'), s = d.scripts[0];
      wf.src = 'https://ajax.googleapis.com/ajax/libs/webfont/1.6.26/webfont.js';
      wf.async = true;
      s.parentNode.insertBefore(wf, s);
   })(document);"])

(def style [[:h1 :h2 :h3 :h4 {:font-family "'Monserrat', sans-serif"}]
            [:ul.social {:display "flex"
                         :justify-content "space-between"}]
            [:p {:font-family "'Lora', serif"}]
            [:div.content {:grid-column " 2 / 23"}]
            [:div.container {:display :grid
                        :grid-gap "20px"
                        :grid-template-rows "auto 1fr auto"
                        :grid-template-columns "repeat(24, [col-start] 1fr)"}]
            [:ul {:list-style-type "none"
                  :padding 0}]
            (at-media {:min-width "320px"}
                      [:html
                       {:font-size "calc(18px + 6 * ((100vw - 320px) / 680))"}])])

(defn render
  [content]
  (html5 [:head [:meta {:charset "utf-8"}]
          (include-css "/css/codemirror.css")
          (include-js "/js/scripts/klipse.js")
          [:style (css style)]
          async-fonts]
         [:div.container [:div.content (-> content :entry :content)]
          (include-js "/js/klipse_plugin.js")]))
