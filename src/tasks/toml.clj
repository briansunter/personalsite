(ns tasks.toml
  (:require [toml.core :as toml]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.walk :as walk]
            [io.perun.core :as perun]))

(def ^:dynamic *toml-head* #"\+\+\+\r?\n")

(defn substr-between
  "Find string that is nested in between two strings. Return first match.
  Copied from https://github.com/funcool/cuerdas"
  [s prefix suffix]
  (cond (nil? s) nil
        (nil? prefix) nil
        (nil? suffix) nil
        :else (some-> s
                      (str/split prefix)
                      second
                      (str/split suffix)
                      first)))

(defn remove-metadata
  [content]
  (let [splitted (str/split content *toml-head* 3)]
    (if (> (count splitted) 2) (first (drop 2 splitted)) content)))

(defn parse-toml
  [{:keys [entry]} keep-toml]
  (let [content (-> entry
                    :full-path
                    io/file
                    slurp)
        parsed-metadata (if-let [metadata-str (substr-between content
                                                              *toml-head*
                                                              *toml-head*)]
                          (walk/keywordize-keys (toml/read metadata-str))
                          {})
        metadata (merge entry parsed-metadata)]
    (if keep-toml
      metadata
      (assoc metadata :rendered (remove-metadata content)))))
