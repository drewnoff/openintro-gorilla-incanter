;; gorilla-repl.fileformat = 1

;; **
;;; # Introduction to data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/intro_to_data/intro_to_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=cdc). 
;; **

;; @@
(ns openintro.intro-to-data)

(require '[incanter.core :as i]
         '[incanter.charts :as c]
         '[incanter.io :as iio]
         '[incanter.stats :as s])
(use 'incanter-gorilla.render :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ## Getting started
;;; The Behavioral Risk Factor Surveillance System (BRFSS) is an annual telephone 
;;; survey of 350,000 people in the United States. As its name implies, the BRFSS 
;;; is designed to identify risk factors in the adult population and report 
;;; emerging health trends. For example, respondents are asked about their diet and 
;;; weekly physical activity, their HIV/AIDS status, possible tobacco use, and even
;;; their level of healthcare coverage. The BRFSS Web site 
;;; ([http://www.cdc.gov/brfss](http://www.cdc.gov/brfss)) contains a complete 
;;; description of the survey, including the research questions that motivate the 
;;; study and many interesting results derived from the data.
;;; 
;;; We will focus on a random sample of 20,000 people from the BRFSS survey 
;;; conducted in 2000. While there are over 200  variables in this data set, we will
;;; work with a small subset.
;;; 
;;; We begin by loading the data set of 20,000 observations into Incanter dataset. 
;;; TODO: a bit of explanation about loading the data, csv format? :header true option.
;; **

;; @@
(def cdc (iio/read-dataset "../data/cdc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.intro-to-data/cdc</span>","value":"#'openintro.intro-to-data/cdc"}
;; <=

;; @@

;; @@
