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

;; **
;;; ## Confidence intervals
;; **

;; **
;;; One of the most common ways to describe the typical or central value of a distribution is to use the mean. In this case we can calculate the mean of the sample using,
;; **

;; @@
(def sample-mean (s/mean samp))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.confidence-intervals/sample-mean</span>","value":"#'openintro.confidence-intervals/sample-mean"}
;; <=

;; **
;;; Return for a moment to the question that first motivated this lab: based on 
;;; this sample, what can we infer about the population? Based only on this single 
;;; sample, the best estimate of the average living area of houses sold in Ames 
;;; would be the sample mean, usually denoted as @@\bar{x}@@ (here we're calling it 
;;; `sample-mean`). That serves as a good *point estimate* but it would be useful 
;;; to also communicate how uncertain we are of that estimate. This can be 
;;; captured by using a *confidence interval*.
;;; 
;;; We can calculate a 95% confidence interval for a sample mean by adding and 
;;; subtracting 1.96 standard errors to the point estimate (See Section 4.2.3 if 
;;; you are unfamiliar with this formula).
;; **

;; @@
(def se (i/$= (s/sd samp) / (i/sqrt 60)))

(def lower (i/$= sample-mean - 1.96 * se))
(def upper (i/$= sample-mean + 1.96 * se))

[lower, upper]
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>1449.9361476532492</span>","value":"1449.9361476532492"},{"type":"html","content":"<span class='clj-double'>1720.4638523467509</span>","value":"1720.4638523467509"}],"value":"[1449.9361476532492 1720.4638523467509]"}
;; <=

;; **
;;; This is an important inference that we've just made: even though we don't know 
;;; what the full population looks like, we're 95% confident that the true 
;;; average size of houses in Ames lies between the values *lower* and *upper*. 
;;; There are a few conditions that must be met for this interval to be valid.
;; **

;; **
;;; *3.  For the confidence interval to be valid, the sample mean must be normally 
;;; distributed and have standard error @@s / \sqrt{n}@@. What conditions must be 
;;; met for this to be true?*
;; **

;; **
;;; ## Confidence levels
;; **

;; **
;;; *4.  What does "95% confidence" mean? If you're not sure, see Section 4.2.2.*
;; **

;; **
;;; In this case we have the luxury of knowing the true population mean since we 
;;; have data on the entire population. This value can be calculated using the 
;;; following command:
;; **

;; @@
(s/mean population)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-double'>1499.6904436860068</span>","value":"1499.6904436860068"}
;; <=

;; **
;;; *5.  Does your confidence interval capture the true average size of houses in 
;;; Ames? If you are working on this lab in a classroom, does your neighbor's 
;;; interval capture this value?*
;;; 
;;; *6.  Each student in your class should have gotten a slightly different 
;;; confidence interval. What proportion of those intervals would you expect to 
;;; capture the true population mean? Why? If you are working in this lab in a 
;;; classroom, collect data on the intervals created by other students in the 
;;; class and calculate the proportion of intervals that capture the true 
;;; population mean*
;; **

;; **
;;; We're going to recreate many samples to learn more about how sample 
;;; means and confidence intervals vary from one sample to another.
;;; 
;;; Here is the rough outline:
;;; 
;;; -   Obtain a random sample.
;;; -   Calculate and store the sample's mean and standard deviation.
;;; -   Repeat steps (1) and (2) 50 times.
;;; -   Use these stored statistics to calculate many confidence intervals.
;; **

;; @@
(def n 60)

(def samp-stats
  [(map (fn [_] (s/mean (s/sample population :size n))) (range 50))
   (map (fn [_] (s/sd (s/sample population :size n))) (range 50))])

(def samp-mean (first samp-stats))
(def samp-sd (second samp-stats))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.confidence-intervals/samp-sd</span>","value":"#'openintro.confidence-intervals/samp-sd"}
;; <=

;; **
;;; Lastly, we construct the confidence intervals.
;; **

;; @@
(def lower-bounds (i/$= samp-mean - 1.96 * samp-sd / (i/sqrt n)))
(def upper-bounds (i/$= samp-mean + 1.96 * samp-sd / (i/sqrt n)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.confidence-intervals/upper-bounds</span>","value":"#'openintro.confidence-intervals/upper-bounds"}
;; <=

;; **
;;; Lower bounds of these 50 confidence intervals are stored in `lower-bounds`, 
;;; and the upper bounds are in `upper-bounds`. Let's view the first interval.
;; **

;; @@
(map first [lower-bounds upper-bounds])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>1333.8926015371828</span>","value":"1333.8926015371828"},{"type":"html","content":"<span class='clj-double'>1621.7740651294837</span>","value":"1621.7740651294837"}],"value":"(1333.8926015371828 1621.7740651294837)"}
;; <=

;; **
;;; * * *
;;; 
;;; ## On your own
;;; 
;;; -   Plot all intervals. What proportion of your confidence intervals include 
;;;     the true population mean? Is this proportion exactly equal to the 
;;;     confidence level? If not, explain why.
;;; 
;;; -   Pick a confidence level of your choosing, provided it is not 95%. What is 
;;;     the appropriate critical value?
;;; 
;;; -   Calculate 50 confidence intervals at the confidence level you chose in the 
;;;     previous question. You do not need to obtain new samples, simply calculate 
;;;     new intervals based on the sample means and standard deviations you have 
;;;     already collected. Plot all intervals and 
;;;     calculate the proportion of intervals that include the true population 
;;;     mean. How does this percentage compare to the confidence level selected for
;;;     the intervals?
;;; 
;; **
