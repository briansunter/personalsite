(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :refer [first-letter]]
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
            (at-media {:min-width "320px"}[:html
                                           {:font-size "4vw"}])
            (at-media {:min-width "1200px"}[:html
                                            {:font-size "1.4vw"}])
            [:div.container {:display :grid
                             :grid-gap "20px"
                             :grid-template-rows "auto 1fr auto"
                             :align-items "center"
                             :grid-template-columns "repeat(24, [col-start] 1fr)"}]
            (at-media {:min-width "320px"}
                      [:div.content {:grid-column " 2 / 23"}])
            (at-media {:min-width "1200px"}
                      [:div.content {:grid-column " 5 / 20"}])])

(defn render
  [content]
  (html5 [:head [:meta {:charset "utf-8"}]
          (include-css "/css/codemirror.css")
          (include-css "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.6.0/styles/default.min.css")
          (include-js "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.6.0/highlight.min.js")
          [:script "hljs.initHighlightingOnLoad()"]
          #_(include-js "/js/scripts/klipse.js")
          [:script "
    window.klipse_settings = {
        selector_eval_js: '.language-js',
        selector: '.language-clj'
    };

"]
          [:style (css style)]
          async-fonts]
         [:div.container [:div.content (-> content :entry :content)]
          (include-js "/js/klipse_plugin.js")]))
