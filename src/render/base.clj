(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [garden.stylesheet :refer [at-media]]
            [garden.selectors :refer [first-letter]]))

(def async-fonts
  [:script
   "WebFontConfig = { google: { families:
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

(def klipse-settings
  [:script
   "
    window.klipse_settings = {
        selector_eval_js: '.language-js ,.js , .javascript',
        selector: '.language-clj,.clojure',
        selector_reagent: '.language-reagent,.reagent',
         selector_eval_reason_3: '.language-reason, .reason'
    };
"])

(defn render
  [content]
  (html5
    [:head
     [:meta {:name "viewport", :content "width=device-width, initial-scale=1"}]
     [:meta {:charset "utf-8"}] (include-css "/css/codemirror.css") ;; (include-css
                                                                    ;; "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.6.0/styles/Vs.min.css")
     (include-js
       "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.6.0/highlight.min.js")
     (include-css "/css/vs.css") (include-css "/css/garden.css")
     (include-js "/js/highlight.pack.js")
     (include-css "https://use.fontawesome.com/releases/v5.0.13/css/all.css")
     [:script "hljs.initHighlightingOnLoad()"]
     #_(include-js "/js/scripts/klipse.js") klipse-settings async-fonts
     [:script {:async true :src "https://www.googletagmanager.com/gtag/js?id=UA-18360473-1"}]
     [:script
      "window.dataLayer = window.dataLayer || [];
     function gtag(){dataLayer.push(arguments);}
     gtag('js', new Date());
     gtag('config', 'UA-18360473-1');"]
     ]
    [:div.container
     [:div.content
      (-> content
          :entry
          :content)] (include-js "/js/klipse_plugin.js")]))
