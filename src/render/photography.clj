(ns render.photography
  (:require [hiccup.core :refer [deftag]]
            [hiccup.page :refer [html5 include-css include-js]]
            [utils :refer [has-tag?]]
            [cheshire.core :refer [generate-string]]))

(defn find-first
  [p c]
  (first (filter)))

(defn images-to-photoswipe
  [images-by-name {:keys [images]}]
  (for [{:keys [title caption] :as image} images]
    (let [{:keys [permalink width height]} (images-by-name (:image image))]
      {:src permalink
       :title title
       :caption caption
       :w width
       :h height})))

(defn update-values [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn group-by-singly
  [p i]
  (update-values (group-by p i) first))

(def photoswipe
   [:div.pswp
    {:aria-hidden "true", :role "dialog", :tabindex "-1"}
    [:div.pswp__bg]
    [:div.pswp__scroll-wrap
     [:div.pswp__container
      [:div.pswp__item]
      [:div.pswp__item]
      [:div.pswp__item]]
     [:div.pswp__ui.pswp__ui--hidden
      [:div.pswp__top-bar
       [:div.pswp__counter]
       [:button.pswp__button.pswp__button--close
        {:title "Close (Esc)"}]
       [:button.pswp__button.pswp__button--share {:title "Share"}]
       [:button.pswp__button.pswp__button--fs
        {:title "Toggle fullscreen"}]
       [:button.pswp__button.pswp__button--zoom {:title "Zoom in/out"}]
       [:div.pswp__preloader
        [:div.pswp__preloader__icn
         [:div.pswp__preloader__cut [:div.pswp__preloader__donut]]]]]
      [:div.pswp__share-modal.pswp__share-modal--hidden.pswp__single-tap
       [:div.pswp__share-tooltip]
       " \n            "]
      [:button.pswp__button.pswp__button--arrow--left
       {:title "Previous (arrow left)"}]
      [:button.pswp__button.pswp__button--arrow--right
       {:title "Next (arrow right)"}]
      [:div.pswp__caption [:div.pswp__caption__center]]]]])

(defn render-album
  [{:keys [entry]}]
  (html5 [:head [:meta {:charset "utf-8"}]
          (include-css "/css/photoswipe.css")
          (include-css "/static/default-skin/default-skin.css")
          (include-js "/js/photoswipe.min.js")
          (include-js "/js/photoswipe-ui-default.min.js")]
         [:div.bar
          photoswipe
          ;; TODO: Clojurescript or react
          [:script (str "var items = " (:photoswipe-json entry) " \n"
                        "var options = {index: 0,
captionEl: true,

addCaptionHTMLFn: function(item, captionEl, isFake) {
    if(!item.title) {
        captionEl.children[0].innerHTML = '';
        return false;
    }
    captionEl.children[0].innerHTML = item.title;
    return true;
}};

var pswpElement = document.querySelectorAll('.pswp')[0];
var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
gallery.init();
                          ")]]))


(defn group-albums
  [entries]
  (let [albums (filter (partial has-tag? "photography") entries)
        all-images (filter :height entries)
        images-by-name (group-by-singly :filename all-images)
        photoswipe-json-for-entry #(generate-string (images-to-photoswipe images-by-name %))]
    (into {} (map (fn [a] [(:filename a) {:entry {:photoswipe-json (photoswipe-json-for-entry a)}}]) albums))))

(defn render
  [content]
  (html5 [:head [:meta {:charset "utf-8"}]]
         [:div.foo (-> content)]))
