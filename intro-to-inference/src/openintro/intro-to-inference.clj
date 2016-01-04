;; gorilla-repl.fileformat = 1

;; **
;;; ## Foundations for statistical inference - Sampling distributions
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/sampling_distributions/sampling_distributions.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=ames). 
;; **

;; @@
(ns openintro.intro-to-inference)

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
;;; In this lab, we investigate the ways in which the statistics from a random 
;;; sample of data can serve as point estimates for population parameters.  We're 
;;; interested in formulating a *sampling distribution* of our estimate in order 
;;; to learn about the properties of the estimate, such as its distribution.
;; **

;; **
;;; ## The data
;; **

;; **
;;; We consider real estate data from the city of Ames, Iowa.  The details of 
;;; every real estate transaction in Ames is recorded by the City Assessor's 
;;; office.  Our particular focus for this lab will be all residential home sales 
;;; in Ames between 2006 and 2010.  This collection represents our population of 
;;; interest.  In this lab we would like to learn about these home sales by taking 
;;; smaller samples from the full population.  Let's load the data.
;; **

;; @@
(def ames (iio/read-dataset "../data/ames.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-inference/ames</span>","value":"#'openintro.intro-to-inference/ames"}
;; <=

;; **
;;; We see that there are quite a few variables in the data set, enough to do a 
;;; very in-depth analysis.  For this lab, we'll restrict our attention to just 
;;; two of the variables: the above ground living area of the house in square feet 
;;; (`Gr.Liv.Area`) and the sale price (`SalePrice`).  To save some effort 
;;; throughout the lab, create two variables with short names that represent these 
;;; two variables. 
;; **

;; @@
(def area (i/sel ames :cols :Gr.Liv.Area))

(def price (i/sel ames :cols :SalePrice))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-inference/price</span>","value":"#'openintro.intro-to-inference/price"}
;; <=

;; **
;;; Let's look at the distribution of area in our population of home sales by 
;;; calculating a few summary statistics and making a histogram.
;; **

;; @@
(println (clojure.string/join "\t" ["Min." "1st Qu." "Median" "3rd Qu" "Max."]))
(println (clojure.string/join "\t"(s/quantile area)))

(println "Mean")
(println (s/mean area))

(chart-view (c/histogram area :nbins 30))
;; @@
;; ->
;;; Min.	1st Qu.	Median	3rd Qu	Max.
;;; 334.0	1126.0	1442.0	1742.75	5642.0
;;; Mean
;;; 1499.6904436860068
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x512f5d5 \"org.jfree.chart.JFreeChart@512f5d5\"], :opts nil}"}
;; <=

;; **
;;; *1.  Describe this population distribution.*
;; **

;; @@

;; @@
