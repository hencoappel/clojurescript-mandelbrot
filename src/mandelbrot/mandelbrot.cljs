(ns ^:figwheel-always mandelbrot.mandelbrot
    (:require))

(defn calcIterations
  ([real imag]
   (calcIterations real imag 100))
  ([real imag maxIter]
   (loop [r (double 0)
          i (double 0)
          iter (int 0)]
     (if (and (< (+ (* r r) (* i i)) 4) (< iter maxIter))
      (recur
        (+ (- (* r r) (* i i)) real)
        (+(* 2 r i) imag)
        (inc iter))
      iter))))

(defn linspace [start end steps]
  (let [step (/ (- end start) (dec steps))]
    (take steps (map #(+ (* % step) start) (range)))))

(defn calcIterForRange [rStart rEnd rSteps iStart iEnd iSteps]
  (vec (for [i (linspace iStart iEnd iSteps)]
      (vec (for [r (linspace rStart rEnd rSteps)]
        [(calcIterations r i)])))))