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
;;; *6.  What is the margin of error for the estimate of the 
;;;     proportion of atheists in US in 2012?*
;;; 
;;; *7.  Calculate confidence intervals for the 
;;;     proportion of atheists in 2012 in two other countries of your choice, and 
;;;     report the associated margins of error. Be sure to note whether the 
;;;     conditions for inference are met. It may be helpful to create new data 
;;;     sets for each of the two countries first, and then use these data sets
;;;     to calculate the confidence intervals.*
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
;;; Think back to the formula for the standard error: $$SE = \sqrt{p(1-p)/n}$$ This 
;;; is then used in the formula for the margin of error for a 95% confidence 
;;; interval: $$ME = 1.96\times SE = 1.96\times\sqrt{p(1-p)/n}$$ Since the 
;;; population proportion @@p@@ is in this @@ME@@ formula, it should make sense that 
;;; the margin of error is in some way dependent on the population proportion. We 
;;; can visualize this relationship by creating a plot of @@ME@@ vs. @@p@@.
;;; 
;;; The first step is to make a vector `p` that is a sequence from 0 to 1 with 
;;; each number separated by 0.01. We can then create a vector of the margin of 
;;; error (`me`) associated with each of these values of `p` using the familiar 
;;; approximate formula (@@ME = 2 \times SE@@). Lastly, we plot the two vectors 
;;; against each other to reveal their relationship.
;; **

;; @@
(def n 1000)
(def p (range 0 1.01 0.01))
(def me
  (map #(i/sqrt (/ (* % (- 1 %))
                     n))
       p))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;openintro.inf-for-categorical-data/me</span>","value":"#'openintro.inf-for-categorical-data/me"}
;; <=

;; @@
(chart-view
  (c/scatter-plot p me
                 :y-label "Margin of Error"
                 :x-label "Population Proportion"))
;; @@
;; =>
;;; {"type":"html","content":"<img src=\"data:image/PNG;base64,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\"/>","value":"#incanter_gorilla.render.ChartView{:content #object[org.jfree.chart.JFreeChart 0x7a089236 \"org.jfree.chart.JFreeChart@7a089236\"], :opts nil}"}
;; <=

;; **
;;; *8.  Describe the relationship between `p` and `me`.*
;; **

;; **
;;; ## Success-failure condition
;; **

;; @@

;; @@
