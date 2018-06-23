(ns render.photography
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [utils :refer [has-tag?]]
            [clojure.java.io :as io]
            [io.perun.core :as perun]
            [garden.stylesheet :refer [at-media]]))

(def photoswipe
  [:div.pswp {:aria-hidden "true", :role "dialog", :tabindex "-1"}
   [:div.pswp__bg]
   [:div.pswp__scroll-wrap
    [:div.pswp__container [:div.pswp__item] [:div.pswp__item] [:div.pswp__item]]
    [:div.pswp__ui.pswp__ui--hidden
     [:div.pswp__top-bar [:div.pswp__counter]
      [:button.pswp__button.pswp__button--close {:title "Close (Esc)"}]
      [:button.pswp__button.pswp__button--share {:title "Share"}]
      [:button.pswp__button.pswp__button--fs {:title "Toggle fullscreen"}]
      [:button.pswp__button.pswp__button--zoom {:title "Zoom in/out"}]
      [:div.pswp__preloader
       [:div.pswp__preloader__icn
        [:div.pswp__preloader__cut [:div.pswp__preloader__donut]]]]]
     [:div.pswp__share-modal.pswp__share-modal--hidden.pswp__single-tap
      [:div.pswp__share-tooltip] " \n            "]
     [:button.pswp__button.pswp__button--arrow--left
      {:title "Previous (arrow left)"}]
     [:button.pswp__button.pswp__button--arrow--right
      {:title "Next (arrow right)"}]
     [:div.pswp__caption [:div.pswp__caption__center]]]]])

(defn photoswipe-js
  [photoswipe-url]
  [:script
   (str
     "var psURL = '" photoswipe-url
     "';\n"
       "var options = {index: 0,
captionEl: true,
addCaptionHTMLFn: function(item, captionEl, isFake) {
    if(!item.title) {
        captionEl.children[0].innerHTML = '';
        return false;
    }
    captionEl.children[0].innerHTML = '<h3>' + item.title + '</h3>';
    return true;
}};

var items;
fetch(psURL).then(function(response) {
  return response.json();
}).then(function(psData) {
items = psData.data;
});

function openGallery(index) {
if (!items) {
  return;
}

options.index = index
var pswpElement = document.querySelectorAll('.pswp')[0];
var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
gallery.init();
}
                          ")])

(def masonry-js
  [:script
   "
 var grid = document.getElementById('album');
 var msnry = new Masonry( grid, {
   // options...
   itemSelector: '.image',
   columnWidth: 10
 });

 imagesLoaded(grid).on('progress',function() {
 msnry.layout();
 });

msnry.layout()

"])

(defn thumbnail
  [filename]
  (let [short-filename (second (re-matches #"(.*).jpg" filename))]
    (str "http://photos.bsun.io/thumbnails/photos/" short-filename "_640.jpg")))

(defn render-album
  [{:keys [entry]}]
  (let [photoswipe-url (str (:permalink entry) "photoswipe.json")]
    (html5
      [:head [:meta {:charset "utf-8"}] (include-css "/css/photoswipe.css")
       (include-css "/static/default-skin/default-skin.css")
       (include-js "/js/photoswipe.min.js")
       (include-js "/js/photoswipe-ui-default.min.js")
       (include-js "https://unpkg.com/masonry-layout@4/dist/masonry.pkgd.js")
       (include-js "https://unpkg.com/imagesloaded@4/imagesloaded.pkgd.js")
       (include-js "https://code.jquery.com/jquery-3.2.1.slim.min.js")
       (include-css "/css/garden.css") (photoswipe-js photoswipe-url)]
      [:div.container
       [:div.content (:content entry) photoswipe
        [:div#album
         (map-indexed (fn [index {:keys [image title]}]
                        [:img.image
                         {:src (thumbnail image),
                          :onClick (str "openGallery(" index ");")}])
                      (:images entry))] masonry-js]])))
