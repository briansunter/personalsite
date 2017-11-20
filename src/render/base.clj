(ns render.base
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]))
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
   })(document);"
  ])

(defn render
  [content]
  (html5 [:head [:meta {:charset "utf-8"}]
          (include-css "/css/codemirror.css")
          (include-css "/css/blog.css")
          (include-js "/js/scripts/klipse.js")]
         async-fonts

         [:div.foo (-> content :entry :content)
          (include-js "/js/klipse_plugin.js")]))
