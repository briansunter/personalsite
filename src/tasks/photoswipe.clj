(ns tasks.photoswipe
  (:require [utils :refer [has-tag? update-values group-by-singly if-let*]]
            [clojure.java.io :as io]
            [io.perun.core :as perun]
            [cheshire.core :refer [generate-string parse-string]]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.walk :as walk]
            [io.perun.core :as perun]))

(defn images-to-photoswipe
  [images-by-name images]
  (for [{:keys [title caption], :as image} images]
    (let [{:keys [filename width height]} (images-by-name (:image image))]
      {:src (str "http://photos.bsun.io/" filename),
       :title title,
       :caption caption,
       :w width,
       :h height})))

(defn group-albums
  [entries]
  (let [photo-entries (filter (partial has-tag? "photography") entries)]
    (into {}
          (for [{:keys [parent-path short-filename], :as entry} photo-entries]
            [(str parent-path "photoswipe/" short-filename ".json")
             {:entry entry, :entries entries}]))))

(defn generate-photoswipe-json
  [{:keys [entry entries]}]
  (perun/report-debug "photoswipe albums"
                      "processing photoswipe json"
                      (:filename entry))
  (let [images (:images entry)
        all-images (filter :height entries)
        images-by-name (group-by-singly :filename entries)]
    (generate-string {:data (images-to-photoswipe images-by-name images)}
                     {:pretty true})))
