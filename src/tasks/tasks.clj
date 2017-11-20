(ns tasks.tasks
  (:require [boot.core :as boot :refer [deftask]]
            [io.perun :as perun]
            [boot.pod :as pod]))

(def ^:private ^:deps global-deps
  '[])

(defn- create-pod' [deps]
  (-> (boot/get-env)
      (update-in [:dependencies] into global-deps)
      (update-in [:dependencies] into deps)
      pod/make-pod))

(defn- create-pod
  [deps]
  (future (create-pod' deps)))

(def ^:private ^:deps toml-metadata-deps
  '[[org.clojure/tools.namespace "0.3.0-alpha3"]
    [clj-toml "0.3.1"]])

(def ^:private +toml-metadata-defaults+
  {:filterer identity
   :extensions []})

(deftask toml-metadata
  "Parse TOML metadata at the beginning of files
  This task is primarily intended for composing with other tasks.
  It will extract and parse any TOML data from the beginning of
  a file, and then overwrite that file with the TOML removed, and
  with the parsed data added as perun metadata."
  [_ filterer   FILTER     code  "predicate to use for selecting entries (default: `identity`)"
   e extensions EXTENSIONS [str] "extensions of files to include (default: `[]`, aka, all extensions)"
   r keep-toml             bool  "if `true`, remove the yaml header from files"]
  (let [pod (create-pod toml-metadata-deps)
        options (merge +toml-metadata-defaults+ *opts*)]
    (perun/content-task
     {:render-form-fn (fn [data] `(tasks.toml/parse-toml ~data ~keep-toml))
      :paths-fn #(perun/content-paths % options)
      :passthru-fn perun/content-passthru
      :task-name "toml-metadata"
      :tracer :io.perun/toml-metadata
      :pod pod})))
