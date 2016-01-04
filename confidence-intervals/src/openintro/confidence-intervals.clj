;; gorilla-repl.fileformat = 1

;; **
;;; # Foundations for statistical inference - Confidence intervals
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/confidence_intervals/confidence_intervals.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=ames). 
;; **

;; @@
(ns openintro.confidence-intervals)

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
;;; ## Sampling from Ames, Iowa
;;; 
;;; If you have access to data on an entire population, say the size of every 
;;; house in Ames, Iowa, it's straight forward to answer questions like, "How big 
;;; is the typical house in Ames?" and "How much variation is there in sizes of 
;;; houses?". If you have access to only a sample of the population, as is often 
;;; the case, the task becomes more complicated. What is your best guess for the 
;;; typical size if you only know the sizes of several dozen houses? This sort of 
;;; situation requires that you use your sample to make inference on what your 
;;; population looks like.
;; **

;; **
;;; ## The data
;;; 
;;; In the previous lab, `Sampling Distributions`, we looked at the population data
;;; of houses from Ames, Iowa. Let's start by loading that data set.
;; **

;; @@
(def ames (iio/read-dataset "../data/ames.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.confidence-intervals/ames</span>","value":"#'openintro.confidence-intervals/ames"}
;; <=

;; **
;;; In this lab we'll start with a simple random sample of size 60 from the 
;;; population. Specifically, this is a simple random sample of size 60. Note that 
;;; the data set has information on many housing variables, but for the first 
;;; portion of the lab we'll focus on the size of the house, represented by the 
;;; variable `Gr.Liv.Area`.
;; **

;; @@
(def population (i/sel ames :cols :Gr.Liv.Area))
(def samp (s/sample population :size 60))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.confidence-intervals/samp</span>","value":"#'openintro.confidence-intervals/samp"}
;; <=

;; **
;;; 1.  Describe the distribution of your sample. What would you say is the 
;;; "typical" size within your sample? Also state precisely what you interpreted 
;;; "typical" to mean.
;;; 
;;; 2.  Would you expect another student's distribution to be identical to yours? 
;;; Would you expect it to be similar? Why or why not?
;; **

;; @@

;; @@
