;; gorilla-repl.fileformat = 1

;; **
;;; # Inference for numerical data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/inf_for_numerical_data/inf_for_numerical_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=nc). 
;; **

;; @@
(ns openintro.inf-for-numerical-data)

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
;;; ## North Carolina births
;;; 
;;; In 2004, the state of North Carolina released a large data set containing 
;;; information on births recorded in this state. This data set is useful to 
;;; researchers studying the relation between habits and practices of expectant 
;;; mothers and the birth of their children. We will work with a random sample of 
;;; observations from this data set.
;; **

;; **
;;; ## Exploratory analysis
;; **

;; **
;;; Load the `nc` data set into our workspace.
;; **

;; @@
(def nc (iio/read-dataset "../data/nc.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-numerical-data/nc</span>","value":"#'openintro.inf-for-numerical-data/nc"}
;; <=

;; **
;;; We have observations on 13 different variables, some categorical and some 
;;; numerical. The meaning of each variable is as follows.
;;; 
;;; variable         | description
;;; ---------------- | -----------
;;; `fage`           | father's age in years.
;;; `mage`           | mother's age in years.
;;; `mature`         | maturity status of mother.
;;; `weeks`          | length of pregnancy in weeks.
;;; `premie`         | whether the birth was classified as premature (premie) or full-term.
;;; `visits`         | number of hospital visits during pregnancy.
;;; `marital`        | whether mother is `married` or `not married` at birth.
;;; `gained`         | weight gained by mother during pregnancy in pounds.
;;; `weight`         | weight of the baby at birth in pounds.
;;; `lowbirthweight` | whether baby was classified as low birthweight (`low`) or not (`not low`).
;;; `gender`         | gender of the baby, `female` or `male`.
;;; `habit`          | status of the mother as a `nonsmoker` or a `smoker`.
;;; `whitemom`       | whether mom is `white` or `not white`.
;;; 
;;; *1.  What are the cases in this data set? How many cases are there in our sample?*
;; **

;; @@

;; @@
