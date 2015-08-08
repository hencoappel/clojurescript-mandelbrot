(ns ^:figwheel-always mandelbrot.core
    (:require
      [mandelbrot.mandelbrot :as mb]))

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
    (if (< (- 1 iterPercent) 0.000001)
      [0 0 0 255] ;; black
      (map #(* 255 %)
           (case i
             0 [1 c a 1]
             1 [b 1 a 1]
             2 [a 1 c 1]
             3 [a b 1 1]
             4 [c a 1 1]
               [1 a b 1])))))

(defn calcMandelbrot [width height maxIter]
  (mapcat
    #(convertIterToColour % maxIter rainbowColourScheme)
    (apply concat (mb/calcIterForRange -2.5 1 width -1 1 height maxIter))))

(defn setImageData [image data]
  (let [idv (map vector (iterate inc 0) data)]
    (doseq [[i v] idv]
      (aset image.data i v))))

(defn drawMandlebrot [ctx x y width height]
  (let [image (.createImageData ctx width height)
        mdlbrt (calcMandelbrot image.width image.height 100)]
    (setImageData image mdlbrt)
    (.putImageData ctx image x y)))

(defn drawRect [ctx x y width height]
  (.fillRect ctx x y width height))

(let [viewport (.getElementById js/document "viewport")
      ctx (.getContext viewport "2d")
      width viewport.width
      height viewport.height]
  (drawRect ctx 0 0 600 400)
  (.time js/console "drawMandlebrot")
  (drawMandlebrot ctx 0 0 600 400)
  (.timeEnd js/console "drawMandlebrot"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

