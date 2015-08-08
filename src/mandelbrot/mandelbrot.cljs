(ns ^:figwheel-always mandelbrot.mandelbrot
    (:require))

(defn calcIterations [^double real ^double imag ^double maxIter]
   (loop [r (double 0)
          i (double 0)
          iter (int 0)]
     (let [rSqr (* r r)
           iSqr (* i i)
           ri (* r i)]
       (if (and (< (+ rSqr iSqr) 4) (< iter maxIter))
         (recur
           (+ (- rSqr iSqr) real)
           (+ ri ri imag)
           (inc iter))
         iter))))

(defn linspace [start end steps]
  (let [step (/ (- end start) (dec steps))]
    (take steps (map #(+ (* % step) start) (range)))))

(defn calcIterForRange
  ([rStart rEnd rSteps iStart iEnd iSteps]
   (calcIterForRange rStart rEnd rSteps iStart iEnd iSteps 100))
  ([rStart rEnd rSteps iStart iEnd iSteps maxIter]
    (vec
      (for [i (linspace iStart iEnd iSteps)]
        (vec
          (for [r (linspace rStart rEnd rSteps)]
            (calcIterations r i maxIter)))))))