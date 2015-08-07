(ns ^:figwheel-always mandelbrot.core
    (:require))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:rStart -2.5 :rEnd 1.0 :iStart -1.0 :iEnd 1.0}))

(defn jsprint [args]
  (.log js/console args))

(defn convertIterToColour [iter maxIter colourScheme]
  (colourScheme (/ iter maxIter)))

(defn rainbowColourScheme [iterPercent]
  (let [h (* iterPercent 6)
        i (Math/floor h)
        a 0
        b (- 1 (- h i))
        c (- h i)]
    (if (< (- 1 iterPercent) 0.0001)
      [0 0 0] ; black
      (map #(* 255 %)
           (case i
             0 [1 c a]
             1 [b 1 a]
             2 [a 1 c]
             3 [a b 1]
             4 [c a 1]
             [1 a b])))))

(defn calcMandelbrot [width height]
  (mapcat conj
            (repeat (vector 255))
            (take (* width height) (cycle (range 255)))))


(defn setImageData [image data]
   (let [idv (map vector (iterate inc 0) data)]))

(defn createImage [image]
  (let [imgData (take (* image.width image.height) (cycle (range 255)))
        idv (map vector (iterate inc 0) (calcMandelbrot image.width image.height))]
    (doseq [[i v] idv]
      (aset image.data i v))))

(defn drawSquare [ctx x y width height]
  (let [image (.createImageData ctx width height)]
    (createImage image)
    (.putImageData ctx image x y)))

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

