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
;;; International, follow the following link:
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

;; **
;;; ## Inference on proportions
;; **

;; **
;;; As was hinted at in Exercise 1, Table 6 provides *statistics*, that is, 
;;; calculations made from the sample of 51,927 people. What we'd like, though, is 
;;; insight into the population *parameters*. You answer the question, "What 
;;; proportion of people in your sample reported being atheists?" with a 
;;; statistic; while the question "What proportion of people on earth would report 
;;; being atheists" is answered with an estimate of the parameter.
;;; 
;;; The inferential tools for estimating population proportion are analogous to 
;;; those used for means in the last chapter: the confidence interval and the 
;;; hypothesis test.
;;; 
;;; *5.  Write out the conditions for inference to construct a 95% confidence
;;;     interval for the proportion of atheists in the United States in 2012.
;;;     Are you confident all conditions are met?*
;;; 
;;; If the conditions for inference are reasonable, we can calculate
;;; the standard error and construct the interval.
;; **

;; @@
(chart-view
  (i/with-data
    (i/$rollup :count :total :response us12)
    (c/bar-chart :response :total)))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x46ccc1bc \"org.jfree.chart.JFreeChart@46ccc1bc\"], :opts nil}"}
;; <=

;; @@
(def p-hat (float (/ (i/nrow (i/$where {:response "atheist"} us12))
           	      (i/nrow us12))))

(def se (i/sqrt (/ (* p-hat (- 1 p-hat))
                   (i/nrow us12))))

(def ci [(s/quantile-normal 0.025 :mean p-hat :sd se)
 	(s/quantile-normal 0.975 :mean p-hat :sd se)])

(print ci)
;; @@
;; ->
;;; [0.03641833479607661 0.06338206563984791]
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; Although formal confidence intervals and hypothesis tests don't show up in the 
;;; report, suggestions of inference appear at the bottom of page 7: "In general, 
;;; the error margin for surveys of this kind is @@\pm@@ 3-5% at 95% confidence".
;;; 
;;; 6.  What is the margin of error for the estimate of the 
;;;     proportion of atheists in US in 2012?
;;; 
;;; 7.  Calculate confidence intervals for the 
;;;     proportion of atheists in 2012 in two other countries of your choice, and 
;;;     report the associated margins of error. Be sure to note whether the 
;;;     conditions for inference are met. It may be helpful to create new data 
;;;     sets for each of the two countries first, and then use these data sets
;;;     to calculate the confidence intervals.
;; **

;; **
;;; ## How does the proportion affect the margin of error?
;; **

;; **
;;; Imagine you've set out to survey 1000 people on two questions: are you female? 
;;; and are you left-handed? Since both of these sample proportions were 
;;; calculated from the same sample size, they should have the same margin of 
;;; error, right? Wrong! While the margin of error does change with sample size, 
;;; it is also affected by the proportion.
;;; 
;;; Think back to the formula for the standard error: $SE = \sqrt{p(1-p)/n}$. This 
;;; is then used in the formula for the margin of error for a 95% confidence 
;;; interval: $ME = 1.96\times SE = 1.96\times\sqrt{p(1-p)/n}$. Since the 
;;; population proportion $p$ is in this $ME$ formula, it should make sense that 
;;; the margin of error is in some way dependent on the population proportion. We 
;;; can visualize this relationship by creating a plot of $ME$ vs. $p$.
;;; 
;;; The first step is to make a vector `p` that is a sequence from 0 to 1 with 
;;; each number separated by 0.01. We can then create a vector of the margin of 
;;; error (`me`) associated with each of these values of `p` using the familiar 
;;; approximate formula ($ME = 2 \times SE$). Lastly, we plot the two vectors 
;;; against each other to reveal their relationship.
;; **

;; @@

;; @@
