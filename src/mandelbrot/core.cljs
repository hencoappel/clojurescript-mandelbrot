(ns ^:figwheel-always mandelbrot.core
    (:require))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn jsprint [args]
  (.log js/console args))

(defn genvals [width height]
  (mapcat conj
            (repeat (vector 255 0 0))
            (take (* width height) (cycle (range 255)))))


(defn setImageData [image data]
   (let [idv (map vector (iterate inc 0) data)]))

(defn createImage [image]
  (let [imgData (take (* image.width image.height) (cycle (range 255)))
        idv (map vector (iterate inc 0) (genvals image.width image.height))]
    (doseq [[i v] idv]
      (aset image.data i v))))

(defn drawSquare [ctx x y width height]
  (let [imgData (.createImageData ctx width height)]
    (jsprint imgData)
    (jsprint (aget imgData "data"))
    (createImage imgData)
    (jsprint (aget imgData "data"))
    (.putImageData ctx imgData x y)))

(let [viewport (.getElementById js/document "viewport")
      ctx (.getContext viewport "2d")
      width viewport.width
      height viewport.height]
  (drawSquare ctx 100 100 100 100))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

