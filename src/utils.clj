(ns utils)

(defn has-tag?
  [t {:keys [tags]}]
  ((set tags) t))
