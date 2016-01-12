;; gorilla-repl.fileformat = 1

;; **
;;; # Inference for categorical data
;;; 
;;; Original lab is available [here](http://htmlpreview.github.io/?https://github.com/andrewpbray/oiLabs-base-R/blob/master/inf_for_categorical_data/inf_for_categorical_data.html).
;;; You can download the data for this lab from [here](https://www.openintro.org/stat/data/?data=atheism). 
;; **

;; @@
(ns openintro.inf-for-categorical-data)

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
;;; In August of 2012, news outlets ranging from the [Washington
;;; Post](http://www.washingtonpost.com/national/on-faith/poll-shows-atheism-on-the-rise-in-the-us/2012/08/13/90020fd6-e57d-11e1-9739-eef99c5fb285_story.html) to the [Huffington
;;; Post](http://www.huffingtonpost.com/2012/08/14/atheism-rise-religiosity-decline-in-america_n_1777031.html)
;;; ran a story about the rise of atheism in America. The source for the story was 
;;; a poll that asked people, "Irrespective of whether you attend a place of 
;;; worship or not, would you say you are a religious person, not a religious 
;;; person or a convinced atheist?" This type of question, which asks people to 
;;; classify themselves in one way or another, is common in polling and generates 
;;; categorical data. In this lab we take a look at the atheism survey and explore 
;;; what's at play when making inference about population proportions using 
;;; categorical data.
;; **

;; **
;;; ## The survey
;; **

;; **
;;; To access the press release for the poll, conducted by WIN-Gallup 
;;; International, click on the following link:
;;; 
;;; *<http://www.wingia.com/web/files/richeditor/filemanager/Global_INDEX_of_Religiosity_and_Atheism_PR__6.pdf>*
;;; 
;;; Take a moment to review the report then address the following questions.
;;; 
;;; *1.  In the first paragraph, several key findings are reported. Do these 
;;;     percentages appear to be *sample statistics* (derived from the data 
;;;     sample) or *population parameters*?*
;;; 
;;; *2.  The title of the report is "Global Index of Religiosity and Atheism". To
;;;     generalize the report's findings to the global human population, what must 
;;;     we assume about the sampling method? Does that seem like a reasonable 
;;;     assumption?*
;; **

;; **
;;; ## The data
;; **

;; **
;;; Turn your attention to Table 6 (pages 15 and 16), which reports the
;;; sample size and response percentages for all 57 countries. While this is
;;; a useful format to summarize the data, we will base our analysis on the
;;; original data set of individual responses to the survey. Load this data
;;; set with the following command.
;; **

;; @@
(def atheism (iio/read-dataset "../data/atheism.csv" :header true))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-categorical-data/atheism</span>","value":"#'openintro.inf-for-categorical-data/atheism"}
;; <=

;; **
;;; *3.  What does each row of Table 6 correspond to? What does each row of 
;;;     `atheism` correspond to?*
;;; 
;;; To investigate the link between these two ways of organizing this data, take a 
;;; look at the estimated proportion of atheists in the United States. Towards 
;;; the bottom of Table 6, we see that this is 5%. We should be able to come to 
;;; the same number using the `atheism` data.
;;; 
;;; *4.  Using the command below, create a new dataframe called `us12` that contains
;;;     only the rows in `atheism` associated with respondents to the 2012 survey 
;;;     from the United States. Next, calculate the proportion of atheist 
;;;     responses. Does it agree with the percentage in Table 6? If not, why?*
;; **

;; @@
(def us12
  (i/$where {:nationality "United States" :year 2012} atheism))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-categorical-data/us12</span>","value":"#'openintro.inf-for-categorical-data/us12"}
;; <=
