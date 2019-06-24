(set-env!
 :jvm-opts ["-Xmx4G"]
 :source-paths #{"src"}
 :resource-paths #{"content" "resources"}
 :dependencies '[[org.flatland/ordered "1.5.7"] 
                 [org.clojure/core.rrb-vector "0.0.13"]
                 [perun "0.4.2-SNAPSHOT" :scope "test"]
                 [org.clojure/clojure "1.8.0"]
                 [boot/core "2.8.3" :scope "provided"]
                 [pandeiro/boot-http "0.8.3"]
                 [shakkuri "1.0.5"]
                 [cheshire "5.8.1"]
                 [clj-time "0.15.1"]
                 [org.martinklepsch/boot-garden "1.3.2-1"]
                 [garden "1.3.9"]
                 [confetti/confetti "0.2.1"]
                 [hashobject/boot-s3 "0.1.2-SNAPSHOT"]])

(require  '[io.perun :refer :all :exclude [trace]]
          '[pandeiro.boot-http :refer [serve]]
          '[tasks.tasks :refer [toml-metadata photoswipe-album]]
          '[utils :refer [has-tag?]]
          '[confetti.boot-confetti :refer [sync-bucket]]
          '[org.martinklepsch.boot-garden :refer [garden]])

(task-options! garden {:styles-var   'render.stylesheet/combined
                       :css-prepend ["css/reset.css"]
                       :output-to    "css/garden.css"
                       :pretty-print false})

(task-options!
 pom {:project 'briansuter.com
      :version "0.2.0"}
 sync-bucket {:bucket "briansunter.com"
          :dir "target/public/"
          :access-key (System/getenv "AWS_ACCESS_KEY")
          :secret-key (System/getenv "AWS_SECRET_KEY")
              :cloudfront-id "ESIJ6FZOAN90E"})

(deftask build
  []
  (comp
   (global-metadata)
   (toml-metadata)
   (draft)
   (pandoc :cmd-opts ["-t" "revealjs" "-s" "-V" "revealjs-url=/js/revealjs" "-V" "theme=dk-light"] :filterer (partial has-tag? "talk"))
   (markdown)
   (asciidoctor)
   (pandoc :extensions [".org"] :out-ext ".html" :cmd-opts ["-f" "org" "-t" "html5"])
   (sitemap :filename "sitemap.xml")
   (rss :site-title "Brian Sunter" :description "Brian Sunter's personal site" :base-url "https://briansunter.com/")
   (atom-feed :site-title "Brian Sunter" :description "Brian Sunter's Personal Site" :base-url "https://briansunter.com/")
   (permalink)
   (ttr)
   (render :renderer 'render.base/render
           :filterer (partial has-tag? "blog"))
   (render :renderer 'render.base/render
           :filterer (partial has-tag? "project"))
   (tags :renderer 'render.tag/render)
   (collection :renderer 'render.layout.index/render
               :page "index.html"
               :filterer (partial has-tag? "index-page"))
   (garden)
   (sift :move {#"^js" "public/js"})
   (sift :move {#"^img" "public/img"})
   (sift :move {#"^photos" "public/photos"})
   (sift :move {#"^css" "public/css"})
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
   (sync-bucket)))
