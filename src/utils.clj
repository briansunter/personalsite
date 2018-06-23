(ns utils)

(defn has-tag? [t {:keys [tags]}] ((set tags) t))

(defn update-values
  [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn group-by-singly [p i] (update-values (group-by p i) first))

(defmacro if-let*
  ([bindings then] `(if-let* ~bindings ~then nil))
  ([bindings then else]
   (if (seq bindings)
     `(if-let [~(first bindings) ~(second bindings)]
        (if-let* ~(drop 2 bindings) ~then ~else)
        ~else)
     then)))
