;; gorilla-repl.fileformat = 1

;; **
;;; # Multiple Linear Regression
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/multiple_regression/multiple_regression.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=evals). 
;; **

;; @@
(ns openintro.multiple-regression)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s]
         '[incanter.distributions :as dist])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Hot Hands
;;; 
;;; Basketball players who make several baskets in succession are described as 
;;; having a *hot hand*. Fans and players have long believed in the hot hand 
;;; phenomenon, which refutes the assumption that each shot is independent of the 
;;; next. However, a 1985 paper by Gilovich, Vallone, and Tversky collected evidence
;;; that contradicted this belief and showed that successive shots are independent 
;;; events ([http://psych.cornell.edu/sites/default/files/Gilo.Vallone.Tversky.pdf](http://psych.cornell.edu/sites/default/files/Gilo.Vallone.Tversky.pdf)). This paper started a great controversy that continues to this day, as you can 
;;; see by Googling *hot hand basketball*.
;;; 
;;; We do not expect to resolve this controversy today. However, in this lab we'll 
;;; apply one approach to answering questions like this. The goals for this lab are 
;;; to (1) think about the effects of independent and dependent events, (2) learn 
;;; how to simulate shooting streaks with Incanter, and (3) to compare a simulation to actual
;;; data in order to determine if the hot hand phenomenon appears to be real.
;; **
