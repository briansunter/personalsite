(set-env!
 :source-paths #{"src" "sass"}
 :resource-paths #{"content" "resources"}
 :dependencies '[[perun "0.4.2-SNAPSHOT" :scope "test"]
                 [boot/core "2.6.0" :scope "provided"]
                 [pandeiro/boot-http "0.8.3"]
                 [shakkuri "1.0.5"]
                 [clj-time "0.9.0"]
                 [garden "1.3.3"]
                 [deraen/boot-sass "0.3.1"]
                 [hashobject/boot-s3 "0.1.2-SNAPSHOT"]])

(require  '[io.perun :refer :all]
          '[pandeiro.boot-http :refer [serve]]
          '[deraen.boot-sass :refer :all]
          '[utils :refer [has-tag?]]
          '[hashobject.boot-s3 :refer :all])

(task-options!
 pom {:project 'briansuter.com
      :version "0.2.0"}
 s3-sync {:bucket "bsun.io"
          :source "public/"
          :access-key (System/getenv "AWS_ACCESS_KEY")
          :secret-key (System/getenv "AWS_SECRET_KEY")
          :options {"Cache-Control" "max-age=315360000, no-transform, public"}})


(deftask build
  []
  (comp
   (global-metadata)
   (draft)
   ;; (asciidoctor)
   (markdown)
   (tags :renderer 'render.tag/render)
   (ttr)
   (permalink)
   (sitemap :filename "sitemap.xml")
   (rss :site-title "Brian Sunter" :description "Brian Sunter's personal site" :base-url "https://briansunter.com/")
   (atom-feed :site-title "Brian Sunter" :description "Brian Sunter's Personal Site" :base-url "https://briansunter.com/")
   (pandoc :extensions [".org"] :out-ext ".html" :cmd-opts ["-f" "org" "-t" "html5"])
   ;; (render :renderer 'render.render/base :extensions [".html"])
   (collection :renderer 'render.index/render
               :page "index.html"
               :filterer (partial has-tag? "index-page"))
   (sass :source-map true)
   (inject-scripts :scripts #{"https://ajax.googleapis.com/ajax/libs/webfont/1.5.18/webfont.js"})
   (sift :move {#"(.*)\.edn$" "$1.html"})
   (sift :move {#"(.*\.ttf)" "public/$1"})
   (sift :move {#"(.*\.js)" "public/$1"})
   (sift :move {#"css/(.*)" "public/css/$1"})
   (sift :move {#"main.css" "public/css/main.css"})
   (target)))

(deftask dev
  []
  (comp
   (watch)
   (build)
   (print-meta)
   (serve :dir "target/public" )))

(deftask deploy
  []
  (comp
   (build)
   (s3-sync)))
