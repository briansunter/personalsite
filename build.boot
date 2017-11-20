(set-env!
 :source-paths #{"src"}
 :resource-paths #{"content" "resources"}
 :dependencies '[[perun "0.4.2-SNAPSHOT" :scope "test"]
                 [boot/core "2.6.0" :scope "provided"]
                 [pandeiro/boot-http "0.8.3"]
                 [shakkuri "1.0.5"]
                 [cheshire "5.8.0"]
                 [clj-time "0.9.0"]
                 [cpmcdaniel/boot-copy "1.0"]
                 [garden "1.3.3"]
                 [hashobject/boot-s3 "0.1.2-SNAPSHOT"]])

(require  '[io.perun :refer :all]
          '[pandeiro.boot-http :refer [serve]]
          '[render.photography :refer [group-albums]]
          '[cpmcdaniel.boot-copy :refer :all]
          '[tasks.tasks :refer [toml-metadata]]
          '[utils :refer [has-tag?]]
          '[hashobject.boot-s3 :refer :all])

(task-options!
 pom {:project 'briansuter.com
      :version "0.2.0"}
 s3-sync {:bucket "bsun.io"
          :source "public/"
          :access-key (System/getenv "AWS_ACCESS_KEY")
          :secret-key (System/getenv "AWS_SECRET_KEY")
          :options {"Cache-Control" "max-age=315360000, no-transform, public"}} )

(deftask build
  []
  (comp
   (global-metadata)
   (images-dimensions)
   (toml-metadata)
   (draft)
   (markdown)
   (asciidoctor)
   (permalink)
   (ttr)
   (render :renderer 'render.photography/render-album
           :filterer (partial has-tag? "photography"))
   (render :renderer 'render.base/render
           :filterer (partial has-tag? "post"))
   (tags :renderer 'render.tag/render)
   (sitemap :filename "sitemap.xml")
   (rss :site-title "Brian Sunter" :description "Brian Sunter's personal site" :base-url "https://briansunter.com/")
   (atom-feed :site-title "Brian Sunter" :description "Brian Sunter's Personal Site" :base-url "https://briansunter.com/")
   (pandoc :extensions [".org"] :out-ext ".html" :cmd-opts ["-f" "org" "-t" "html5"])
   (collection :renderer 'render.index/render
               :page "index.html"
               :filterer (partial has-tag? "index-page"))
   (print-meta)
   (sift :move {#"(.*)\.edn$" "$1.html"})
   (sift :move {#"(.*\.ttf)" "public/$1"})
   (sift :move {#"css" "public"})
   (sift :move {#"(.*\.js$)" "public/$1"})
   (sift :move {#"img" "public/img/"})
   (sift :move {#"static" "public/static"})
   (sift :move {#"^photos" "public/photos"})
   (sift :move {#"photography/photoswipe/(.*)\.json" "public/photography/$1/photoswipe.json"})
   (sift :move {#"main.css" "public/css/main.css"})
   (target)))

(deftask dev
  []
  (comp
   (watch)
   (build)
   (serve :dir "target/public" )))

(deftask deploy
  []
  (comp
   (build)
   (s3-sync)))

(deftask deploy-images
  []
  (comp
   (build)
   (images-dimensions)
   (markdown)
   (permalink)
   (assortment :renderer 'render.photography/render-photoswipe-json
               :grouper group-albums
               :extensions [".htm" ".html" "png" "jpg"]
               :out-dir "photoswipe")
   (sift :move {#"photoswipe/photography/(.*)\..+" "photoswipe/$1.json"})
   (target)
   (copy :output-dir    "content/photography/"
         :matching       #{#"^photoswipe/.*\.json$"})
   (s3-sync :source "public/photos" :bucket "photos.bsun.io")))
